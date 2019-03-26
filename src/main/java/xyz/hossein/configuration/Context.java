package xyz.hossein.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.hossein.standard.annotations.Wire;
import xyz.hossein.util.Util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Context {
	private static final Logger logger = LoggerFactory.getLogger(Context.class);
	private Map<Class, Map<Object, Object>> elements = new HashMap<>();

	public Object getElement(Class clazz, Object key) {
		return elements.get(clazz).get(key);
	}

	public Map.Entry<String, Optional<Object>> getElement(Field field) {
		if (Util.isCollection(field.getType())) {
			return getElementCollection(field);
		} else {
			return getDesiredElement(field);
		}
	}

	private Map.Entry<String, Optional<Object>> getDesiredElement(Field field) {
		Wire wire = field.getAnnotation(Wire.class);

		String key = wire.value();
		if (wire.value().isEmpty()) {
			key = field.getName();
		}

		final String finalKey = key;

		AtomicReference<Optional<Object>> value = new AtomicReference<>(Optional.empty());

		// Match with name
		elements.forEach((c, m) -> {
			m.forEach((k, v) -> {
				if (k.equals(finalKey)
					&& Util.isInstance(v.getClass(), field.getType())) {
					value.set(Optional.of(v));
				}
			});
		});

		if (value.get().isPresent()) {
			return new AbstractMap.SimpleImmutableEntry<>(key, value.get());
		}

		//  Match with type
		elements.forEach((c, m) -> {
			m.forEach((k, v) -> {
				if (Util.isInstance(v.getClass(), field.getType())) {
					value.set(Optional.of(v));
				}
			});
		});

		return new AbstractMap.SimpleImmutableEntry<>(key, value.get());
	}

	private Map.Entry<String, Optional<Object>> getElementCollection(Field field) {
		Wire wire = field.getAnnotation(Wire.class);

		String key = wire.value();
		if (wire.value().isEmpty()) {
			key = field.getName();
		}

		Class clazz = (Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
		Collection<Object> collection = new ArrayList<>();

		elements.forEach((c, m) -> {
			m.forEach((k, v) -> {
				if (Util.isInstance(v.getClass(), clazz)) {
					collection.add(v);
				}
			});
		});

		return new AbstractMap.SimpleImmutableEntry<>(key, Optional.of(collection));
	}

	public Map<Object, Object> getElements(Class clazz) {
		return elements.get(clazz);
	}

	public <T> void putElement(Class clazz, Object key, T element) {
		if (!elements.containsKey(clazz)) {
			elements.put(clazz, new HashMap<>());
		}

		elements.get(clazz).put(key, element);
	}
}
