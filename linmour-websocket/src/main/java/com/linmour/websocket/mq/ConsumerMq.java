package com.linmour.websocket.mq;

import com.linmour.websocket.feign.OrderFeign;
import com.linmour.websocket.pojo.Result;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;

import static com.linmour.common.utils.SecurityUtils.setShopId;
import static com.linmour.websocket.ws.WebSocketServer.sendInfo;

@Component
public class ConsumerMq {

    @Resource
    private OrderFeign orderFeign;

    @Service
    @RocketMQMessageListener(topic = "NEW_ORDER_TOPIC", consumerGroup = "newOrder")
    public class newOrder implements RocketMQListener<HashMap<String, String>> {
        // 监听到消息就会执行此方法
        @Override
        public void onMessage( HashMap<String, String> map) {
            //手动设置id，因为这个没经过token认证没有创建security认证信息，只能手动设置，要不然结果会为null，引发一系列错误
            setShopId(Long.parseLong(map.get("shopId")));
            Result orderInfo = orderFeign.getOrderInfo(Long.parseLong(map.get("tableId")));
            orderInfo.setMsg("order");
            try {
                sendInfo(orderInfo,"1");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }


}
