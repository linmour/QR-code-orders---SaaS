package com.linmour.order.controller.order;

import com.linmour.common.dtos.Result;
import com.linmour.order.pojo.Dto.CreateOrderDto;
import com.linmour.order.pojo.Dto.SubmitOrderDto;
import com.linmour.order.service.OrderInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order/order")
public class orderController {
    @Resource
    private OrderInfoService orderInfoService;
    @PostMapping("/createOrder")
    public Result createOrder(@RequestBody CreateOrderDto createOrderDto){
        return orderInfoService.createOrder(createOrderDto);
    }

    @PostMapping("/submitOrder")
    public Result submitOrder(@RequestBody SubmitOrderDto submitOrderDto){
        return orderInfoService.submitOrder(submitOrderDto);
    }
}
