package com.linmour.websocket.chain;

import com.alibaba.fastjson.JSONObject;
import com.linmour.websocket.feign.OrderFeign;
import com.linmour.websocket.ws.AppWebSocketServer;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Handler {
    protected Handler nextHandler;


    public Handler addNextHandler(Handler handler) {
        this.nextHandler = handler;
        return handler;
    }

    public void handleNext(ConcurrentHashMap<String, List<AppWebSocketServer>> webSocketMap,
                           JSONObject jsonObject,
                           ConcurrentHashMap<String, List<JSONObject>> recordMap,
                           AppWebSocketServer webSocke,
                           OrderFeign orderFeign) throws IOException {
        if (nextHandler != null) {
            nextHandler.handleRequest(webSocketMap, jsonObject, recordMap, webSocke, orderFeign);
        }
    }


    public abstract void handleRequest(ConcurrentHashMap<String, List<AppWebSocketServer>> webSocketMap,
                                       JSONObject jsonObject, ConcurrentHashMap<String,
                                       List<JSONObject>> recordMap,
                                       AppWebSocketServer webSocke, OrderFeign orderFeign) throws IOException;
}