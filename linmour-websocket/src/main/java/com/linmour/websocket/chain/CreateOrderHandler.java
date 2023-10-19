package com.linmour.websocket.chain;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.linmour.order.pojo.Dto.CreateOrderDto;
import com.linmour.order.pojo.Dto.ShopListDto;
import com.linmour.websocket.feign.OrderFeign;
import com.linmour.websocket.pojo.Result;
import com.linmour.websocket.ws.AppWebSocketServer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.linmour.common.utils.SecurityUtils.setShopId;
import static com.linmour.websocket.ws.AppWebSocketServer.AppSendInfo;

//处理前端创建订单
public class CreateOrderHandler extends Handler{

    @Override
    public void handleRequest(ConcurrentHashMap<String, List<AppWebSocketServer>> webSocketMap,
                              JSONObject jsonObject, ConcurrentHashMap<String,
                              List<JSONObject>> recordMap,
                              AppWebSocketServer webSocke,
                              OrderFeign orderFeign) throws IOException {


        if (jsonObject.containsKey("createOrder")) {
            if (StringUtils.isNotBlank(webSocke.getTableId()) && webSocketMap.containsKey(webSocke.getTableId())) {
                List<AppWebSocketServer> serverList = webSocketMap.get(webSocke.getTableId());
                //有一个为true就说明已经有订单了
                if (serverList.stream().anyMatch(m -> m.getCreateOrder().get())) {
                    webSocke.sendMessage("已有人提交订单，请稍后");
                    return;
                }
            }
            synchronized (webSocke) {
                if (StringUtils.isNotBlank(webSocke.getTableId()) && webSocketMap.containsKey(webSocke.getTableId())) {
                    List<AppWebSocketServer> serverList = webSocketMap.get(webSocke.getTableId());
                    //有一个为true就说明已经有订单了
                    if (serverList.stream().anyMatch(m -> m.getCreateOrder().get())) {
                        webSocke.sendMessage("已有人提交订单，请稍后");
                        return;
                    }

                    BigDecimal amount = new BigDecimal((Integer) jsonObject.get("amount"));
                    JSONArray shopCarList = jsonObject.getJSONArray("shopCarList");
                    List<ShopListDto> list=shopCarList.toJavaList(ShopListDto.class);
                    //TODO 加个拦截加个shopid
                    setShopId(list.get(0).getShopId());
                    //todo 这过程失败的话，既没有生成订单，用户的购物车也没了
                    try {
                        orderFeign.createOrder(new CreateOrderDto(Long.parseLong(webSocke.getTableId()), amount, list, ""));
                        //通知清空购物车
                        AppSendInfo("订单创建成功", webSocke.getTableId());
                        //清空本地的购物记录
                        recordMap.get(webSocke.getTableId()).clear();
                        webSocke.getCreateOrder().set(true);

                    }catch (Exception e){
                        e.printStackTrace();
                        //TODO 这个发不了信息
                        AppSendInfo("订单提交失败",webSocke.getTableId());
                    }

                }
            }

        } else {
            // 无法处理，传递给下一个处理器
            if (nextHandler != null) {
                nextHandler.handleRequest(webSocketMap,jsonObject,recordMap,webSocke,orderFeign);
            }
        }
    }
}
