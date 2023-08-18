package com.linmour.websocket.mq;


import com.linmour.order.pojo.Dto.OrderInfoDto;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.linmour.order.constants.MqConstant.NEW_ORDER_TOPIC;
import static com.linmour.websocket.ws.WebSocketServer.sendInfo;


@Component
public class ConsumerMq {



    @Service
    @RocketMQMessageListener(topic = NEW_ORDER_TOPIC, consumerGroup = "newOrder")
    public class newOrder implements RocketMQListener<OrderInfoDto> {
        // 监听到消息就会执行此方法
        @Override
        public void onMessage(OrderInfoDto dto) {
            System.out.println(dto);
            try {
                sendInfo(dto,"1");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



}
