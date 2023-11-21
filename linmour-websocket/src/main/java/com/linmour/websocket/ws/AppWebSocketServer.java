package com.linmour.websocket.ws;
import com.alibaba.fastjson.JSONObject;
import com.linmour.websocket.chain.*;
import com.linmour.websocket.config.WebSocketCustomEncoding;
import com.linmour.websocket.feign.OrderFeign;
import com.linmour.websocket.mq.ProducerMq;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

@ServerEndpoint(value = "/websocket/table/{tableId}", encoders = WebSocketCustomEncoding.class)
@Component
@Slf4j
@Data
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
    private volatile AtomicBoolean sync = new AtomicBoolean(true);

    private volatile AtomicBoolean createOrder = new AtomicBoolean(false);


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

                //这里采用责任链模式，每一个处理器对应一个前段发过来的请求，这里还可以用工厂模式来生成对象
                ChangeHandler changeHandler = new ChangeHandler();
                CreateOrderHandler createOrderHandler = new CreateOrderHandler();
                SyncHandler syncHandler = new SyncHandler();
                ClearHandler clearHandler = new ClearHandler();
                OtherHandler otherHandler = new OtherHandler();
                changeHandler.addNextHandler(syncHandler).addNextHandler(createOrderHandler).addNextHandler(clearHandler).addNextHandler(otherHandler);
                changeHandler.handleRequest(webSocketMap, jsonObject, recordMap, this, orderFeign);
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
