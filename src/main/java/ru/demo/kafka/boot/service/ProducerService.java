package ru.demo.kafka.boot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Service;
import ru.demo.kafka.boot.bean.CallbackKafka;
import ru.demo.kafka.boot.bean.ProducerKafka;
import ru.demo.kafka.boot.dto.DocumentDto;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerService {

    private final ProducerKafka producer;
    private final ObjectMapper mapper;
    private final CallbackKafka callbackKafka;


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

    public Map<String, Object> patch(String kafkaBootStrapServers) {
        producer.configure(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootStrapServers);
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

    public DocumentDto send(String topic, String key, DocumentDto document) throws Exception {
        prepare(document);

        log.debug(document.toString());
        String value = mapper.writeValueAsString(document);

        log.debug(value);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
        Future<RecordMetadata> metadataFuture = producer.send(record, callbackKafka);

        log.debug("#send: {}", metadataFuture.get(5, TimeUnit.SECONDS));

        return document;
    }

    private void prepare(DocumentDto document) {
        if (StringUtils.isBlank(document.getDraftId())) {
            document.setDraftId(UUID.randomUUID().toString());
        }

        if (StringUtils.isBlank(document.getInternalId())) {
            document.setInternalId(UUID.randomUUID().toString());
        }

        String content = document.getContent().replaceAll(StringUtils.CR, "").replaceAll(StringUtils.LF, "");
        document.setContent(content);

        byte[] encodedContent = Base64.getEncoder().encode(document.getContent().getBytes());
        document.setRawDocument(new String(encodedContent, StandardCharsets.UTF_8));

        document.setReceivedAt(new Date());
    }

}
