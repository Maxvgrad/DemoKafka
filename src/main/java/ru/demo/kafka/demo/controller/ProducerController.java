package ru.demo.kafka.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.demo.kafka.demo.DemoApplication;
import ru.demo.kafka.demo.dto.DocumentDto;
import ru.demo.kafka.demo.service.ProducerService;

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
    public Map<String, Object> patch(String kafkaBootStrapServers, String groupId) {
        log.debug("#patch: kafka-bootstrap-servers: {}, groupId: {}, topic: {}", kafkaBootStrapServers, groupId);
        return service.patch(kafkaBootStrapServers, groupId);
    }

    @DeleteMapping("/")
    public Map.Entry<String, Object> remove(String key) {
        log.debug("#remove: key: {}", key);
        return service.remove(key);
    }

    @PostMapping("/send/{topic}/{key}")
    public ResponseEntity send(@PathVariable String topic, @PathVariable String key,
                               @RequestBody DocumentDto document) throws Exception {
        log.debug("#send: topic: {}, key: {}, document: {}", topic, key, document);
        service.send(topic, key, document);
        return ResponseEntity.ok().build();
    }
}
