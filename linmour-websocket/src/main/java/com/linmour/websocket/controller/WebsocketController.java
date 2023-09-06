package com.linmour.websocket.controller;

import com.linmour.websocket.feign.OrderFeign;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.io.IOException;

import static com.linmour.websocket.ws.WebSocketServer.sendInfo;

@RestController
@RequestMapping("/websocket")
public class WebsocketController {

    @Resource
    private OrderFeign orderFeign;
    @RequestMapping("/a")
    public Object a(){
        return orderFeign.getOrderInfo(1L);
    }

    @RequestMapping("/b")
    public void b()   {
        try {
             sendInfo(orderFeign.getOrderInfo(1L),"1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/b")
    public void c()   {
        try {
             sendInfo(orderFeign.getOrderInfo(1L),"1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
