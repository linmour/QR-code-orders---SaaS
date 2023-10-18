package com.linmour.websocket.mq;

import com.alibaba.fastjson.JSONObject;
import com.linmour.order.pojo.Dto.CreateOrderDto;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.List;

import static com.linmour.common.constant.MqConstant.CREATE_ORDER_TOPIC;
import static com.linmour.common.constant.MqConstant.SYNC_SHOP_CAR_TOPIC;


@Component
public class ProducerMq {

    @Value("${rocketmq.producer.send-message-timeout}")
    private Integer messageTimeOut;

    // 直接注入使用，用于发送消息到broker服务器
    @Resource
    private RocketMQTemplate rocketMQTemplate;




    public void syncShopCar(List<JSONObject> msgBody){
        isSuccess(rocketMQTemplate.syncSend(SYNC_SHOP_CAR_TOPIC, MessageBuilder.withPayload(msgBody).build(),messageTimeOut));
    }




//    public void createOrder(CreateOrderDto msgBody){
//        isSuccess(rocketMQTemplate.syncSend(CREATE_ORDER_TOPIC, MessageBuilder.withPayload(msgBody).build(),messageTimeOut));
//    }








    private static void isSuccess(SendResult result) {
        if (result.getSendStatus() == SendStatus.SEND_OK) {
            // 消息发送成功的处理逻辑
            System.out.println("消息发送成功，消息ID: " + result.getMsgId());
        } else {
            // 消息发送失败的处理逻辑
            System.out.println("消息发送失败，错误信息: " + result.getSendStatus().name());
        }
    }

}
