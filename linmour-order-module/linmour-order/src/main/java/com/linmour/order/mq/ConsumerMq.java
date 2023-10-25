package com.linmour.order.mq;

import com.linmour.order.pojo.Do.OrderInfo;
import com.linmour.order.service.OrderInfoService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Objects;

import static com.linmour.common.constant.MqConstant.ORDER_PAY_TIMEOUT_TOPIC;
import static com.linmour.order.constants.OrderConstant.CANCEL_ORDER;
import static com.linmour.order.constants.OrderConstant.PAYMENT;

@Component
public class ConsumerMq {

    @Resource
    private OrderInfoService orderInfoService;



    @Service
    @RocketMQMessageListener(topic = ORDER_PAY_TIMEOUT_TOPIC, consumerGroup = "orderPayTimeOut")
    public class orderPayTimeOut implements RocketMQListener<String> {
        // 监听到消息就会执行此方法
        @Override
        public void onMessage(String orderId) {
            OrderInfo orderInfo = orderInfoService.getById(orderId);
            if (!Objects.equals(orderInfo.getPayStatus(), PAYMENT)){
                orderInfo.setPayStatus(CANCEL_ORDER);
            }
            orderInfoService.updateById(orderInfo);
        }
    }



}
