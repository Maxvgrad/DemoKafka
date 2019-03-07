package ru.demo.kafka.demo.bean;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallbackKafka implements Callback {

    @Override
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        if (exception != null) {
            log.error("#onCompletion: {}", exception.getLocalizedMessage());
        }

        log.info("#onCompletion: offset({})", metadata.topic());
        log.info("#onCompletion: partition({})", metadata.partition());
        log.info("#onCompletion: offset({})", metadata.offset());
        log.info("#onCompletion: serializedKeySize({})", metadata.serializedKeySize());
        log.info("#onCompletion: serializedValueSize({})", metadata.serializedValueSize());
        log.info("#onCompletion: timestamp({})", metadata.timestamp());
    }
}
