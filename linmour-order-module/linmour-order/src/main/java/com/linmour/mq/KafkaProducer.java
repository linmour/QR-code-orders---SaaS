package com.linmour.mq;

import com.linmour.order.pojo.Dto.OrderAllDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther:
 * @Date: 2023/10/19 16:22
 * @Description: com.example.kafkademo.demo
 * @version: 1.0
 */
@RestController
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @GetMapping("/kafka/normal/{message}")
    public void sendNormalMessage(@PathVariable("message") String message) {
        kafkaTemplate.send("sb_topic", message);
    }

    public void originalOrder(OrderAllDto message) {
        kafkaTemplate.send("sb_topic", message);
    }


}
