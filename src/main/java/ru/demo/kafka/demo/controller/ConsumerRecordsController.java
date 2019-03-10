package ru.demo.kafka.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.demo.kafka.demo.DemoApplication;
import ru.demo.kafka.demo.bean.ConsumerRecordsRegistry;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(DemoApplication.APP_ROOT_PATH + "/records")
public class ConsumerRecordsController {

    private final ConsumerRecordsRegistry<String, String> registry;


    @GetMapping("/{topic}")
    public Map<Long, ConsumerRecord<String, String>> get(@PathVariable String topic) {
        log.debug("#get: topic({})", topic);
        return registry.get(topic);
    }

    @GetMapping("/")
    public Iterable<Map<Long, ConsumerRecord<String, String>>> getAll() {
        log.debug("#getAll: ");
        return registry.get();
    }
}
