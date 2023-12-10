package com.linmour.websocket.chain;

import com.alibaba.fastjson.JSONObject;
import com.linmour.websocket.feign.OrderFeign;
import com.linmour.websocket.ws.AppWebSocketServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.linmour.websocket.ws.AppWebSocketServer.AppSendInfo;


//兜底处理器
@Component
public class OtherHandler extends Handler{
    @Override
    public void handleRequest(ConcurrentHashMap<String, List<AppWebSocketServer>> webSocketMap,
                              JSONObject jsonObject,
                              ConcurrentHashMap<String, List<JSONObject>> recordMap,
                              AppWebSocketServer webSocket) throws IOException {

        //传送给对应tableId用户的websocket
        if (StringUtils.isNotBlank(webSocket.getTableId()) && webSocketMap.containsKey(webSocket.getTableId())) {
            AppSendInfo("1", webSocket.getTableId());
        } else {
            System.out.println("请求的tableId:" + webSocket.getTableId() + "不在该服务器上");
        }
    }
}
