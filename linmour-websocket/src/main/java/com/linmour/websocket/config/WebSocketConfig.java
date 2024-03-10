package com.linmour.websocket.config;

import com.linmour.websocket.ws.AppWebSocketServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.annotation.Resource;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;


@Configuration
public class WebSocketConfig  extends ServerEndpointConfig.Configurator {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
    @Resource
    private AppWebSocketServer appWebSocketServer;
    /**
     * 重写modifyHandshake方法,获取Sec-WebSocket-Protocol请求头
     * @param sec
     * @param request
     * @param response
     */
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        //当Sec-WebSocket-Protocol请求头不为空时,需要返回给前端相同的响应
//        List<String> resp = response.getHeaders().put("Sec-Websocket-Protocol", request.getHeaders().get("Sec-Websocket-Protocol"));
//        try {
//            appWebSocketServer.sendMessage("1");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        super.modifyHandshake(sec, request, response);
    }

}
