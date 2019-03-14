package ru.demo.kafka.boot.bean;

import java.util.Map;


/**
 * Интерфейс для работы с прозводителем
 */
public interface Producer<E, R> {

    Map.Entry<String, Object> configure(String key, String value);

    void configure(Map<String, ?> properties);

    Map<String, Object> getProperties();

    Map.Entry<String, Object> getProperty(String key);

    R send(E record);
}
