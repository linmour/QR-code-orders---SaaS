package com.linmour.websocket.chain;

import com.alibaba.fastjson.JSONObject;
import com.linmour.websocket.feign.OrderFeign;
import com.linmour.websocket.ws.AppWebSocketServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.linmour.websocket.ws.AppWebSocketServer.AppSendInfo;
import static com.linmour.websocket.ws.AppWebSocketServer.producerMq;

//处理前端改变购物车的行为，并记录
public class ChangeHandler extends Handler {

    @Override
    public void handleRequest(ConcurrentHashMap<String, List<AppWebSocketServer>> webSocketMap,
                              JSONObject jsonObject, ConcurrentHashMap<String,
                              List<JSONObject>> recordMap,
                              AppWebSocketServer webSocke,
                              OrderFeign orderFeign) throws IOException {

        if (jsonObject.containsKey("change")) {
            ArrayList<JSONObject> jsonObjects = new ArrayList<>();
            jsonObjects.add(jsonObject);
            producerMq.syncShopCar(jsonObjects);
            AppSendInfo(jsonObjects, webSocke.getTableId());    
            //记录每一次购物车变化的记录
            List<JSONObject> objects = recordMap.get(webSocke.getTableId());
            objects.add(jsonObject);
        } else {
            // 无法处理，传递给下一个处理器
            if (nextHandler != null) {
                nextHandler.handleRequest(webSocketMap,jsonObject,recordMap,webSocke,orderFeign);
            }
        }
    }
}

