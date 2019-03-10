package ru.demo.kafka.demo.bean;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class ConsumerRecordsRegistry<K, V> {

    private Map<String, Map<Long, ConsumerRecord<K, V>>> registry;

    @PostConstruct
    private void init() {
        registry = new HashMap<>();
    }


    public void add(ConsumerRecord<K, V> record) {
        registry.computeIfAbsent(record.topic(), k -> new HashMap<>()).put(record.offset(), record);
    }

    public void add(ConsumerRecords<K, V> records) {
        for (ConsumerRecord<K, V> record : records) {
            add(record);
        }
    }

    public Map<Long, ConsumerRecord<K, V>> get(String topic) {
        return registry.getOrDefault(topic, Collections.emptyMap());
    }

    public Iterable<Map<Long, ConsumerRecord<K, V>>> get() {
        return registry.values();
    }
}
