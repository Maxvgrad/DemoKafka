package ru.demo.kafka.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Service;
import ru.demo.kafka.demo.bean.Configurator;
import ru.demo.kafka.demo.bean.ConsumerRecordsRegistry;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerService {

    private final ConsumerRecordsRegistry<String, String> consumerRegistry;
    private final Configurator<String, Object> configurator;
    private final Function<Map<String, Object>, KafkaConsumer<String, String>> kafkaConsumerCreator;

    private KafkaConsumer<String, String> consumerKafka;
    private String topic;

    @PostConstruct
    private void init() {
        configurator.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configurator.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    }

    public Map<String, Object> configure(String key, Object value) {
        configurator.put(key, value);
        return getProperties();
    }

    public Map<String, Object> getProperties() {
        return configurator.get();
    }

    public Map.Entry<String, Object> getProperty(String key) {
        return configurator.get(key);
    }


    public Map<String, Object> patch(String bootStrapServers, String groupId, String autoOffset) {
        configurator.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        configurator.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configurator.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffset);
        return configurator.get();
    }

    public Map.Entry<String, Object> remove(String key) {
        return new ImmutablePair<>(key, configurator.remove(key));
    }

    public String subscribe(String topic) {
        String previousTopic = this.topic;

        if (StringUtils.isBlank(topic)) {
            log.error("#subscribe: topic blank");
            throw new IllegalArgumentException("Empty topic.");
        }

        if (!StringUtils.equalsIgnoreCase(previousTopic, topic)) {
            KafkaConsumer<String, String> consumer = refresh();
            this.topic = topic;
            consumer.subscribe(Collections.singleton(topic));
        }

        return previousTopic;
    }

    public ConsumerRecords<String, String> poll() {
        KafkaConsumer<String, String> consumer = refresh();
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(2));
        consumerRegistry.add(records);
        return records;
    }

    protected KafkaConsumer<String, String> refresh() {
        if (configurator.isNeedRefresh()) {
            consumerKafka = kafkaConsumerCreator.apply(configurator.refresh());
        }
        return consumerKafka;
    }
}
