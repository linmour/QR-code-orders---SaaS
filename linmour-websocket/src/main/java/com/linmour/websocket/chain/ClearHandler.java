package com.linmour.websocket.chain;

import com.alibaba.fastjson.JSONObject;

import com.linmour.websocket.GrpcClientService3;
import com.linmour.websocket.ws.AppWebSocketServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

//处理前端订单已完成，把订单标志位置为false
@Component
public class ClearHandler extends Handler{

    @Override
    public void handleRequest(ConcurrentHashMap<String, List<AppWebSocketServer>> webSocketMap,
                              JSONObject jsonObject,
                              ConcurrentHashMap<String, List<JSONObject>> recordMap,
                              AppWebSocketServer webSocket, Session session, GrpcClientService3 grpcService) throws IOException {
        if (jsonObject.containsKey("clear")) {
            //情况订单记录
            recordMap.clear();
            if (StringUtils.isNotBlank(webSocket.getTableId()) && webSocketMap.containsKey(webSocket.getTableId())) {
                List<AppWebSocketServer> serverList = webSocketMap.get(webSocket.getTableId());
                //遍历所有对象，把订单都改为未提交，为了下一次点餐
                serverList.forEach(m -> m.getCreateOrder().set(false));

            }
        } else {
            // 无法处理，传递给下一个处理器
            if (nextHandler != null) {
                nextHandler.handleRequest(webSocketMap,jsonObject,recordMap,webSocket,session,grpcService);
            }
        }
    }
}
