package ru.demo.kafka.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import ru.demo.kafka.demo.bean.ProducerKafka;
import ru.demo.kafka.demo.dto.DocumentDto;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerService {

    private final ProducerKafka producer;


    public Map<String, Object> get() {
        return producer.getProperties();
    }

    public Map.Entry<String, Object> getValue(String key) {
        return producer.getProperty(key);
    }

    public Map<String, Object> put(Map<String, ?> properties) {
        producer.configure(properties);
        return producer.getProperties();
    }

    public Map<String, Object> patch(String kafkaBootStrapServers, String groupId) {
        producer.configure(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootStrapServers);
        producer.configure(ProducerConfig.CLIENT_ID_CONFIG, groupId);

        return producer.getProperties();
    }

    public Map.Entry<String, Object> remove(String key) {

        Map.Entry<String, Object> entry = producer.getProperty(key);

        if (entry.getValue() != null) {
            Map<String, Object> properties = producer.getProperties();
            properties.remove(key);

            producer.configure(properties);
        }
        return entry;
    }

    public void send(String topic, String key, DocumentDto document) {

        ProducerRecord<String, DocumentDto> record = new ProducerRecord<String, DocumentDto>(topic, key, document);

        producer.send(record);
        return;
    }

}
