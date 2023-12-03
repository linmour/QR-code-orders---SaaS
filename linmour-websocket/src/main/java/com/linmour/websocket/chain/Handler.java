package com.linmour.websocket.chain;

import com.alibaba.fastjson.JSONObject;
import com.linmour.websocket.feign.OrderFeign;
import com.linmour.websocket.mq.ProducerMq;
import com.linmour.websocket.ws.AppWebSocketServer;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
@Component
public abstract class Handler {
    public static RedissonClient redissonClient;
    @Resource
    public void setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public static OrderFeign orderFeign;
    @Resource
    public void setOrderFeign(OrderFeign orderFeign) {
        this.orderFeign = orderFeign;
    }
    protected Handler nextHandler;

    public Handler addNextHandler(Handler handler) {
        this.nextHandler = handler;
        return handler;
    }



    public abstract void handleRequest(ConcurrentHashMap<String, List<AppWebSocketServer>> webSocketMap,
                                       JSONObject jsonObject,
                                       ConcurrentHashMap<String, List<JSONObject>> recordMap,
                                       AppWebSocketServer webSocke) throws IOException;
}