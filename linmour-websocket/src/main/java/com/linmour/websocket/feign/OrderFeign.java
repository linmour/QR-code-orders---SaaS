package com.linmour.websocket.feign;


import com.linmour.websocket.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "linmour-order")
public interface OrderFeign {
    @GetMapping("/order/order/getOrderInfo/{tableId}")
    Result getOrderInfo(@PathVariable Long tableId);
}
