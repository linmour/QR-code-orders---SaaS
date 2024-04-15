package com.linmour.dataAnaly;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.linmour.dataAnaly.pojo.Do.OrderSummaryDay;
import com.linmour.dataAnaly.pojo.Do.OrderSummaryTimePeriod;
import com.linmour.dataAnaly.pojo.Do.ProductSummaryDay;
import com.linmour.order.pojo.Dto.OrderAllDto;
import com.linmour.security.utils.RedisCache;
import org.apache.ibatis.transaction.Transaction;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@SpringBootApplication(scanBasePackages = {"com.linmour"})
@EnableDiscoveryClient
@MapperScan("com.linmour.dataAnaly.mapper")
public class DataAnalyApplication {

    public static void main(String[] args) {

        SpringApplication.run(DataAnalyApplication.class, args);
//        Properties props = new Properties();
//        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-streams-example-app");
//        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "120.79.7.243:9092");
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
//        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
//
//        // 创建 ObjectMapper 以解析 JSON 消息
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//
//        // 构建 Kafka Streams 应用程序
//        StreamsBuilder builder = new StreamsBuilder();
//        KStream<String, String> source = builder.stream("sb_topic", Consumed.with(Serdes.String(), Serdes.String()));
//
//        // 将 JSON 消息转换为 OrderAllDto 对象
//        KStream<String, OrderAllDto> orders = source.mapValues(value -> {
//            try {
//                System.out.println(value);
//                System.out.println("=========");
//                return objectMapper.readValue(value, OrderAllDto.class);
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
//        });
//
//        System.out.println("+++++");
//        System.out.println(orders);

//        // 根据订单信息创建数据存储对象
//        KStream<String, ProductSummaryDay> productSummaryDayList = orders
//                .flatMapValues(order -> {
//                    List<ProductSummaryDay> result = new ArrayList<>();
//                    order.getOrderItems().forEach(orderItem -> {
//                        ProductSummaryDay productSummaryDay = new ProductSummaryDay();
//                        Date date = Date.from(orderItem.getCreateTime().atZone(ZoneId.systemDefault()).toInstant());
//
//                        productSummaryDay.builder().date(date);
//                        result.add(productSummaryDay);
//                    });
//                    return result;
//                });
//
//        OrderSummaryDay orderSummaryDay1 = new OrderSummaryDay();
//
//        KStream<String, OrderSummaryDay> orderSummaryDayList = orders
//                .flatMapValues(order -> {
//                    List<OrderSummaryDay> result = new ArrayList<>();
//                    order.getOrderItems().forEach(orderItem -> {
//                        OrderSummaryDay orderSummaryDay = new OrderSummaryDay();
//                        orderSummaryDay.setOrderNum();
//                        new OrderSummaryDay{
//                            order.getOrderInfo().getCreateTime().toLocalDate(),
//                                    1,
//                                    orderItem.getQuantity(),
//                                    orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity())),
//                                    orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))
//                        }
//                        result.add();
//                    });
//                    return result;
//                });
//
//        KStream<Object, OrderSummaryTimePeriod> orderSummaryTimePeriodList = orders
//                .flatMapValues(order -> {
//                    List<Object[]> result = new ArrayList<>();
//                    order.getOrderItems().forEach(orderItem -> {
//                        long time = order.getOrderInfo().getCreateTime().getHour();
//                        String timePeriod = "";
//                        if (time >= 7 && time < 10) {
//                            timePeriod = "07:00 - 10:00";
//                        } else if (time >= 11 && time < 14) {
//                            timePeriod = "11:00 - 14:00";
//                        } else if (time >= 17 && time < 20) {
//                            timePeriod = "17:00 - 20:00";
//                        } else if (time >= 21 && time < 24) {
//                            timePeriod = "21:00 - 00:00";
//                        }
//                        result.add(new Object[]{
//                                timePeriod,
//                                1,
//                                orderItem.getQuantity(),
//                                orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity())),
//                                orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))
//                        });
//                    });
//                    return result;
//                });

        // 将数据存储对象写入输出主题
//        productSummaryDayList.to("product-summary-day");
//        orderSummaryDayList.to("order-summary-day");
//        orderSummaryTimePeriodList.to("order-summary-time-period");

        // 创建和启动 Kafka Streams 应用程序
//        KafkaStreams streams = new KafkaStreams(builder.build(), props);
//        streams.start();
    }


}


