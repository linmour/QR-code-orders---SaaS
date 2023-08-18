package com.linmour.websocket.config;
import com.alibaba.fastjson.JSON;
import com.linmour.order.pojo.Dto.OrderInfoDto;


import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.util.Map;

/**
 * 为了websocket发送对象
 */
public class WebSocketCustomEncoding implements Encoder.Text<OrderInfoDto> {
    @Override
    public String encode(OrderInfoDto vo) {
        assert vo!=null;
        return JSON.toJSONString(vo);
    }
 
    @Override
    public void init(EndpointConfig endpointConfig) {
 
    }
    @Override
    public void destroy() {
 
    }
 
}