package com.linmour.order;

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
public class SimpleGrpcController {
    @Autowired
    private GrpcClientService service;

    @GetMapping("test")
    public Result test() throws IOException {
        List<Long> longs = new ArrayList<>();
        longs.add(1L);
        longs.add(2L);
        service.getProductDetails(longs);
        service.reduceInventories(longs);
        service.getShopByIds(longs);
        service.getMerchantByIds(longs);
        return new Result();
    }


}