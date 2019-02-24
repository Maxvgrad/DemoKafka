package ru.demo.kafka.demo.bean;

import org.springframework.stereotype.Component;

public interface Producer {

    void configure();

    void send();
}
