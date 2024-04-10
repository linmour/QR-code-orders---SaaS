package com.linmour.mq.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.serialization.Serializer;
//序列化
public class KafkaSerializer<T> implements Serializer<T> {

    @Override
    public byte[] serialize(String topic, T data) {
        // 将对象转换为字节数组
        // 例如，使用 JSON 序列化库
        try {
            ObjectMapper mapper = new ObjectMapper();
            //LocalDateTime 和 String 互转
            mapper.registerModule(new JavaTimeModule()); // 注册 Java Time 模块以处理 LocalDateTime
            return mapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        // 释放任何资源
    }
}