package ru.demo.kafka.boot.bean;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

@Component
public class KafkaConsumerCreator implements Function<Map<String, Object>, KafkaConsumer<String, String>> {

    @Override
    public KafkaConsumer<String, String> apply(Map<String, Object> properties) {
        return new KafkaConsumer<>(properties);
    }
}
