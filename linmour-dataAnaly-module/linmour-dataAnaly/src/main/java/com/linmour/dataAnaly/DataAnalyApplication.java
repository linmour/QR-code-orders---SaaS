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

@MapperScan("com.linmour.dataAnaly.mapper")
public class DataAnalyApplication {

    public static void main(String[] args) {

        SpringApplication.run(DataAnalyApplication.class, args);
    }


}


