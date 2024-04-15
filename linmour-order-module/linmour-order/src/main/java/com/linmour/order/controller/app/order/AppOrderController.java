package com.linmour.order.controller.app.order;

import com.baomidou.mybatisplus.extension.api.R;
import com.linmour.order.pojo.Do.OrderItem;
import com.linmour.security.dtos.Result;
import com.linmour.order.pojo.Dto.CreateOrderDto;
import com.linmour.order.pojo.Dto.CheckoutDto;
import com.linmour.order.service.OrderInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app/order/order")
public class AppOrderController {
    @Resource


    private OrderInfoService orderInfoService;

    @PostMapping("/createOrder")
    public Result createOrder(@RequestBody CreateOrderDto createOrderDto) {
        return orderInfoService.createOrder(createOrderDto);
    }

    @PostMapping("/checkout")
    public Result checkout(@RequestBody CheckoutDto checkoutDto) {
        return orderInfoService.checkout(checkoutDto);
    }

    @GetMapping("/GetHistoryOrderList/{tableId}")
    public Result GetHistoryOrderList(@PathVariable Long tableId) {
        return Result.success(orderInfoService.GetHistoryOrderList(tableId));
    }

    @GetMapping("/GetCurrentOrderInfo/{tableId}")
    public Result getCurrentOrderInfo(@PathVariable Long tableId) {
        return Result.success(orderInfoService.GetCurrentOrderInfo(tableId));
    }

    @GetMapping("/GetOrderInfoDetail/{orderId}")
    public Result GetOrderInfoDetail(@PathVariable Long orderId) {
        return Result.success(orderInfoService.GetOrderInfoDetail(orderId));
    }
    @PostMapping("/updateOrderItemStatus/{tableId}/{index}")
    public Result updateOrderItemStatus(@PathVariable String  tableId ,@PathVariable Long index) {
        orderInfoService.updateOrderItemStatus(tableId,index);
        return Result.success();
    }


}
