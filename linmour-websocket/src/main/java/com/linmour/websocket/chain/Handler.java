package com.linmour.websocket.chain;

import com.alibaba.fastjson.JSONObject;

import com.linmour.websocket.GrpcClientService3;
import com.linmour.websocket.ws.AppWebSocketServer;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.Session;
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

    protected Handler nextHandler;

    public Handler addNextHandler(Handler handler) {
        this.nextHandler = handler;
        return handler;
    }



    public abstract void handleRequest(ConcurrentHashMap<String, List<AppWebSocketServer>> webSocketMap,
                                       JSONObject jsonObject,
                                       ConcurrentHashMap<String, List<JSONObject>> recordMap,
                                       AppWebSocketServer webSocket, Session session, GrpcClientService3 grpcService) throws IOException;
}