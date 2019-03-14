package ru.demo.kafka.boot.bean;


import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class ProducerKafka implements Producer<ProducerRecord<String, String>, Future<RecordMetadata>> {

    private KafkaProducer<String, String> producer;

    private Map<String, Object> properties;

    private boolean needRefresh = true; // refresh producer properties?

    @PostConstruct
    private void init() {
        properties = new HashMap<>();

        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    }

    @PreDestroy
    private void cleanUp() {
        if (producer != null) {
            producer.close();
        }
    }

    @Override
    public void configure(Map<String, ?> properties) {
        this.properties.putAll(properties);
        needRefresh = true;
    }

    @Override
    public Map.Entry<String, Object> configure(String key, String value) {
        Object oldValue = this.properties.put(key, value);

        needRefresh = needRefresh || !value.equals(oldValue);

        return new ImmutablePair<>(key, oldValue);
    }

    @Override
    public Map<String, Object> getProperties() {
        return new HashMap<>(properties);
    }

    @Override
    public Map.Entry<String, Object> getProperty(String key) {
        return new ImmutablePair<>(key, properties.get(key));
    }

    @Override
    public Future<RecordMetadata> send(ProducerRecord<String, String> record) {
        return send(record, null);
    }

    public Future<RecordMetadata> send(ProducerRecord<String, String> record, Callback callback) {

        if (needRefresh) {
            producer = new KafkaProducer<>(properties);
        }

        return producer.send(record, callback);
    }
}