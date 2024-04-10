package com.linmour.websocket.mq;

import com.linmour.security.utils.RedisCache;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.linmour.websocket.ws.WebSocketServer.sendInfo;

@Component
public class RedisSubscriber implements MessageListener {

    @Resource
    private RedisCache a;

    //注入为空
    public static RedisCache redisCache;

    @PostConstruct
    public void b() {
        redisCache = this.a;
    }


    @Override
    public void onMessage(Message message, byte[] pattern) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> allHash = redisCache.getAllHash("dataAnaly:orderSummaryDay:" + message);
        Map<String, Object> allHash1 = redisCache.getAllHash("dataAnaly:productSummaryDay:" + message);
        List<String> s = new ArrayList<>();
        s.add("晚餐");
        s.add("深夜");
        s.add("午餐");
        s.add("早餐");
        List< Map<String, Object>> list = new ArrayList<>();
        s.forEach(m ->{
            Map<String, Object> allHash2 = redisCache.getAllHash("dataAnaly:orderSummaryTimePeriod:" + message + ":" + m);
            list.add(allHash2);
        });
        map.put("orderSummaryDay",allHash);
        map.put("productSummaryDay",allHash1);
        map.put("timePeriodMap",list);
        try {
            sendInfo(map,message.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 处理收到的消息
        System.out.println("Received message: " + map);
    }
}