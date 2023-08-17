package com.linmour.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmour.common.dtos.Result;
import com.linmour.order.convert.OrderInfoDtoConvert;
import com.linmour.order.mapper.OrderInfoMapper;
import com.linmour.order.mq.ProducerMq;
import com.linmour.order.pojo.Do.OrderInfo;
import com.linmour.order.pojo.Do.OrderProduct;
import com.linmour.order.pojo.Do.ROrderPreduct;
import com.linmour.order.pojo.Dto.CreateOrderDto;
import com.linmour.order.pojo.Dto.OrderInfoDto;
import com.linmour.order.pojo.Dto.SubmitOrderDto;
import com.linmour.order.service.OrderInfoService;
import com.linmour.order.service.OrderProductService;
import com.linmour.order.service.ROrderPreductService;
import com.linmour.order.utils.IdGenerateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.linmour.order.constants.constant.PAYMENT;


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
    private OrderProductService orderProductService;
    @Resource
    private ROrderPreductService rOrderPreductService;
    @Resource
    private ProducerMq producerMq;


    @Override
    public Result createOrder(CreateOrderDto createOrderDto) {
        //创建订单基本信息
        long orderId = IdGenerateUtil.get().nextId();
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(orderId);
        orderInfo.setPayStatus(2);
        orderInfo.setTableId(createOrderDto.getTableId());
        orderInfo.setPayAmount(createOrderDto.getAmount());
        orderInfo.setOrderStatus(3);
        orderInfoMapper.insert(orderInfo);

        //保存点的菜
        ArrayList<OrderProduct> list = new ArrayList<>();
        createOrderDto.getShopList().stream().forEach(m -> {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProductId(m.getId());
            orderProduct.setPrice(m.getPrice());
            orderProduct.setQuantity(m.getSelectNum());
            list.add(orderProduct);
        });
        orderProductService.saveBatch(list);

        //订单和点菜关系表
        ArrayList<ROrderPreduct> rList = new ArrayList<>();
        list.stream().forEach(m -> {
            ROrderPreduct rOrderPreduct = new ROrderPreduct();
            rOrderPreduct.setOrderId(orderInfo.getId());
            rOrderPreduct.setProductId(m.getId());
            rList.add(rOrderPreduct);
        });
        rOrderPreductService.saveBatch(rList);
        //序列化有问题，只能这么办
        OrderInfoDto orderInfoDto = OrderInfoDtoConvert.IN.OrderInfoToOrderInfoDto(orderInfo);

        //创建延时队列
        producerMq.orderDelay(orderInfoDto, 4);

        //转为字符串是因为前端会丢失精度
        return Result.success(orderInfo.getId().toString());
    }



    @Override
    public Result submitOrder(SubmitOrderDto submitOrderDto) {
        orderInfoMapper.update(null, new LambdaUpdateWrapper<OrderInfo>().eq(OrderInfo::getId, submitOrderDto.getOrderNo())
                .set(OrderInfo::getPayType, submitOrderDto.getPayType())
                .set(OrderInfo::getPayStatus,PAYMENT));
        return null;
    }
}




