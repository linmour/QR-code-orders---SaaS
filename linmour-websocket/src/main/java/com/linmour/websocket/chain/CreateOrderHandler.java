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
                    String remark = jsonObject.get("remark").toString();
                    List<ShopListDto> list=shopCarList.toJavaList(ShopListDto.class);
                    //TODO 加个拦截加个shopid,抛出异常，前端展示
                    setShopId(list.get(0).getShopId());
                    try {
                        Result order = orderFeign.createOrder(new CreateOrderDto(Long.parseLong(webSocke.getTableId()), amount, list, remark));
                        if (order.getCode() == 200){
                            //通知清空购物车
                            AppSendInfo("订单提交成功", webSocke.getTableId());
                            //清空本地的购物记录
                            recordMap.get(webSocke.getTableId()).clear();
                            webSocke.getCreateOrder().set(true);
                        }else {
                            AppSendInfo("订单提交失败",webSocke.getTableId());
                        }


                    }catch (Exception e){
                        e.printStackTrace();
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
