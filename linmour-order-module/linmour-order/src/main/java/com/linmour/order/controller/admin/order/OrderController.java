package com.linmour.order.controller.admin.order;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.linmour.order.pojo.Dto.OrderInfoPage;
import com.linmour.security.dtos.PageParam;
import com.linmour.security.dtos.Result;
import com.linmour.security.utils.RedisCache;
import com.linmour.order.mapper.OrderItemMapper;
import com.linmour.order.pojo.Do.OrderInfo;
import com.linmour.order.pojo.Do.OrderItem;
import com.linmour.order.pojo.Dto.OrderInfoDto;
import com.linmour.order.service.OrderInfoService;
import com.linmour.order.service.OrderItemService;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.linmour.security.utils.SecurityUtils.getShopId;

@RestController
@RequestMapping("/order/order")
public class OrderController {
    @Resource
    private OrderInfoService orderInfoService;

    @Resource
    private OrderItemService orderItemService;
    @Resource
    private OrderItemMapper orderItemMapper;
    @Resource
    private RedisCache redisCache;

    @GetMapping("/GetCurrentOrderInfo/{tableId}")
    public Result GetCurrentOrderInfo(@PathVariable Long tableId) {
        return Result.success(orderInfoService.GetCurrentOrderInfo(tableId));
    }

    @GetMapping("/GetOrderByShopId")
    public Result GetOrderByShopId() {
        return Result.success(orderInfoService.GetOrderByShopId());
    }

    @GetMapping("/getOrderPayAmount")
    public Result getOrderPayAmount(OrderInfoPage orderInfoPage) {
        return Result.success(orderInfoService.getOrderPayAmount(orderInfoPage));
    }


    @PostMapping("/changeOrder")
    public Result changeOrderStatus(@RequestBody Map<String, OrderInfoDto> map) {
        OrderInfoDto orderInfoDto = map.get("orderInfoDto");
        return orderInfoService.changeOrder(orderInfoDto);
    }

    @GetMapping("/a")
    @Transactional
//    @ShardingTransactionType(TransactionType.BASE)
    public void a() {
        OrderInfo orderInfo = new OrderInfo();
        OrderInfo orderInfo1 = new OrderInfo();
        OrderInfo orderInfo2 = new OrderInfo();
        orderInfo.setId("1111");
        orderInfo1.setId("222222222");
        orderInfo2.setId("33333");
        orderInfo1.setOpenid("dsfsd");
        orderInfo2.setOpenid("4L");
        orderInfo.setOpenid("2L");
        orderInfo1.setShopId(1L);
        orderInfo2.setShopId(3L);
        orderInfo.setShopId(2L);
        orderInfo1.setCreateTime(LocalDateTime.now());
        orderInfo.setCreateTime(LocalDateTime.now());
        orderInfo2.setCreateTime(LocalDateTime.now());
//
        orderInfoService.save(orderInfo1);

        orderInfoService.save(orderInfo2);
        orderInfoService.save(orderInfo);
        OrderItem a = new OrderItem();
        OrderItem b = new OrderItem();
        OrderItem c = new OrderItem();
        a.setId(1L);
        b.setId(2L);
        c.setId(3L);
        a.setCreateTime(LocalDateTime.now());
        a.setShopId(1L);
        b.setShopId(2L);
        c.setShopId(4L);
//        a.setOrderId("aaa");
//        b.setOrderId("bbb");
//        c.setOrderId("ccc");
        orderItemService.save(a);
        orderItemService.save(b);
        orderItemService.save(c);
        int ss = 1/0;
    }

    @GetMapping("/b")
    public void b() {
        List<OrderItem> orderItems = orderItemMapper.selectList(null);
        orderItemMapper.selectList(new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getOrderId, 1111));
        System.out.println("--------------------------------------------------------------------------");
        orderItemMapper.selectList(new LambdaQueryWrapper<OrderItem>().eq(!getShopId().equals(0L),OrderItem::getShopId, 88));
    }

    @GetMapping("/d")
    public void d() {
        String cql = String.format("select * from order_info where id = '7676676'");
//        OrderInfo orderInfo = cassandraTemplate.selectOne("select * from order_info where id = '7676676'", OrderInfo.class);
//        List<OrderItem> select = cassandraTemplate.select("select * from order_item where order_id = 'aaa' ALLOW FILTERING", OrderItem.class);
//        System.out.println(select);
//        System.out.println(orderInfo);


    }

    @GetMapping("/c")
    public void c() {
        List<String> commands = new ArrayList<>();

        commands.add("python D:\\soft\\datax\\bin\\datax.py D:\\soft\\datax\\job\\linmour_order_info.json");
        commands.add("python D:\\soft\\datax\\bin\\datax.py D:\\soft\\datax\\job\\linmour_order_item.json");
        commands.forEach(m -> {
            try {
                Process process = Runtime.getRuntime().exec(m);

                // 获取命令输出
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                // 等待命令执行完成
                int exitCode = process.waitFor();
                System.out.println("命令执行完成，退出码：" + exitCode);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        //删除mysql中的数据
        orderItemService.postSql();

    }

    @GetMapping("/e")
    public void e(){
        List<Object> cacheList = redisCache.getCacheList("orderItem:1:1");

        List<OrderItem> list = com.alibaba.fastjson.JSONObject.parseArray(JSONObject.toJSONString(cacheList), OrderItem.class);

    }


}
