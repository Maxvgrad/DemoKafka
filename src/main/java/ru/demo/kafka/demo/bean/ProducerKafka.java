package ru.demo.kafka.demo.bean;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.stereotype.Component;

@Component
public class ProducerKafka implements Producer {

    private KafkaProducer<String, String> producer;

    @Override
    public void configure() {

    }

    @Override
    public void send() {

    }
}
