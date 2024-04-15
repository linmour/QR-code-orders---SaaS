package com.linmour.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.linmour.redisPub.RedisPublisher;
import com.linmour.dataAnaly.service.OrderSummaryDayService;
import com.linmour.order.pojo.Do.OrderInfo;
import com.linmour.order.pojo.Do.OrderItem;
import com.linmour.order.pojo.Dto.OrderAllDto;
import com.linmour.security.utils.RedisCache;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Properties;

@Configuration
public class KafkaStreamsApplication {

    @Resource
    private RedisCache redisCache;
    @Resource
    private OrderSummaryDayService orderSummaryDayService;
    @Resource
    private RedisPublisher redisPublisher;
    @Bean
    public KafkaStreams kafkaStreams() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-streams-example-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "120.79.7.243:9092");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // 创建 ObjectMapper 以解析 JSON 消息
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // 构建 Kafka Streams 应用程序
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> source = builder.stream("sb_topic", Consumed.with(Serdes.String(), Serdes.String()));


        // 将 JSON 消息转换为 OrderAllDto 对象
        KStream<String, OrderAllDto> orders = source.mapValues(value -> {

            try {
                OrderAllDto orderAllDto = objectMapper.readValue(value, OrderAllDto.class);
                OrderInfo orderInfo = orderAllDto.getOrderInfo();
                List<OrderItem> orderItems = orderAllDto.getOrderItems();

                redisCache.incrementHash("dataAnaly:orderSummaryDay:" + orderInfo.getShopId(), "orderNum", Long.valueOf(1));
                redisCache.incrementHash("dataAnaly:orderSummaryDay:" + orderInfo.getShopId(), "productNum", Long.valueOf(orderItems.size()));
                redisCache.incrementHash("dataAnaly:orderSummaryDay:" + orderInfo.getShopId(), "price", (orderInfo.getPayAmount()).longValue());

                orderItems.forEach(m -> {
                    redisCache.incrementHash("dataAnaly:productSummaryDay:" + orderInfo.getShopId(), m.getName(), Long.valueOf(m.getQuantity()));

                });
                LocalDateTime createTime = orderInfo.getCreateTime();
                redisCache.incrementHash("dataAnaly:orderSummaryTimePeriod:" + orderInfo.getShopId() +":" + detectMealTime(createTime), "orderNum", Long.valueOf(1));
                redisCache.incrementHash("dataAnaly:orderSummaryTimePeriod:" + orderInfo.getShopId() +":" + detectMealTime(createTime), "productNum", Long.valueOf(orderItems.size()));
                redisCache.incrementHash("dataAnaly:orderSummaryTimePeriod:" + orderInfo.getShopId() +":" + detectMealTime(createTime), "price", (orderInfo.getPayAmount()).longValue());

                //通知webstock推送给前端
                redisPublisher.publishMessage(String.valueOf(orderInfo.getShopId()));
                return orderAllDto;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        return new KafkaStreams(builder.build(), props);
    }

    //判断是在哪个时间段
    private String detectMealTime(LocalDateTime localDateTime) {
        // 将 LocalDateTime 数据转换为 Instant
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);

        // 获取一天中的秒数
        long secondsOfDay = instant.getEpochSecond() % (24 * 60 * 60);

        // 根据一天中的秒数判断时间范围
        String mealTime = null;
        if (secondsOfDay >= LocalTime.of(7, 0).toSecondOfDay() && secondsOfDay < LocalTime.of(11, 0).toSecondOfDay()) {
            mealTime = "早餐";
        } else if (secondsOfDay >= LocalTime.of(11, 0).toSecondOfDay() && secondsOfDay < LocalTime.of(15, 0).toSecondOfDay()) {
            mealTime = "午餐";
        } else if (secondsOfDay >= LocalTime.of(15, 0).toSecondOfDay() && secondsOfDay < LocalTime.of(23, 0).toSecondOfDay()) {
            mealTime = "晚餐";
        } else {
            mealTime = "深夜";
        }

        return mealTime;
    }
}