package ru.demo.kafka.boot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.demo.kafka.boot.DemoApplication;
import ru.demo.kafka.boot.dto.DocumentDto;
import ru.demo.kafka.boot.service.ProducerService;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(DemoApplication.APP_ROOT_PATH + "/producer")
public class ProducerController {

    private final ProducerService service;

    @GetMapping("/}")
    public Map<String, Object> get() {
        log.debug("#get:");
        return service.get();
    }

    @GetMapping("/{key}")
    public Map.Entry<String, Object> getValue(@PathVariable String key) {
        log.debug("#getValue: key:{}", key);
        return service.getValue(key);
    }

    @PutMapping("/")
    public Map<String, Object> put(Map<String, String> properties) {
        log.debug("#put: {}", properties);
        return service.put(properties);
    }

    @PatchMapping("/")
    public Map<String, Object> patch(String kafkaBootStrapServers) {
        log.debug("#patch: kafka-bootstrap-servers: {}, topic: {}", kafkaBootStrapServers);
        return service.patch(kafkaBootStrapServers);
    }

    @DeleteMapping("/")
    public Map.Entry<String, Object> remove(String key) {
        log.debug("#remove: key: {}", key);
        return service.remove(key);
    }

    @PostMapping("/send/{topic}/{key}")
    public DocumentDto send(@PathVariable String topic, @PathVariable String key,
                               @RequestParam String content, @RequestParam String signInn, @RequestBody String signature) throws Exception {
        log.debug("#send: topic: {}, key: {}, document: {}", topic, key, content);

        return service.send(topic, key, DocumentDto.builder().content(content).signInn(signInn).signature(signature).build());
    }
}
