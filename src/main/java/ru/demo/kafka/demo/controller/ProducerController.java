package ru.demo.kafka.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.demo.kafka.demo.DemoApplication;
import ru.demo.kafka.demo.dto.ProducerRecord;
import ru.demo.kafka.demo.service.ProducerService;

@Slf4j
@RestController(DemoApplication.APP_ROOT_PATH + "/producer")
@RequiredArgsConstructor
public class ProducerController {

    private final ProducerService service;

    @PostMapping("/configure")
    public ResponseEntity configure(@RequestBody String bootstrapServers) {
        log.debug("#configure: bootstrapServers({})", bootstrapServers);
        service.configure(bootstrapServers);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send")
    public ResponseEntity send(@RequestBody ProducerRecord record) {
        log.debug("#send: {}", record);

        return ResponseEntity.ok().build();
    }

}
