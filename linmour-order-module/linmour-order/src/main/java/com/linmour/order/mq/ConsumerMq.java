package com.linmour.order.mq;

import com.linmour.common.dtos.Result;
import com.linmour.order.convert.OrderInfoConvert;
import com.linmour.order.feign.ProductFeign;
import com.linmour.order.pojo.Do.OrderInfo;
import com.linmour.order.pojo.Dto.OrderInfoDto;
import com.linmour.order.service.OrderInfoService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Objects;

import static com.linmour.order.constants.MqConstant.ORDER_PAY_TIMEOUT_TOPIC;
import static com.linmour.order.constants.OrderConstant.CANCEL_ORDER;
import static com.linmour.order.constants.OrderConstant.PAYMENT;

@Component
public class ConsumerMq {

    @Resource
    private OrderInfoService orderInfoService;


    @Service
    @RocketMQMessageListener(topic = ORDER_PAY_TIMEOUT_TOPIC, consumerGroup = "orderDelay")
    public class orderPayTimeOut implements RocketMQListener<OrderInfoDto> {
        // 监听到消息就会执行此方法
        @Override
        public void onMessage(OrderInfoDto dto) {
            OrderInfo orderInfo = OrderInfoConvert.IN.OrderInfoDtoToOrderInfo(dto);
            if (!Objects.equals(orderInfo.getPayStatus(), PAYMENT)){
                orderInfo.setPayStatus(CANCEL_ORDER);
            }
            orderInfoService.updateById(orderInfo);
        }
    }



}
