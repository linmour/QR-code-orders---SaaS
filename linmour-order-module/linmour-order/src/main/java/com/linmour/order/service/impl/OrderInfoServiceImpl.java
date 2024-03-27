package com.linmour.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linmour.order.pojo.Dto.*;
import com.linmour.security.dtos.Result;
import com.linmour.security.utils.RedisCache;
import com.linmour.order.convert.OrderInfoConvert;
import com.linmour.order.feign.ProductFeign;
import com.linmour.order.mapper.OrderInfoMapper;
import com.linmour.order.mapper.OrderItemMapper;
import com.linmour.order.mq.ProducerMq;
import com.linmour.order.pojo.Do.OrderInfo;
import com.linmour.order.pojo.Do.OrderItem;
import com.linmour.order.service.OrderInfoService;
import com.linmour.order.service.OrderItemService;
import com.linmour.order.utils.IdGenerateUtil;
import com.linmour.product.pojo.Dto.ProductDetailDto;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
        if (createOrderDto.getOrderItems().isEmpty())
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
            orderInfo.setCreateTime(LocalDateTime.now());
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

        //添加新点的菜
        List<OrderItem> rList = createOrderDto.getOrderItems();
        rList.forEach(m -> {
            m.setOrderId(orderId);
            m.setShopId(getShopId());
        });


        // ------------------------- 获取商品详细信息返回给商家端---------------------------------
        //获取这次订单的商品id
        List<Long> productIds = rList.stream().map(OrderItem::getProductId).collect(Collectors.toList());
        //拿到缓存里的所有id
        Set<Long> cacheProductIds = redisCache.getAllHashKeys(productKey).stream()
                .map(Long::parseLong)
                .collect(Collectors.toSet());

        List<String> includedList = new ArrayList<>();
        List<Long> excludedList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        List<OrderItem> cacheProductList = new ArrayList<>();

        //缓存不为空
        if (!ObjectUtil.isEmpty(cacheProductIds)) {
            Map<Boolean, List<Long>> partitionedMap = productIds.stream()
                    .collect(Collectors.partitioningBy(cacheProductIds::contains));
            //在缓存中的id
            includedList = partitionedMap.get(true).stream().map(String::valueOf).collect(Collectors.toList());
            //不在缓存中的id
            excludedList = partitionedMap.get(false);
            List<String> product = redisCache.getHashFields(productKey, includedList);
            cacheProductList = mapper.convertValue(product, new TypeReference<List<OrderItem>>() {
            });

        } else if (ObjectUtil.isEmpty(cacheProductIds)) {
            //查找缓存没有的
            Result result = productFeign.getProductDetails(productIds);
            List<ProductDetailDto> data1 = (ArrayList<ProductDetailDto>) result.getData();
            cacheProductList = mapper.convertValue(data1, new TypeReference<List<OrderItem>>() {
            });
            //封装存入缓存的数据
            Map<String, OrderItem> entries = new HashMap<>();
            cacheProductList.forEach(m -> {
                entries.put(m.getId().toString(), m);
            });
            //存入缓存
            redisCache.setHashValues(productKey, entries);
        }

        if (!ObjectUtil.isEmpty(excludedList)) {
            //查找缓存没有的
            Result result = productFeign.getProductDetails(excludedList);
            List<ProductDetailDto> data1 = (ArrayList<ProductDetailDto>) result.getData();
            rList.addAll(mapper.convertValue(data1, new TypeReference<List<OrderItem>>() {
            }));
            //封装存入缓存的数据
            Map<String, OrderItem> entries = new HashMap<>();
            rList.forEach(m -> {
                entries.put(m.getProductId().toString(), m);
            });
            //存入缓存
            redisCache.setHashValues(productKey, entries);
        }
        cacheProductList.forEach(n -> {
            rList.forEach(m -> {
                if (n.getId().equals(m.getProductId())) {
                    m.setSort(n.getSort());
                    m.setSortId(n.getSortId());
                    BeanUtil.copyProperties(m,new OrderItem());
                }
            });
        });

        //推送到mq，由webstock推送给餐厅订单来了
        Result<Object> result1 = new Result<>();
        //为了方便拿到shopid
        result1.setMsg(getShopId().toString());
        SeatOrderDto seatOrderDto = new SeatOrderDto(orderInfo.getId(), orderInfo.getPayAmount().toString(), orderInfo.getTableId().toString(), rList);
        result1.setData(seatOrderDto);
//            producerMq.orderCreationCompleted(result1);
        redisCache.setCacheList(orderItemKey, rList);

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
//                producerMq.checkout(checkoutDto.getTableId());
                return Result.success();
            }

            //todo 调用支付相关接口

            //map转对象
            String s = JSON.toJSONString(map);
            OrderInfo orderInfo = JSON.parseObject(s, OrderInfo.class);
            orderInfo.setOpenid(checkoutDto.getOpenid());
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
    public OrderAllDto GetCurrentOrderInfo(Long tableId) {
        OrderAllDto orderAllDto = new OrderAllDto();

        String orderInfoKey = ORDERINFO + ":" + getShopId() + ":" + tableId;
        String orderItemKey = ORDER_ITEM + ":" + getShopId() + ":" + tableId;
        Map<String, Object> map = redisCache.getAllHash(orderInfoKey);
        if (map.size() == 0) {
            return orderAllDto;
        }
        String s = JSON.toJSONString(map);
        OrderInfo orderInfo = JSON.parseObject(s, OrderInfo.class);

        //找到相对应的所有菜品id
        List<Object> cacheList = redisCache.getCacheList(orderItemKey);
        List<OrderItem> orderItems = JSONObject.parseArray(JSONObject.toJSONString(cacheList), OrderItem.class);
//        List<ProductDetailDto> listNew = OrderInfoConvert.IN.cover(orderItems);
//        List<Long> collect = orderItems.stream().map(OrderItem::getProductId).collect(Collectors.toList());
//        Result result = productFeign.getProductDetails(collect);
//        List<ProductDetailDto> data1 = (ArrayList<ProductDetailDto>) result.getData();
//        ObjectMapper mapper = new ObjectMapper();
//        List<ProductDetailDto> listNew = mapper.convertValue(data1, new TypeReference<List<ProductDetailDto>>() {
//        });
//        // 对data1进行修改
//        listNew.forEach(m -> {
//            orderItems.forEach(n -> {
//                if (m.getId().equals(n.getId())) {
//                    if (m.getQuantity() != null)
//                    m.setQuantity(m.getQuantity() + n.getQuantity());
//                    else
//                        m.setQuantity( n.getQuantity());
//
//                }
//            });
//        });
        orderAllDto.setOrderInfo(orderInfo);
        orderAllDto.setOrderItems(orderItems);
        return orderAllDto;
    }

    @Override
    public Result changeOrder(OrderInfoDto orderInfoDto) {
        orderInfoMapper.updateById(OrderInfoConvert.IN.OrderInfoDtoToOrderInfo(orderInfoDto));
        return Result.success();
    }

    @Override
    public List<OrderAllDto> GetHistoryOrderList(Long tableId) {
        //找出本桌所有订单
        List<OrderInfo> orderInfos = orderInfoMapper.selectList(new LambdaQueryWrapper<OrderInfo>()
                .eq(OrderInfo::getTableId, tableId)
                //未支付说明这一桌人还没结账，还有可能接着点餐，说明这个桌子还有人
                .eq(OrderInfo::getPayStatus, PAYMENT)
        );
        List<OrderAllDto> orderAllDtos = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        orderInfos.forEach(m -> {
            OrderAllDto orderAllDto = new OrderAllDto();
            //这一桌的菜
            List<OrderItem> list = orderItemMapper.selectList(new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getOrderId, m.getId()));
            List<Long> collect = list.stream().map(OrderItem::getId).collect(Collectors.toList());
            Result result = productFeign.getProductDetails(collect);
            List<OrderItem> data1 = (ArrayList<OrderItem>) result.getData();
            List<OrderItem> listNew = mapper.convertValue(data1, new TypeReference<List<OrderItem>>() {
            });
            // 对data1进行修改
//            listNew.forEach(i -> {
//                list.forEach(n -> {
//                    if (i.get.equals(n.getId())) {
//                        i.setQuantity(n.getQuantity());
//                    }
//                });
//            });
            orderAllDto.setOrderInfo(m);
            orderAllDto.setOrderItems(listNew);
            orderAllDtos.add(orderAllDto);
        });
        Collections.reverse(orderAllDtos);
        return orderAllDtos;
    }

    @Override
    public OrderAllDto GetOrderInfoDetail(Long orderId) {
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        List<OrderItem> list = orderItemMapper.selectList(new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getOrderId, orderId));
//        List<Long> collect = list.stream().map(OrderItem::getProductId).collect(Collectors.toList());
//        Result result = productFeign.getProductDetails(collect);
//        List<OrderItem> data1 = new ArrayList<>();
//        if (result.getData() instanceof List) {
//            List<Map<String, Object>> dataList = (List<Map<String, Object>>) result.getData();
//            for (Map<String, Object> map : dataList) {
//                // 进行逐个转换
//                ObjectMapper mapper = new ObjectMapper();
//                OrderItem orderItem = mapper.convertValue(map, OrderItem.class);
//                data1.add(orderItem);
//            }
//        }
//        list.forEach(m -> {
//            data1.forEach(n -> {
//                if (m.getId().equals(n.getId())) {
//                    n.setQuantity(m.getQuantity());
//                }
//            });
//        });
        OrderAllDto orderAllDto = new OrderAllDto();
        orderAllDto.setOrderInfo(orderInfo);
        orderAllDto.setOrderItems(list);
        return orderAllDto;
    }
}




