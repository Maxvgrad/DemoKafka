package ru.demo.kafka.demo.service;


import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.demo.kafka.demo.ReflectionHelper;
import ru.demo.kafka.demo.bean.Configurator;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ConsumerServiceTest {

    private static final String TOPIC_1 = "topic_1";
    private static final String TOPIC_2 = "topic_2";

    private ConsumerService consumerService;
    private KafkaConsumer<String, String> kafkaConsumer;

    @BeforeEach
    void setUp() {
        kafkaConsumer = Mockito.mock(KafkaConsumer.class);
        consumerService = new ConsumerService(null, new Configurator<>(), prop -> kafkaConsumer);
    }

    @Test
    void subscribeTopicEmpty() {
        assertThrows(IllegalArgumentException.class, () -> consumerService.subscribe(StringUtils.EMPTY));
    }

    @Test
    void subscribePrevTopicIsNull() {
        String prevTopic = consumerService.subscribe(TOPIC_1);

        assertNull(prevTopic);
        Mockito.verify(kafkaConsumer).subscribe(Collections.singleton(TOPIC_1));
    }

    @Test
    void subscribePrevTopicIsTheSame() {
        ReflectionHelper.setField("topic", consumerService, TOPIC_1);
        String prevTopic = consumerService.subscribe(TOPIC_1);

        assertEquals(TOPIC_1, prevTopic);
        Mockito.verify(kafkaConsumer, Mockito.never()).subscribe(Collections.singleton(TOPIC_1));
    }

    @Test
    void subscribePrevTopicDifferent() {
        consumerService.subscribe(TOPIC_1);
        String prevTopic = consumerService.subscribe(TOPIC_2);

        assertEquals(TOPIC_1, prevTopic);
        Mockito.verify(kafkaConsumer).subscribe(Collections.singleton(TOPIC_1));
        Mockito.verify(kafkaConsumer).subscribe(Collections.singleton(TOPIC_2));
    }

    @Test
    void poll() {
    }
}