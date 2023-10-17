package com.linmour.websocket.ws;

import com.alibaba.fastjson.JSONObject;
import com.linmour.order.pojo.Dto.CreateOrderDto;
import com.linmour.order.pojo.Dto.ShopListDto;
import com.linmour.websocket.config.WebSocketCustomEncoding;
import com.linmour.websocket.feign.OrderFeign;
import com.linmour.websocket.mq.ProducerMq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.linmour.common.utils.SecurityUtils.setShopId;

@ServerEndpoint(value = "/websocket/table/{tableId}", encoders = WebSocketCustomEncoding.class)
@Component
@Slf4j
public class AppWebSocketServer {

    @Resource
    private ProducerMq a;

    //注入为空
    public static ProducerMq producerMq;

    @PostConstruct
    public void b() {
        producerMq = this.a;
    }

    @Resource
    private OrderFeign w;

    public static OrderFeign orderFeign;

    @PostConstruct
    public void m() {
        orderFeign = w;
    }

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    //一个 AppWebSocketServer 就是一个用户，一个tableId下有一个 List<AppWebSocketServer> 也就是多个用户
    private static ConcurrentHashMap<String, List<AppWebSocketServer>> webSocketMap = new ConcurrentHashMap<>();
    //购物车的记录，用于第一次扫码的人同步购物车
    private static ConcurrentHashMap<String, List<JSONObject>> recordMap = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收tableId
     */
    private String tableId = "";

    /**
     * 用来标识这个用户需要接收同步的购物车信息
     */
    private boolean flag = true;

    private volatile boolean createOrder = false;


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("tableId") String tableId) {
        this.session = session;
        this.tableId = tableId;
        if (webSocketMap.containsKey(tableId)) {
            List<AppWebSocketServer> serverList = webSocketMap.get(tableId);
            serverList.add(this);
        } else {
            List<AppWebSocketServer> serverList = new ArrayList<>();
            serverList.add(this);
            webSocketMap.put(tableId, serverList);
        }
        try {
            sendMessage("1");
        } catch (IOException e) {
            log.error("用户:" + tableId + ",网络异常!!!!!!");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(tableId)) {
            List<AppWebSocketServer> serverList = webSocketMap.get(tableId);
            serverList.remove(this);
            if (serverList.isEmpty()) {
                webSocketMap.remove(tableId);
            }
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {

        System.out.println(message);
        //可以群发消息
        //消息保存到数据库、redis
        if (StringUtils.isNotBlank(message)) {
            try {
                //解析发送的报文
                JSONObject jsonObject = JSONObject.parseObject(message);
                //追加发送人(防止串改)
                jsonObject.put("fromtableId", this.tableId);

                //已经扫过码的点餐
                if (jsonObject.containsKey("change")) {
                    //todo 考虑一下这种方案的加锁问题
                    ArrayList<JSONObject> jsonObjects = new ArrayList<>();
                    jsonObjects.add(jsonObject);
                    producerMq.syncShopCar(jsonObjects);
                    //记录每一次购物车变化的记录
                    List<JSONObject> objects = recordMap.get(this.tableId);
                    objects.add(jsonObject);
                }
                //第一次扫码的，进行同步购物车
                else if (jsonObject.containsKey("sync")) {
                    //这个是判断是否有这个桌号，也就是 是否有人点过餐
                    if (recordMap.containsKey(this.tableId)) {
                        List<JSONObject> recordList = recordMap.get(tableId);
                        //指定发送对象
                        if (StringUtils.isNotBlank(this.tableId) && webSocketMap.containsKey(this.tableId) && recordList.size() > 0) {
                            List<AppWebSocketServer> serverList = webSocketMap.get(this.tableId);
                            for (AppWebSocketServer server : serverList) {
                                if (server.flag) {
                                    server.sendMessage(recordList);
                                }
                            }
                        }
                    } else {
                        ArrayList<JSONObject> objects = new ArrayList<>();
                        recordMap.put(this.tableId, objects);
                    }
                    this.flag = !this.flag;

                } else if (jsonObject.containsKey("createOrder")) {
                    synchronized (this) {

                        if (StringUtils.isNotBlank(this.tableId) && webSocketMap.containsKey(this.tableId)) {
                            List<AppWebSocketServer> serverList = webSocketMap.get(this.tableId);

                            // 使用同块
                            //有一个为true就说明已经有订单了
                            if (serverList.stream().anyMatch(m -> m.createOrder)) {
                                this.sendMessage("已有人提交订单，请稍后");

                                //遍历所有对象，把订单都改为未提交，为了下一次点餐
                                for (AppWebSocketServer server : serverList) {

                                    server.createOrder = false;
                                }

                                return;
                            }

                            List<ShopListDto> shopList = (List<ShopListDto>) jsonObject.get("shopList");
                            BigDecimal amount = new BigDecimal((Integer) jsonObject.get("amount"));
                            Long tableId = Long.parseLong((String) jsonObject.get("tableId"));
                            Object shopList1 = ((List<Object>) jsonObject.get("shopList")).get(0);
                            setShopId(Long.valueOf((((JSONObject) shopList1).get("shopId")).toString()));
//                            orderFeign.createOrder(new CreateOrderDto(tableId, amount, shopList, ""));
                            this.createOrder = true;
                            for (AppWebSocketServer server : serverList) {
                                //通知情况本地购物车
                                server.sendMessage("订单创建成功");
                            }

                        }
                    }

                } else {
                    //传送给对应tableId用户的websocket
                    if (StringUtils.isNotBlank(this.tableId) && webSocketMap.containsKey(this.tableId)) {
                        List<AppWebSocketServer> serverList = webSocketMap.get(this.tableId);
                        for (AppWebSocketServer server : serverList) {
                            server.sendMessage("1");
                        }
                    } else {
                        log.error("请求的tableId:" + this.tableId + "不在该服务器上");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时候
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:" + this.tableId + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(Object message) throws IOException {
        //加入线程锁
        synchronized (session) {
            try {
                //同步发送信息
                this.session.getBasicRemote().sendObject(message);
            } catch (Exception e) {
                log.error("服务器推送失败:" + e.getMessage());
            }
        }
    }


    /**
     * 发送自定义消息
     * */
    /**
     * 发送自定义消息
     *
     * @param message 发送的信息
     * @param tableId 如果为null默认发送所有
     * @throws IOException
     */
    public static void AppSendInfo(Object message, String tableId) throws IOException {
        if (StringUtils.isEmpty(tableId)) {
            // 向所有用户发送信息
            for (List<AppWebSocketServer> serverList : webSocketMap.values()) {
                for (AppWebSocketServer server : serverList) {
                    server.sendMessage(message);
                }
            }
        } else if (webSocketMap.containsKey(tableId)) {
            // 发送给指定用户信息
            List<AppWebSocketServer> serverList = webSocketMap.get(tableId);
            for (AppWebSocketServer server : serverList) {
                server.sendMessage(message);
            }
        } else {
            log.error("请求的tableId:" + tableId + "不在该服务器上");
        }
    }


    public static synchronized ConcurrentHashMap<String, List<AppWebSocketServer>> getWebSocketMap() {
        return AppWebSocketServer.webSocketMap;
    }

}
