package com.linmour.order.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
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
import com.linmour.order.pojo.Dto.SubmitOrderDto;
import com.linmour.order.pojo.Dto.OrderDetailDto;
import com.linmour.order.service.OrderInfoService;
import com.linmour.order.service.ROrderProductService;
import com.linmour.order.utils.IdGenerateUtil;
import com.linmour.product.pojo.Dto.ProductDetailDto;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.linmour.common.utils.SecurityUtils.getShopId;
import static com.linmour.order.constants.OrderConstant.NON_PAYMENT;
import static com.linmour.order.constants.OrderConstant.PAYMENT;


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
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public Result createOrder(CreateOrderDto createOrderDto) {
        //todo 这查询
//如果由缓存就不创建订单，直接查缓存

        OrderInfo orderInfo = new OrderInfo();
        String orderId = "";
        String tableId = createOrderDto.getTableId().toString();
//        else 创建订单，
        if (ObjectUtil.isEmpty(redisCache.getCacheObject("1"))){

            //创建订单基本信息
            orderId = String.valueOf(IdGenerateUtil.get().nextId());

            orderInfo.setId(orderId);
            orderInfo.setTableId(Long.parseLong(tableId));
            orderInfo.setPayAmount(createOrderDto.getAmount());
            orderInfo.setShopId(getShopId());
            orderInfoMapper.insert(orderInfo);
            redisCache.setCacheObject(tableId,orderId);


        }else {
            Object cacheObject = redisCache.getCacheObject(tableId.toString());
             orderInfo = orderInfoMapper.selectById(cacheObject.toString());
            orderInfo.setPayAmount(BigDecimal.valueOf(orderInfo.getPayAmount().compareTo(createOrderDto.getAmount())));
            orderInfoMapper.updateById(orderInfo);

        }
        //订单和点菜的关系表
        ArrayList<ROrderProduct> rList = new ArrayList<>();
        OrderInfo finalOrderInfo = orderInfo;
        createOrderDto.getShopList().forEach(m -> {
            ROrderProduct rOrderProduct = new ROrderProduct();
            rOrderProduct.setOrderId(finalOrderInfo.getId());
            rOrderProduct.setProductId(m.getId());
            rOrderProduct.setQuantity(m.getSelectNum());
            rList.add(rOrderProduct);
        });
        rOrderProductService.saveBatch(rList);

        //序列化有问题，只能这么办，LocalTime会报错，直接转为String
        //发送给餐厅服务
        OrderInfoDto orderInfoDto = OrderInfoDtoConvert.IN.OrderInfoToOrderInfoDto(orderInfo);

        Result orderInfo1 = getOrderInfo(orderInfoDto.getTableId());
        //为了方便拿到shopid
        orderInfo1.setMsg(getShopId().toString());
        //推送到mq，由webstock推送给餐厅订单来了
        producerMq.createOrder(orderInfo1);
        redisCache.setCacheObject(tableId.toString(),orderId);


        //转为字符串是因为前端会丢失精度
        return Result.success(orderInfo.getId());
    }


    @Override
    public Result submitOrder(SubmitOrderDto submitOrderDto) {
        orderInfoMapper.update(null, new LambdaUpdateWrapper<OrderInfo>().eq(OrderInfo::getId, submitOrderDto.getOrderNo())
                .set(OrderInfo::getPayType, submitOrderDto.getPayType())
                .set(OrderInfo::getPayStatus, PAYMENT));
        OrderInfo orderInfo = orderInfoMapper.selectById(submitOrderDto.getOrderNo());
        //发送给餐厅服务
        OrderInfoDto orderInfoDto = OrderInfoDtoConvert.IN.OrderInfoToOrderInfoDto(orderInfo);
        //封装shopId要不然没有id
        HashMap<String, String> map = new HashMap<>();
        map.put("tableId", orderInfoDto.getTableId().toString());
        map.put("shopId", getShopId().toString());
//        producerMq.createOrder(map);
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
        List<ProductDetailDto> listNew= mapper.convertValue(data1, new TypeReference<List<ProductDetailDto>>() { });
        // 对data1进行修改
        listNew.stream().forEach(m ->{
            rOrderProducts.stream().forEach(n ->{
                if (m.getId() == m.getId()){
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
obj.put("order",a);
        return Result.success(obj);
    }

    @Override
    public Result changeOrder(OrderInfoDto orderInfoDto) {
        orderInfoMapper.updateById(OrderInfoConvert.IN.OrderInfoDtoToOrderInfo(orderInfoDto));
        return Result.success();
    }
}




