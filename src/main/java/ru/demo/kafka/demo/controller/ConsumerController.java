package ru.demo.kafka.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.demo.kafka.demo.DemoApplication;
import ru.demo.kafka.demo.service.ConsumerService;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = DemoApplication.APP_ROOT_PATH + "/consumer")
public class ConsumerController {

    private final ConsumerService consumerService;

    @PatchMapping("/")
    public Map<String, Object> patch(String bootStrapServers, String groupId, String autoOffset) {
        log.debug("#patch: bootStrapServers({}), groupId({}), autoOffset({})", bootStrapServers, groupId, autoOffset);
        return consumerService.patch(bootStrapServers, groupId, autoOffset);
    }

    @GetMapping("/")
    public Map<String, Object> get() {
        log.debug("#get: ");
        return consumerService.getProperties();
    }

    @DeleteMapping("/{key}")
    public Map.Entry<String, Object> remove(@PathVariable String key) {
        log.debug("#remove: key({})", key);
        return consumerService.remove(key);
    }

    @PutMapping("/")
    public Map<String, Object> put(String key, String value) {
        return consumerService.configure(key, value);
    }

    @PostMapping("/")
    public ConsumerRecords<String, String> poll() {
        log.debug("#poll:");
        return consumerService.poll();
    }

    @PatchMapping("/{topic}")
    public String subscribe(@PathVariable String topic) {
        log.debug("#poll: topic({})", topic);
        return consumerService.subscribe(topic);
    }
}
