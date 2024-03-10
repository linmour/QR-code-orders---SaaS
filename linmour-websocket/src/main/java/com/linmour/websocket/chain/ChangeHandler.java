package com.linmour.websocket.chain;

import com.alibaba.fastjson.JSONObject;
import com.linmour.websocket.feign.OrderFeign;
import com.linmour.websocket.ws.AppWebSocketServer;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.linmour.websocket.ws.AppWebSocketServer.AppSendInfo;

//处理前端改变购物车的行为，并记录
@Component
public class ChangeHandler extends Handler {

    @Override
    public void handleRequest(ConcurrentHashMap<String, List<AppWebSocketServer>> webSocketMap,
                              JSONObject jsonObject,
                              ConcurrentHashMap<String, List<JSONObject>> recordMap,
                              AppWebSocketServer webSocket, OrderFeign orderFeign, Session session) throws IOException {

        if (jsonObject.containsKey("change")) {
            ArrayList<JSONObject> jsonObjects = new ArrayList<>();
            jsonObjects.add(jsonObject);
//            producerMq.syncShopCar(jsonObjects);
            String fromtableId = (String)(jsonObjects.get(0).get("fromtableId"));
            try {
                AppSendInfo(jsonObjects,fromtableId, session.getId(),false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //记录每一次购物车变化的记录
            List<JSONObject> objects = recordMap.get(webSocket.getTableId());
            objects.add(jsonObject);
        } else {
            // 无法处理，传递给下一个处理器
            if (nextHandler != null) {
                nextHandler.handleRequest(webSocketMap, jsonObject, recordMap, webSocket,orderFeign,session);
            }
        }
    }
}

