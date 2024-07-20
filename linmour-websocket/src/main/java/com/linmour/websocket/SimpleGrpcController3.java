package com.linmour.websocket;

import com.linmour.order.pojo.Do.OrderItem;
import com.linmour.order.pojo.Dto.CreateOrderDto;
import com.linmour.security.dtos.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 测试grpc接口
 * @Author kele
 * @Data 2023/9/6 15:35
 */
@RestController
@RequestMapping("grpc")
public class SimpleGrpcController3 {
    @Autowired
    private GrpcClientService3 service;

    @GetMapping("test")
    public Result test() throws Exception {
        List<Long> longs = new ArrayList<>();
        longs.add(1L);
        longs.add(2L);
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        service.createOrder(new CreateOrderDto("545",23L,null,orderItems,"1",1L));
        return new Result();
    }


}