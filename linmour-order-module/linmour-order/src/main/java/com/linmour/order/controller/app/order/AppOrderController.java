package com.linmour.order.controller.app.order;

import com.linmour.common.dtos.Result;
import com.linmour.order.pojo.Dto.CreateOrderDto;
import com.linmour.order.pojo.Dto.SubmitOrderDto;
import com.linmour.order.service.OrderInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app/order/order")
public class AppOrderController {
    @Resource


    private OrderInfoService orderInfoService;
    @PostMapping("/createOrder")
    public Result createOrder(@RequestBody CreateOrderDto createOrderDto){
        return orderInfoService.createOrder(createOrderDto);
    }

    @PostMapping("/checkout")
    public Result submitOrder(@RequestBody SubmitOrderDto submitOrderDto){
        return orderInfoService.submitOrder(submitOrderDto);
    }

    @GetMapping("/getOrderInfo/{tableId}")
    public Result getOrderInfo(@PathVariable Long tableId){
        return orderInfoService.getOrderInfo(tableId);
    }




}
