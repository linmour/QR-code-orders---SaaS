package com.linmour.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmour.common.dtos.Result;
import com.linmour.order.convert.OrderInfoConvert;
import com.linmour.order.convert.OrderInfoDtoConvert;
import com.linmour.order.feign.ProductFeign;
import com.linmour.order.mapper.OrderInfoMapper;
import com.linmour.order.mapper.ROrderPreductMapper;
import com.linmour.order.mq.ProducerMq;
import com.linmour.order.pojo.Do.OrderInfo;
import com.linmour.order.pojo.Do.ROrderPreduct;
import com.linmour.order.pojo.Dto.CreateOrderDto;
import com.linmour.order.pojo.Dto.OrderInfoDto;
import com.linmour.order.pojo.Dto.SubmitOrderDto;
import com.linmour.order.pojo.Dto.OrderDetailDto;
import com.linmour.order.service.OrderInfoService;
import com.linmour.order.service.ROrderPreductService;
import com.linmour.order.utils.IdGenerateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.linmour.common.utils.SecurityUtils.getShopId;
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
    private ROrderPreductService rOrderPreductService;
    @Resource
    private ROrderPreductMapper rOrderPreductMapper;
    @Resource
    private ProducerMq producerMq;
    @Resource
    private ProductFeign productFeign;


    @Override
    public Result createOrder(CreateOrderDto createOrderDto) {
        //创建订单基本信息
        String orderId = String.valueOf(IdGenerateUtil.get().nextId());
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(orderId);
        orderInfo.setPayStatus(2);
        orderInfo.setTableId(createOrderDto.getTableId());
        orderInfo.setPayAmount(createOrderDto.getAmount());
        orderInfo.setOrderStatus(3);
        orderInfo.setShopId(getShopId());
        orderInfoMapper.insert(orderInfo);

        //订单和点菜关系表
        ArrayList<ROrderPreduct> rList = new ArrayList<>();
        createOrderDto.getShopList().forEach(m ->{
            ROrderPreduct rOrderPreduct = new ROrderPreduct();
            rOrderPreduct.setOrderId(orderInfo.getId());
            rOrderPreduct.setProductId(m.getId());
            rOrderPreduct.setQuantity(m.getSelectNum());
            rList.add(rOrderPreduct);
        });
        rOrderPreductService.saveBatch(rList);

        //序列化有问题，只能这么办，LocalTime会报错，直接转为String
        OrderInfoDto orderInfoDto = OrderInfoDtoConvert.IN.OrderInfoToOrderInfoDto(orderInfo);

        //创建延时队列

        producerMq.orderPayTimeOut(orderInfoDto.getId(), 4);

        //转为字符串是因为前端会丢失精度
        return Result.success(orderInfo.getId());
    }



    @Override
    public Result submitOrder(SubmitOrderDto submitOrderDto) {
        orderInfoMapper.update(null, new LambdaUpdateWrapper<OrderInfo>().eq(OrderInfo::getId, submitOrderDto.getOrderNo())
                .set(OrderInfo::getPayType, submitOrderDto.getPayType())
                .set(OrderInfo::getPayStatus,PAYMENT));
        //发送给餐厅服务
        OrderInfoDto orderInfoDto = OrderInfoDtoConvert.IN.OrderInfoToOrderInfoDto(orderInfoMapper.selectById(submitOrderDto.getOrderNo()));
        //封装shopId要不然没有id
        HashMap<String, String> map = new HashMap<>();
        map.put("tableId",orderInfoDto.getTableId().toString());
        map.put("shopId",getShopId().toString());
        producerMq.newOrder(map);
    }

    @Override
    public Result getOrderInfo(Long tableId) {
        //找出本桌所有订单
        List<OrderInfo> orderInfos = orderInfoMapper.selectList(new LambdaQueryWrapper<OrderInfo>()
                .eq(OrderInfo::getTableId, tableId)
                        .ne(OrderInfo::getOrderStatus,1)
                        .eq(OrderInfo::getPayStatus,1)
                );

        //找到相对应的所有菜品id
        List<String> orderIds = orderInfos.stream().map(m -> m.getId()).collect(Collectors.toList());
        //数组为查询条件用in
        List<ROrderPreduct> rOrderPreducts = rOrderPreductMapper.selectList(new LambdaQueryWrapper<ROrderPreduct>().in(ROrderPreduct::getOrderId, orderIds));
        //查询有几个订单
        List<OrderDetailDto> orderDetailDtos = new ArrayList<>();
        orderInfos.forEach(m ->{
            orderDetailDtos.add(new OrderDetailDto(m.getId()));
        });
        /**
         * 这一块是为了按订单分类菜品，主要是根据productId来做的
         */
        for (OrderDetailDto orderDetailDto : orderDetailDtos) {
            List<Long> productIds = new ArrayList();
            for (ROrderPreduct rOrderPreduct : rOrderPreducts) {
                if (Objects.equals(orderDetailDto.getOrderId(), rOrderPreduct.getOrderId())){
                    productIds.add(rOrderPreduct.getProductId());
                }
            }
            //这个保存的不返回前端，只是为了能过匹配相应的菜品id,为了避免循环查库
            orderDetailDto.setProductIds(productIds);
        }
        List<Map<String,Object>> a = new ArrayList<>();
        rOrderPreducts.stream().forEach(m ->{
            //TODO 通过feign拿到菜品的详细信息,调用的方法可以改进，要不然这样是循环查库
            Result result = productFeign.getProductDetails(m.getProductId());
            Map<String,Object> data = (Map<String, Object>) result.getData();
            //这个data就每个菜品的属性，有需要可以添加
            data.put("quantity",m.getQuantity());
            a.add(data);
        });
        //根据上面保存的productId来匹配订单中的菜，然后存入
        orderDetailDtos.forEach(n ->{
            List<Map<String,Object>> maps = new ArrayList<>();
            for (Long productId : n.getProductIds()) {
                for (Map<String, Object> map : a) {
                    if (String.valueOf(map.get("id")).equals(productId.toString())){
                        maps.add(map);
                    }
                }
                n.setProducts(maps);
            }
        });

        List<OrderInfoDto> orderInfoDtos = OrderInfoDtoConvert.IN.OrderInfoListToOrderInfoDtoList(orderInfos);

        Map<String, Object> obj = new HashMap<>();
        obj.put("orderInfoDtos" ,orderInfoDtos);
        obj.put("orderDetailDtos", orderDetailDtos);
        return Result.success(obj);
    }

    @Override
    public Result changeOrder(OrderInfoDto orderInfoDto) {
        orderInfoMapper.updateById(OrderInfoConvert.IN.OrderInfoDtoToOrderInfo(orderInfoDto));
        return Result.success();
    }
}




