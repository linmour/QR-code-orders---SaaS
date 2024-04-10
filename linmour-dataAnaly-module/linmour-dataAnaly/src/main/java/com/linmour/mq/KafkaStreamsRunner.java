package com.linmour.mq;

import org.apache.kafka.streams.KafkaStreams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class KafkaStreamsRunner implements CommandLineRunner {

    @Autowired
    private KafkaStreams kafkaStreams;

    @Override
    public void run(String... args) throws Exception {
        kafkaStreams.start();
    }
}