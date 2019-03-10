package ru.demo.kafka.demo;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class ReflectionHelper {

    private ReflectionHelper() {}

    public static void setField(String fieldName, Object obj, Object value) {
        Field field = ReflectionUtils.findField(obj.getClass(), fieldName);
        field.setAccessible(true);
        ReflectionUtils.setField(field, obj, value);
    }
}
