package com.linmour.mq.serializer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
public class KafkaDeserializer implements Deserializer<Object> {

    @Override
    public Object deserialize(String topic, byte[] data) {
        // 将字节数组转换为对象
        // 例如，使用 JSON 反序列化库
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); // 注册 Java Time 模块以处理 LocalDateTime
            mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
            return mapper.readValue(data, Object.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void close() {
        // 释放任何资源
    }
}