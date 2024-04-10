package com.linmour.mq;
 
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.linmour.order.pojo.Dto.OrderAllDto;
import com.linmour.product.pojo.Dto.ProductDetailDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.KafkaStreams;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Auther: 
 * @Date: 2023/10/19 16:24
 * @Description: com.example.kafkademo.demo
 * @version: 1.0
 */
@Component
public class KafkaConsumer {

    //监听消费
//    @KafkaListener(topics = {"sb_topic"})
//    public void onNormalMessage(LinkedHashMap<String, Object> record) {
//        ObjectMapper mapper = new ObjectMapper();
////      LocalDateTime序列化问题
//        mapper.registerModule(new JavaTimeModule());
//        mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
//        OrderAllDto orderAllDto = mapper.convertValue(record, new TypeReference<OrderAllDto>() {});
//        System.out.println("简单消费：" + record);
//
//    }

}
 