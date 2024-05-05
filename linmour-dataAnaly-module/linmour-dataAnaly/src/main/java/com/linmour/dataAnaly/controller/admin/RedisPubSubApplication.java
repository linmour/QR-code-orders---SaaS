package com.linmour.dataAnaly.controller.admin;

import com.linmour.dataAnaly.mapper.OrderSummaryTimePeriodMapper;
import com.linmour.dataAnaly.pojo.Do.OrderSummaryTimePeriod;
import com.linmour.redisPub.RedisPublisher;
import com.linmour.security.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dataAnaly")
public class RedisPubSubApplication {

    @Autowired
    private RedisPublisher redisPublisher;
    @Resource
    private RedisCache redisCache;
    @Resource
    private OrderSummaryTimePeriodMapper orderSummaryTimePeriodMapper;
    public static void main(String[] args) {
        SpringApplication.run(RedisPubSubApplication.class, args);
    }

    @PostMapping("/publish")
    public void publishMessage(@RequestBody String message) {
//        System.out.println(orderSummaryTimePeriodMapper.selectById(1));
        OrderSummaryTimePeriod orderSummaryTimePeriod = new OrderSummaryTimePeriod();
        orderSummaryTimePeriod.setTimePeriod("45");
        orderSummaryTimePeriod.setShopId(1L);
        orderSummaryTimePeriodMapper.insert(orderSummaryTimePeriod);
    }
}