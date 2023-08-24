package com.linmour.websocket.mq;

import com.linmour.websocket.feign.OrderFeign;
import com.linmour.websocket.pojo.Result;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.io.IOException;

import static com.linmour.websocket.ws.WebSocketServer.sendInfo;

@Component
public class ConsumerMq {

    @Resource
    private OrderFeign orderFeign;

    @Service
    @RocketMQMessageListener(topic = "NEW_ORDER_TOPIC", consumerGroup = "orderPayTimeOut")
    public class orderPayTimeOut implements RocketMQListener<Long> {
        // 监听到消息就会执行此方法
        @Override
        public void onMessage(Long tableId) {
            Result orderInfo = orderFeign.getOrderInfo(tableId);
            orderInfo.setMsg("order");
            try {
                sendInfo(orderInfo,"1");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }


}
