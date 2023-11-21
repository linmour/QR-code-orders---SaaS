package com.linmour.order.controller.admin.order;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.linmour.common.dtos.Result;
import com.linmour.order.mapper.OrderItemMapper;
import com.linmour.order.pojo.Do.OrderInfo;
import com.linmour.order.pojo.Do.OrderItem;
import com.linmour.order.pojo.Dto.OrderInfoDto;
import com.linmour.order.service.OrderInfoService;
import com.linmour.order.service.OrderItemService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

@RestController
@RequestMapping("/order/order")
public class OrderController {
    @Resource
    private OrderInfoService orderInfoService;
    @Resource
    private OrderItemService orderItemService;

    @GetMapping("/getOrderInfo/{tableId}")
    public Result getOrderInfo(@PathVariable Long tableId) {
        return orderInfoService.getOrderInfo(tableId);
    }


    @PostMapping("/changeOrder")
    public Result changeOrderStatus(@RequestBody Map<String, OrderInfoDto> map) {
        OrderInfoDto orderInfoDto = map.get("orderInfoDto");
        return orderInfoService.changeOrder(orderInfoDto);
    }

    @GetMapping("/a")
    public void a() {
//        OrderInfo orderInfo = new OrderInfo();
//        OrderInfo orderInfo1 = new OrderInfo();
//        OrderInfo orderInfo2 = new OrderInfo();
//        orderInfo.setId("11111111111111111");
//        orderInfo1.setId("22222222222222222");
//        orderInfo2.setId("3333333333333333");
//        orderInfo1.setCusId(1L);
//        orderInfo2.setCusId(4L);
//        orderInfo.setCusId(2L);
//        orderInfo1.setShopId(1L);
//        orderInfo2.setShopId(3L);
//        orderInfo.setShopId(2L);
//
//        orderInfoService.save(orderInfo1);
//        orderInfoService.save(orderInfo2);
//        orderInfoService.save(orderInfo);
        OrderItem a = new OrderItem();
        OrderItem b = new OrderItem();
        OrderItem c = new OrderItem();
        a.setId(1L);
        b.setId(2L);
        c.setId(3L);
        a.setShopId(1L);
        b.setShopId(2L);
        c.setShopId(4L);
        a.setOrderId("aaa");
        b.setOrderId("bbb");
        c.setOrderId("ccc");
        orderItemService.save(a);
        orderItemService.save(b);
        orderItemService.save(c);

    }


    @GetMapping("/b")
    public void b() {
        OrderInfo one = orderInfoService.getOne(null);
        OrderInfo a = orderInfoService.getOne(new LambdaQueryWrapper<OrderInfo>().eq(OrderInfo::getCusId, 2L).eq(OrderInfo::getShopId, 2L));
    }

    @GetMapping("/c")
    public void c() {
        String command = "python D:\\soft\\datax\\bin\\datax.py D:\\soft\\datax\\job\\linmour_order.json";
        try {
            Process process = Runtime.getRuntime().exec(command);

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
    }


}
