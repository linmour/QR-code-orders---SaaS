package com.linmour.websocket.chain;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linmour.order.pojo.Dto.CreateOrderDto;
import com.linmour.order.pojo.Dto.ShopListDto;
import com.linmour.websocket.feign.OrderFeign;
import com.linmour.websocket.mq.ProducerMq;
import com.linmour.websocket.pojo.Result;
import com.linmour.websocket.ws.AppWebSocketServer;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.linmour.common.utils.SecurityUtils.setShopId;
import static com.linmour.websocket.ws.AppWebSocketServer.AppSendInfo;
import static com.linmour.websocket.ws.AppWebSocketServer.producerMq;
import static com.linmour.websocket.ws.WebSocketServer.sendInfo;
import static java.util.concurrent.TimeUnit.SECONDS;

@Component
//处理前端创建订单
public class CreateOrderHandler extends Handler {


    @Override
    public void handleRequest(ConcurrentHashMap<String, List<AppWebSocketServer>> webSocketMap,
                              JSONObject jsonObject,
                              ConcurrentHashMap<String, List<JSONObject>> recordMap,
                              AppWebSocketServer webSocket, OrderFeign orderFeign) throws IOException {


        if (jsonObject.containsKey("createOrder")) {

            RLock lock = redissonClient.getLock("lock");
            //todo 极端情况下还是会重复提交
            lock.lock();
            try {
                if (StringUtils.isNotBlank(webSocket.getTableId()) && webSocketMap.containsKey(webSocket.getTableId())) {
                    List<AppWebSocketServer> serverList = webSocketMap.get(webSocket.getTableId());
                    //有一个为true就说明已经有订单了
                    if (serverList.stream().anyMatch(m -> m.getCreateOrder().get())) {
                        webSocket.sendMessage("已有人提交订单，请稍后");
                        return;
                    }
                    BigDecimal amount = new BigDecimal((Integer) jsonObject.get("amount"));
                    String remark = jsonObject.get("remark").toString();
                    //类型转换:JSONArray转list
                    JSONArray shopCarList = jsonObject.getJSONArray("shopCarList");
                    List<ShopListDto> list = shopCarList.toJavaList(ShopListDto.class);
                    //TODO 1.加个拦截加个shopid
                    setShopId(list.get(0).getShopId());
//                    if (producerMq.createOrder(new CreateOrderDto(Long.parseLong(webSocket.getTableId()), amount, list, remark, list.get(0).getShopId())))
                    Result result = orderFeign.createOrder(new CreateOrderDto(Long.parseLong(webSocket.getTableId()), amount, list, remark, list.get(0).getShopId()));
                    if (result != null){
                        //手动设置id，因为这个没经过token认证没有创建security认证信息，只能手动设置，要不然结果会为null，引发一系列错误
                        setShopId(Long.parseLong(result.getMsg()));
                        result.setMsg("order");

                        try {
                            sendInfo(result, "1");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        AppSendInfo("订单提交成功", webSocket.getTableId());
                          recordMap.get(webSocket.getTableId()).clear();
                    }
                    else
                    AppSendInfo("订单提交失败", webSocket.getTableId());
                }
            } catch (Exception e) {
                e.printStackTrace();
                AppSendInfo("订单提交失败", webSocket.getTableId());
            } finally {
                lock.unlock();
            }
        } else {
            // 无法处理，传递给下一个处理器
            if (nextHandler != null) {
                nextHandler.handleRequest(webSocketMap, jsonObject, recordMap, webSocket, orderFeign);
            }
        }
    }
}
