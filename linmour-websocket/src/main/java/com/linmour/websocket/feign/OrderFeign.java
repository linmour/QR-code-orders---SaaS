package com.linmour.websocket.feign;


import com.linmour.order.pojo.Dto.CreateOrderDto;
import com.linmour.websocket.config.ExampleFeignConfiguration;
import com.linmour.websocket.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.concurrent.CompletableFuture;

@FeignClient(name = "linmour-order",configuration = ExampleFeignConfiguration.class)
public interface OrderFeign {
    @GetMapping("/order/order/getOrderInfo/{tableId}")
    Result getOrderInfo(@PathVariable Long tableId);


    @PostMapping("/app/order/order/createOrder")
    Result createOrder(@RequestBody CreateOrderDto createOrderDto);
}
