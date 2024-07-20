package com.linmour.websocket.mq;//package com.linmour.websocket.mq;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.linmour.order.pojo.Dto.CreateOrderDto;
//import com.linmour.security.dtos.Result;
//
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
//import static com.linmour.security.constant.MqConstant.*;
//import static com.linmour.websocket.ws.AppWebSocketServer.AppSendInfo;
//import static com.linmour.websocket.ws.AppWebSocketServer.producerMq;
//import static com.linmour.websocket.ws.WebSocketServer.sendInfo;
//
//@Component
//public class ConsumerMq {
//
//    @Resource
//    private OrderFeign orderFeign;
//
//    @Service
//    @RocketMQMessageListener(topic = ORDER_CREATION_COMPLETED_TOPIC, consumerGroup = "orderCreationCompleted")
//    public class orderCreationCompleted implements RocketMQListener<Result> {
//        // 监听到消息就会执行此方法
//        @Override
//        public void onMessage( Result result) {
//            //手动设置id，因为这个没经过token认证没有创建security认证信息，只能手动设置，要不然结果会为null，引发一系列错误
////            setShopId(Long.parseLong(result.getMsg()));
//            result.setMsg("order");
//
//            try {
//                sendInfo(result,"1");
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//
//        }
//    }
//
//    @Service
//    @RocketMQMessageListener(topic = SYNC_SHOP_CAR_TOPIC,consumerGroup = "syncShopCar")
//    public class syncShopCar implements RocketMQListener<List<JSONObject>>{
//        @Override
//        public void onMessage(List<JSONObject>  obj) {
//            String fromtableId = (String)(obj.get(0).get("fromtableId"));
//            try {
//                AppSendInfo(obj,fromtableId,null);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//    @Service
//    @RocketMQMessageListener(topic = CHECKOUT_TOPIC,consumerGroup = "checkout")
//    public class checkout implements RocketMQListener<String>{
//
//        @Override
//        public void onMessage(String tableId) {
//
//            try {
//                AppSendInfo("没有要结算的订单",tableId,null);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//
//        }
//    }
//
//    @Service
//    @RocketMQMessageListener(topic = ERROR_TOPIC,consumerGroup = "error")
//    public class error implements RocketMQListener<Map<String,Object>>{
//
//        @Override
//        public void onMessage(Map<String,Object> map) {
//
//
//            if (map.containsKey(CREATE_ORDER_TOPIC)){
//
//                String jsonStr = JSON.toJSONString( map.get(CREATE_ORDER_TOPIC));
//                CreateOrderDto createOrderDto = JSON.parseObject(jsonStr, CreateOrderDto.class);
//                producerMq.createOrder(createOrderDto);
//            }
//
//        }
//    }
//
//
//}
