package com.linmour.redisPub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisPublisher {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void publishMessage(String message) {
        redisTemplate.convertAndSend("my_channel", message);
    }
}