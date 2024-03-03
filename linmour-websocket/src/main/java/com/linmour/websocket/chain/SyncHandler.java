package com.linmour.websocket.chain;

import com.alibaba.fastjson.JSONObject;
import com.linmour.websocket.feign.OrderFeign;
import com.linmour.websocket.ws.AppWebSocketServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

//处理前端初次扫码同步购物车
@Component
public class SyncHandler extends Handler {

    @Override
    public void handleRequest(ConcurrentHashMap<String, List<AppWebSocketServer>> webSocketMap,
                              JSONObject jsonObject,
                              ConcurrentHashMap<String, List<JSONObject>> recordMap,
                              AppWebSocketServer webSocket,OrderFeign orderFeign) throws IOException {
        if (jsonObject.containsKey("sync")) {
            //这个是判断是否有这个桌号，也就是 是否有人点过餐

            List<JSONObject> recordList = recordMap.get(webSocket.getTableId());
            //指定发送对象
            if (StringUtils.isNotBlank(webSocket.getTableId()) && webSocketMap.containsKey(webSocket.getTableId()) && recordList != null) {
                List<AppWebSocketServer> serverList = webSocketMap.get(webSocket.getTableId());
                for (AppWebSocketServer server : serverList) {
                    if (server.getSync().get()) {
                        server.sendMessage(recordList);
                    }

                }
            } else {
                ArrayList<JSONObject> objects = new ArrayList<>();
                recordMap.put(webSocket.getTableId(), objects);
            }
            webSocket.getSync().set(!webSocket.getSync().get());

        } else {
            // 无法处理，传递给下一个处理器
            if (nextHandler != null) {
                nextHandler.handleRequest(webSocketMap, jsonObject, recordMap, webSocket,orderFeign);
            }
        }
    }
}