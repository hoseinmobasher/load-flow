package xyz.hossein.configuration;

import java.util.HashMap;
import java.util.Map;

public class Context {
    public Object getElement(Object key) {
        return elements.get(key);
    }

    public <T> void putElement(Object key, T element) {
        elements.put(key, element);
    }

    private Map<Object, Object> elements = new HashMap<>();
}
