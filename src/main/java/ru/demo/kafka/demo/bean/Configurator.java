package ru.demo.kafka.demo.bean;


import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Configurator<K, V> {

    private Map<K, V> properties = new HashMap<>();

    private boolean needRefresh = true;

    public Map.Entry<K, V> put(K key, V value) {
        V oldValue = this.properties.put(key, value);
        needRefresh = needRefresh || !value.equals(oldValue);
        return new ImmutablePair<>(key, oldValue);
    }

    public Map<K, V> putAll(Map<K, V> properties) {

        if (properties != null && !properties.isEmpty()) {
            this.properties.putAll(properties);
            needRefresh = true;
        }

        return new HashMap<>(this.properties);
    }

    public Map<K, V> get() {
        return new HashMap<>(properties);
    }

    public Map.Entry<K, V> get(K key) {
        return new ImmutablePair<>(key, properties.get(key));
    }

    public boolean isNeedRefresh() {
        return needRefresh;
    }

    public int size() {
        return properties.size();
    }

    public Map<K, V> refresh() {
        needRefresh = false;
        return get();
    }

    public V remove(String key) {
        return properties.remove(key);
    }

}
