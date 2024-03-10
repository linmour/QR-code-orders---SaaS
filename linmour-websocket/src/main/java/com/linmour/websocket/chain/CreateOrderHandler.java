package com.linmour.websocket.chain;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linmour.order.pojo.Dto.CreateOrderDto;
import com.linmour.order.pojo.Dto.ShopListDto;
import com.linmour.security.dtos.Result;
import com.linmour.websocket.feign.OrderFeign;
import com.linmour.websocket.ws.AppWebSocketServer;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.linmour.security.utils.SecurityUtils.getShopId;
import static com.linmour.websocket.ws.AppWebSocketServer.AppSendInfo;
import static com.linmour.websocket.ws.WebSocketServer.sendInfo;

@Component
//处理前端创建订单
public class CreateOrderHandler extends Handler {


    @Override
    public void handleRequest(ConcurrentHashMap<String, List<AppWebSocketServer>> webSocketMap,
                              JSONObject jsonObject,
                              ConcurrentHashMap<String, List<JSONObject>> recordMap,
                              AppWebSocketServer webSocket, OrderFeign orderFeign, Session session) throws IOException {


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
//                    if (producerMq.createOrder(new CreateOrderDto(Long.parseLong(webSocket.getTableId()), amount, list, remark, list.get(0).getShopId())))
                    Result result = orderFeign.createOrder(new CreateOrderDto(Long.parseLong(webSocket.getTableId()), amount, list, remark, getShopId()));
                    String a = "本桌订单提交成功";
                    if (result != null || result.getMsg().equals("出现错误")) {
                        result.setMsg("order");
                        //发送给餐厅
                        sendInfo(result, String.valueOf(getShopId()));
                        recordMap.get(webSocket.getTableId()).clear();
                    } else {
                        a = "本桌订单提交失败";
                    }
                    for (AppWebSocketServer server : serverList) {
                        //发送给请求同步的人
                        if (server.getSession().getId().equals(session.getId())) {
                            AppSendInfo(a, webSocket.getTableId(), null, true);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                AppSendInfo("本桌订单提交失败", webSocket.getTableId(), null, true);
            } finally {
                lock.unlock();
            }
        } else {
            // 无法处理，传递给下一个处理器
            if (nextHandler != null) {
                nextHandler.handleRequest(webSocketMap, jsonObject, recordMap, webSocket, orderFeign, session);
            }
        }
    }
}
