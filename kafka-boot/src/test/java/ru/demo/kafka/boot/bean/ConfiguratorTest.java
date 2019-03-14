package ru.demo.kafka.boot.bean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConfiguratorTest {

    private static final String KEY_1 = "key_1";
    private static final String KEY_2 = "key_2";
    private static final String KEY_3 = "key_3";
    private static final String VALUE_1 = "value_1";
    private static final String VALUE_2 = "value_2";

    private Configurator<String, String> configurator;

    @BeforeEach
    void setUp() {
        configurator = new Configurator<>();
    }

    @Test
    void initialConfiguratorState() {
        assertEquals(0, configurator.size());
        assertNotNull(configurator.get());
    }

    @Test
    void putNoPreviousValue() {
        Map.Entry<String, String> result = configurator.put(KEY_1, VALUE_1);

        assertEquals(KEY_1, result.getKey());
        assertNull(result.getValue());
        assertEquals(1, configurator.get().size());
        assertEquals(VALUE_1, configurator.get(KEY_1).getValue());
    }

    @Test
    void putReplacePreviousValue() {
        configurator.put(KEY_1, VALUE_1);

        Map.Entry<String, String> result = configurator.put(KEY_1, VALUE_2);

        assertEquals(KEY_1, result.getKey());
        assertEquals(VALUE_1, result.getValue());
        assertEquals(1, configurator.get().size());
        assertEquals(VALUE_2, configurator.get(KEY_1).getValue());
    }

    @Test
    void putReturnImmutableEntry() {
        Map.Entry entry = configurator.put(KEY_1, VALUE_1);
        assertThrows(UnsupportedOperationException.class, () -> entry.setValue(null));
    }

    @Test
    void putAll() {
        Map<String, String> properties = new HashMap<>();
        properties.put(KEY_1, VALUE_1);
        properties.put(KEY_2, VALUE_2);

        configurator.putAll(properties);

        assertEquals(2, configurator.size());
    }

    @Test
    void putAllConfiguratorImmutability() {
        Map<String, String> properties = new HashMap<>();
        properties.put(KEY_1, VALUE_1);
        properties.put(KEY_2, VALUE_2);

        Map<String, String> result = configurator.putAll(properties);
        result.put(KEY_3, VALUE_1);

        assertEquals(2, configurator.size());
    }

    @Test
    void getNullKey() {
        Map.Entry<String, String> result = configurator.get(null);
        assertNull(result.getKey());
        assertNull(result.getValue());
    }

    @Test
    void getPropertyNotPresent() {
        Map.Entry<String, String> result = configurator.get(KEY_1);
        assertEquals(KEY_1, result.getKey());
        assertNull(result.getValue());
    }

    @Test
    void getPropertySuccess() {
        configurator.put(KEY_1, VALUE_1);

        Map.Entry<String, String> result = configurator.get(KEY_1);

        assertEquals(KEY_1, result.getKey());
        assertEquals(VALUE_1, result.getValue());
    }

    @Test
    void isNeedRefreshAfterPut() {
        configurator.put(KEY_1, VALUE_1);
        assertTrue(configurator.isNeedRefresh());
    }

    @Test
    void isNeedRefreshAfterPutExistingKeyAndValue() {
        configurator.put(KEY_1, VALUE_1);
        configurator.refresh();
        configurator.put(KEY_1, VALUE_1);

        assertFalse(configurator.isNeedRefresh());
    }

    @Test
    void isNeedRefreshAfterPutAllEmptyMap() {
        configurator.putAll(new HashMap<>());
        configurator.refresh();

        assertFalse(configurator.isNeedRefresh());
    }

    @Test
    void isNeedRefreshAfterPutAll() {
        Map<String, String> properties = new HashMap<>();
        properties.put(KEY_1, VALUE_1);
        properties.put(KEY_2, VALUE_2);

        configurator.putAll(properties);

        assertTrue(configurator.isNeedRefresh());
    }
}