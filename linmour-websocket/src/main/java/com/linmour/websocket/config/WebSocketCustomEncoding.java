package com.linmour.websocket.config;


import com.alibaba.fastjson.JSON;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.util.Map;

/**
 * 为了websocket发送对象
 */

public class WebSocketCustomEncoding implements Encoder.Text<Object> {
//      public String encode(Object vo)  这个就是指定发送的类型
    @Override
    public String encode(Object vo) {
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