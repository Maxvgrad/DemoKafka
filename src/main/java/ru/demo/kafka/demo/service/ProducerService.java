package ru.demo.kafka.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.stereotype.Service;

@Slf4j
@Service()
@RequiredArgsConstructor
public class ProducerService {

    private KafkaProducer<String, String> producer;

    public void configure(String bootStrapServers) {

    }

}
