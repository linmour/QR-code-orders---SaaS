package com.linmour.order.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linmour.common.dtos.Result;
import com.linmour.common.utils.RedisCache;
import com.linmour.order.convert.OrderInfoConvert;
import com.linmour.order.convert.OrderInfoDtoConvert;
import com.linmour.order.feign.ProductFeign;
import com.linmour.order.mapper.OrderInfoMapper;
import com.linmour.order.mapper.ROrderPreductMapper;
import com.linmour.order.mq.ProducerMq;
import com.linmour.order.pojo.Do.OrderInfo;
import com.linmour.order.pojo.Do.ROrderProduct;
import com.linmour.order.pojo.Dto.CreateOrderDto;
import com.linmour.order.pojo.Dto.OrderInfoDto;
import com.linmour.order.pojo.Dto.CheckoutDto;
import com.linmour.order.service.OrderInfoService;
import com.linmour.order.service.ROrderProductService;
import com.linmour.order.utils.IdGenerateUtil;
import com.linmour.product.pojo.Dto.ProductDetailDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.linmour.common.utils.SecurityUtils.getShopId;
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
    private ROrderProductService rOrderProductService;
    @Resource
    private ROrderPreductMapper rOrderPreductMapper;
    @Resource
    private ProducerMq producerMq;
    @Resource
    private ProductFeign productFeign;
    @Resource
    private RedisCache redisCache;


    @Override
    public Result createOrder(CreateOrderDto createOrderDto) {


        OrderInfo orderInfo = new OrderInfo();
        String orderId = "";
        String tableId = createOrderDto.getTableId().toString();
        String productKey = PRODUCT + ":" + getShopId();
        String tableKey = TABLE + ":" + getShopId()  + ":" + tableId;
        

        if (ObjectUtil.isEmpty(redisCache.getCacheObject(tableKey))) {
            //创建订单基本信息
            orderId = String.valueOf(IdGenerateUtil.get().nextId());
            orderInfo.setId(orderId);
            orderInfo.setTableId(Long.parseLong(tableId));
            orderInfo.setPayAmount(createOrderDto.getAmount());
            orderInfo.setShopId(getShopId());
            orderInfo.setRemark(createOrderDto.getRemark());
            orderInfoMapper.insert(orderInfo);
            redisCache.setCacheObject(tableKey, orderId);
        } else {
            //从缓存中拿到订单号
            Object cacheObject = redisCache.getCacheObject(tableKey);
            orderInfo = orderInfoMapper.selectById(Long.parseLong((String) cacheObject));
            orderInfo.setPayAmount(orderInfo.getPayAmount().add(createOrderDto.getAmount()));
            orderInfo.setRemark(createOrderDto.getRemark());
            orderInfoMapper.updateById(orderInfo);
        }
        //订单和点菜的关系表
        List<ROrderProduct> rList = new ArrayList<>();
        OrderInfo finalOrderInfo = orderInfo;
        createOrderDto.getShopList().forEach(m -> {
            ROrderProduct rOrderProduct = new ROrderProduct();
            rOrderProduct.setOrderId(finalOrderInfo.getId());
            rOrderProduct.setProductId(m.getId());
            rOrderProduct.setQuantity(m.getSelectNum());
            rList.add(rOrderProduct);
        });
        rOrderProductService.saveBatch(rList);

        //获取这次订单的商品id
        List<Long> collect = rList.stream().map(ROrderProduct::getProductId).collect(Collectors.toList());
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

        } else if (ObjectUtil.isEmpty(productIds)){

            //查找缓存没有的
            Result result = productFeign.getProductDetails(collect);
            List<ProductDetailDto> data1 = (ArrayList<ProductDetailDto>) result.getData();
            listNew = mapper.convertValue(data1, new TypeReference<List<ProductDetailDto>>() {});
            //封装存入缓存的数据
            Map<String, ProductDetailDto> entries = new HashMap<>();
            listNew.forEach(m -> {
                entries.put(m.getId().toString(), m);
            });
            //存入缓存
            redisCache.setHashValues(productKey, entries);
        }
        if (!ObjectUtil.isEmpty(excludedList)){
            //查找缓存没有的
            Result result = productFeign.getProductDetails(excludedList);
            List<ProductDetailDto> data1 = (ArrayList<ProductDetailDto>) result.getData();
            listNew.addAll(mapper.convertValue(data1, new TypeReference<List<ProductDetailDto>>() {}));
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
        Map<String, Object> b = new HashMap<>();
        b.put("orderDetailDtos", listNew);

        List<Map<String, Object>> a = new ArrayList<>();
        a.add(b);

        Map<String, Object> obj = new HashMap<>();
        obj.put("orderInfoDtos", orderInfo);
        obj.put("order", a);


        //推送到mq，由webstock推送给餐厅订单来了
        Result<Object> result1 = new Result<>();
        //为了方便拿到shopid
        result1.setMsg(getShopId().toString());
        result1.setData(obj);
        producerMq.createOrder(result1);
        return Result.success();

    }


    @Override
    public Result checkout(CheckoutDto checkoutDto) {
        synchronized(this){
            String key = TABLE + ":" + getShopId() + ":" + checkoutDto.getTableId();
            String orderNo = (redisCache.getCacheObject(key));
            if (ObjectUtil.isNull(orderNo)){
                producerMq.checkout(checkoutDto.getTableId());
                return Result.success();
            }

            orderInfoMapper.update(null, new LambdaUpdateWrapper<OrderInfo>().eq(OrderInfo::getId, orderNo)
                    .set(OrderInfo::getPayType, checkoutDto.getPayType())
                    .set(OrderInfo::getPayStatus, PAYMENT));

            if (redisCache.deleteObject(key)){
                //...
            }

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

        List<ROrderProduct> rOrderProducts = rOrderPreductMapper.selectList(new LambdaQueryWrapper<ROrderProduct>().eq(ROrderProduct::getOrderId, orderInfo.getId()));

        List<Long> collect = rOrderProducts.stream().map(ROrderProduct::getProductId).collect(Collectors.toList());
        Result result = productFeign.getProductDetails(collect);
        List<ProductDetailDto> data1 = (ArrayList<ProductDetailDto>) result.getData();
        ObjectMapper mapper = new ObjectMapper();
        List<ProductDetailDto> listNew = mapper.convertValue(data1, new TypeReference<List<ProductDetailDto>>() {
        });
        // 对data1进行修改
        listNew.stream().forEach(m -> {
            rOrderProducts.stream().forEach(n -> {
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




