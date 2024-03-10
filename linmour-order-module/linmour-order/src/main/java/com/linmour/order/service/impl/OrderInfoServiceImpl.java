package com.linmour.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linmour.security.dtos.Result;
import com.linmour.security.utils.RedisCache;
import com.linmour.order.convert.OrderInfoConvert;
import com.linmour.order.feign.ProductFeign;
import com.linmour.order.mapper.OrderInfoMapper;
import com.linmour.order.mapper.OrderItemMapper;
import com.linmour.order.mq.ProducerMq;
import com.linmour.order.pojo.Do.OrderInfo;
import com.linmour.order.pojo.Do.OrderItem;
import com.linmour.order.pojo.Dto.CheckoutDto;
import com.linmour.order.pojo.Dto.CreateOrderDto;
import com.linmour.order.pojo.Dto.OrderInfoDto;
import com.linmour.order.pojo.Dto.SeatOrderDto;
import com.linmour.order.service.OrderInfoService;
import com.linmour.order.service.OrderItemService;
import com.linmour.order.utils.IdGenerateUtil;
import com.linmour.product.pojo.Dto.ProductDetailDto;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.linmour.security.exception.enums.AppHttpCodeEnum.ORDER_ITEM_NOT_NULL;
import static com.linmour.security.utils.SecurityUtils.getShopId;
import static com.linmour.order.constants.OrderConstant.*;


/**
 * @author linmour
 * @description 针对表【order_info】的数据库操作Service实现
 * @createDate 2023-08-16 15:25:38
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo>
        implements OrderInfoService {

    @Resource
    private OrderInfoMapper orderInfoMapper;
    @Resource
    private OrderItemService orderItemService;
    @Resource
    private OrderItemMapper orderItemMapper;
    @Resource
    private ProducerMq producerMq;
    @Resource
    private ProductFeign productFeign;
    @Resource
    private RedisCache redisCache;
    @Resource
    private RedissonClient redissonClient;


    @Override
    public Result createOrder(CreateOrderDto createOrderDto) {
        if (createOrderDto.getShopList().isEmpty())
            return Result.error(ORDER_ITEM_NOT_NULL);
        OrderInfo orderInfo = new OrderInfo();
        String orderId;
        String tableId = createOrderDto.getTableId().toString();
        String productKey = PRODUCT + ":" + getShopId();
        String orderInfoKey = ORDERINFO + ":" + getShopId() + ":" + tableId;
        String orderItemKey = ORDER_ITEM + ":" + getShopId() + ":" + tableId;

        //缓存中没有本桌订单信息
        if (ObjectUtil.isEmpty(redisCache.getAllHash(orderInfoKey))) {
            //创建订单基本信息
            orderId = String.valueOf(IdGenerateUtil.get().nextId());
            orderInfo.setId(orderId);
            orderInfo.setTableId(Long.parseLong(tableId));
            orderInfo.setPayAmount(createOrderDto.getAmount());
            orderInfo.setShopId(getShopId());
            orderInfo.setRemark(createOrderDto.getRemark());
            //转为map批量缓存订单信息
            Map<String, Object> ordereInfoMap = BeanUtil.beanToMap(orderInfo);
            redisCache.setHashValues(orderInfoKey, ordereInfoMap);
        } else {
            orderId = redisCache.getHashValue(orderInfoKey, "id").toString();
            //原有订单改变金额和备注
            redisCache.setHashValue(orderInfoKey, "remark", createOrderDto.getRemark());
            redisCache.setHashValue(orderInfoKey, "payAmount", createOrderDto.getAmount().add(new BigDecimal(redisCache.getHashValue(orderInfoKey, "payAmount").toString())));
            orderInfo.setId(orderId);
            orderInfo.setTableId(Long.parseLong(redisCache.getHashValue(orderInfoKey, "tableId").toString()));
            orderInfo.setPayAmount(new BigDecimal(redisCache.getHashValue(orderInfoKey, "payAmount").toString()));
            orderInfo.setShopId(getShopId());
            orderInfo.setRemark(redisCache.getHashValue(orderInfoKey, "remark").toString());
        }


        //订单条目表
        List<OrderItem> rList = new ArrayList<>();
        createOrderDto.getShopList().forEach(m -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);
            orderItem.setProductId(m.getId());
            orderItem.setQuantity(m.getNumber());
            orderItem.setShopId(getShopId());
            rList.add(orderItem);
        });
        redisCache.setCacheList(orderItemKey, rList);

        // ------------------------- 获取商品详细信息返回给商家端---------------------------------
        //获取这次订单的商品id
        List<Long> collect = rList.stream().map(OrderItem::getProductId).collect(Collectors.toList());
        //拿到缓存里的所有id
        Set<Long> productIds = redisCache.getAllHashKeys(productKey).stream()
                .map(Long::parseLong)
                .collect(Collectors.toSet());

        List<String> includedList = new ArrayList<>();
        List<Long> excludedList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        List<ProductDetailDto> productDetailDtos1 = new ArrayList<>();
        List<ProductDetailDto> listNew = new ArrayList<>();

        //缓存不为空
        if (!ObjectUtil.isEmpty(productIds)) {
            Map<Boolean, List<Long>> partitionedMap = collect.stream()
                    .collect(Collectors.partitioningBy(productIds::contains));
            //在缓存中的id
            includedList = partitionedMap.get(true).stream().map(String::valueOf).collect(Collectors.toList());
            //不在缓存中的id
            excludedList = partitionedMap.get(false);
            List<String> product = redisCache.getHashFields(productKey, includedList);
            productDetailDtos1 = mapper.convertValue(product, new TypeReference<List<ProductDetailDto>>() {
            });

        } else if (ObjectUtil.isEmpty(productIds)) {
            //查找缓存没有的
            Result result = productFeign.getProductDetails(collect);
            List<ProductDetailDto> data1 = (ArrayList<ProductDetailDto>) result.getData();
            listNew = mapper.convertValue(data1, new TypeReference<List<ProductDetailDto>>() {
            });
            //封装存入缓存的数据
            Map<String, ProductDetailDto> entries = new HashMap<>();
            listNew.forEach(m -> {
                entries.put(m.getId().toString(), m);
            });
            //存入缓存
            redisCache.setHashValues(productKey, entries);
        }
        if (!ObjectUtil.isEmpty(excludedList)) {
            //查找缓存没有的
            Result result = productFeign.getProductDetails(excludedList);
            List<ProductDetailDto> data1 = (ArrayList<ProductDetailDto>) result.getData();
            listNew.addAll(mapper.convertValue(data1, new TypeReference<List<ProductDetailDto>>() {
            }));
            //封装存入缓存的数据
            Map<String, ProductDetailDto> entries = new HashMap<>();
            listNew.forEach(m -> {
                entries.put(m.getId().toString(), m);
            });
            //存入缓存
            redisCache.setHashValues(productKey, entries);
        }
        listNew.addAll(productDetailDtos1);
        // 对data1进行修改
        listNew.forEach(m -> {
            rList.forEach(n -> {
                if (Objects.equals(m.getId(), n.getProductId())) {
                    m.setQuantity(n.getQuantity());
                }
            });
        });


        //推送到mq，由webstock推送给餐厅订单来了
        Result<Object> result1 = new Result<>();
        //为了方便拿到shopid
        result1.setMsg(getShopId().toString());
        SeatOrderDto seatOrderDto = new SeatOrderDto(orderInfo.getId(), orderInfo.getPayAmount().toString(), orderInfo.getTableId().toString(), listNew);
        result1.setData(seatOrderDto);
//            producerMq.orderCreationCompleted(result1);


        return result1;

    }


    @Override
    public Result checkout(CheckoutDto checkoutDto) {
        String tableId = checkoutDto.getTableId().toString();
        String orderInfoKey = ORDERINFO + ":" + getShopId() + ":" + tableId;
        String orderItemKey = ORDER_ITEM + ":" + getShopId() + ":" + tableId;
        RLock lock = redissonClient.getLock("lock");
        lock.lock();
        try {
            //改变缓存中的付款状态等信息
            redisCache.setHashValue(orderInfoKey, "payType", checkoutDto.getPayType());
            redisCache.setHashValue(orderInfoKey, "payStatus", PAYMENT);
            if (checkoutDto.getPayType() != 4)
                //把结账人的openid存给订单，是一种妥协，因为这样只有结账的人能看见订单了
                redisCache.setHashValue(orderInfoKey, "openid", checkoutDto.getOpenid());
            //取出缓存中的数据然后存入数据库
            Map<String, Object> map = redisCache.getAllHash(orderInfoKey);
            //没有查到订单
            if (ObjectUtil.isEmpty(map)) {
                //反馈给前端
                producerMq.checkout(checkoutDto.getTableId());
                return Result.success();
            }

            //todo 调用支付相关接口

            //map转对象
            String s = JSON.toJSONString(map);
            OrderInfo orderInfo = JSON.parseObject(s, OrderInfo.class);
            orderInfoMapper.insert(orderInfo);
            List<Object> cacheList = redisCache.getCacheList(orderItemKey);
            List<OrderItem> list = JSONObject.parseArray(JSONObject.toJSONString(cacheList), OrderItem.class);
            orderItemService.saveBatch(list);
            //删除缓存中的订单信息
            redisCache.deleteObject(orderInfoKey);
            redisCache.deleteObject(orderItemKey);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return Result.success();
    }

    @Override
    public Result getOrderInfo(Long tableId) {
        //找出本桌所有订单
        OrderInfo orderInfo = orderInfoMapper.selectOne(new LambdaQueryWrapper<OrderInfo>()
                .eq(OrderInfo::getTableId, tableId)
                //未支付说明这一桌人还没结账，还有可能接着点餐，说明这个桌子还有人
                .eq(OrderInfo::getPayStatus, NON_PAYMENT)
        );
        if (ObjectUtil.isEmpty(orderInfo)) {
            return Result.error("没有订单");
        }
        //找到相对应的所有菜品id

        List<OrderItem> orderItems = orderItemMapper.selectList(new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getOrderId, orderInfo.getId()));

        List<Long> collect = orderItems.stream().map(OrderItem::getProductId).collect(Collectors.toList());
        Result result = productFeign.getProductDetails(collect);
        List<ProductDetailDto> data1 = (ArrayList<ProductDetailDto>) result.getData();
        ObjectMapper mapper = new ObjectMapper();
        List<ProductDetailDto> listNew = mapper.convertValue(data1, new TypeReference<List<ProductDetailDto>>() {
        });
        // 对data1进行修改
        listNew.stream().forEach(m -> {
            orderItems.stream().forEach(n -> {
                if (m.getId() == m.getId()) {
                    m.setQuantity(n.getQuantity());
                }
            });
        });

        Map<String, Object> obj = new HashMap<>();
        Map<String, Object> b = new HashMap<>();
        List<Map<String, Object>> a = new ArrayList<>();
        obj.put("orderInfoDtos", orderInfo);
        b.put("orderDetailDtos", listNew);
        a.add(b);
        obj.put("order", a);
        return Result.success(obj);
    }

    @Override
    public Result changeOrder(OrderInfoDto orderInfoDto) {
        orderInfoMapper.updateById(OrderInfoConvert.IN.OrderInfoDtoToOrderInfo(orderInfoDto));
        return Result.success();
    }
}




