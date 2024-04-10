package com.linmour.dataAnaly.controller.admin;

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

    public static void main(String[] args) {
        SpringApplication.run(RedisPubSubApplication.class, args);
    }

    @PostMapping("/publish")
    public void publishMessage(@RequestBody String message) {
        List<Object> allHashValues = redisCache.getAllHashValues("dataAnaly:orderSummaryDay:1");
        Object orderNum = redisCache.getHashValue("dataAnaly:orderSummaryDay:1", "orderNum");
        Map<String, Object> allHash = redisCache.getAllHash("dataAnaly:orderSummaryDay:1" );
        Map<String, Object> a = redisCache.getAllHash("fd" );
        Object login = redisCache.getCacheObject("login");
    }
}