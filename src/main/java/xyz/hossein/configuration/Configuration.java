package xyz.hossein.configuration;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.hossein.standard.annotations.*;

import java.lang.reflect.Field;
import java.util.*;

public class Configuration {
	private static final Logger logger = LoggerFactory.getLogger(Configuration.class);

	public static Context run(Class<?> clazz) {
		if (clazz == null) {
			throw new RuntimeException("clazz should be passed as argument");
		}

		if (!clazz.isAnnotationPresent(xyz.hossein.standard.annotations.Configuration.class)) {
			throw new RuntimeException(String.format("%s doesn't annotate as Configuration.", clazz.getName()));
		}

		xyz.hossein.standard.annotations.Configuration configuration = clazz.getAnnotation(xyz.hossein.standard.annotations.Configuration.class);

		List<String> packages = new ArrayList<>();
		packages.add(configuration.defaultPackage());

		if (configuration.defaultPackages() != null) {
			for (String defaultPackage : configuration.defaultPackages()) {
				packages.add(defaultPackage);
			}
		}

		Context context = new Context();

		for (String defaultPackage : packages) {
			Reflections reflections = new Reflections(defaultPackage);
			initializeAlgorithms(context, reflections);
			initializeMessages(context, reflections);
			initializeProcessors(context, reflections);
			initializePOJOs(context, reflections);

			reflections = new Reflections(defaultPackage, new FieldAnnotationsScanner());
			initializeWires(context, reflections);
		}

		return context;
	}

	private static void initializePOJOs(Context context, Reflections reflections) {
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Pojo.class);

		annotated.forEach(pojo -> {
			try {
				logger.debug("Making an instance from POJO {}", pojo.getName());
				context.putElement(Pojo.class, pojo.getAnnotation(Pojo.class).value(),
					pojo.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
	}

	private static void initializeWires(Context context, Reflections reflections) {
		Set<Field> fields = reflections.getFieldsAnnotatedWith(Wire.class);

		for (Field field : fields) {
			if (field.getDeclaringClass().isAnnotationPresent(Pojo.class)) {
				Map.Entry<String, Optional<Object>> element = context.getElement(field);

				element.getValue().ifPresent(result -> {
					Object object = context.getElement(Pojo.class, field.getDeclaringClass().getAnnotation(Pojo.class).value());

					try {
						Field value = object.getClass().getDeclaredField(field.getName());

						if (value != null) {
							value.setAccessible(true);
							value.set(object, result);
						}

						logger.trace("Class= {}, Key= {}, Value= {}", field.getDeclaringClass().getName(), element.getKey(), result);
					} catch (NoSuchFieldException | IllegalAccessException e) {
						e.printStackTrace();
					}
				});
			}
		}
	}

	private static void initializeProcessors(Context context, Reflections reflections) {
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Processor.class);

		annotated.forEach(processor -> {
			try {
				logger.debug("Making an instance from processor {}", processor.getName());
				context.putElement(Processor.class, processor.getAnnotation(Processor.class).hashCode(),
					processor.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
	}

	private static void initializeMessages(Context context, Reflections reflections) {
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Message.class);

		annotated.forEach(message -> {
			try {
				logger.debug("Making an instance from message {}", message.getName());
				context.putElement(Message.class, message.getAnnotation(Message.class).hashCode(),
					message.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
	}

	private static void initializeAlgorithms(Context context, Reflections reflections) {
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Algorithm.class);

		annotated.forEach(algorithm -> {
			try {
				logger.debug("Making an instance from algorithm {}, {}", algorithm.getAnnotation(Algorithm.class).value(), algorithm.getName());

				if (algorithm.getAnnotation(Algorithm.class).value().isEmpty()) {
					context.putElement(Algorithm.class, algorithm.getName(), algorithm.newInstance());
				} else {
					context.putElement(Algorithm.class, algorithm.getAnnotation(Algorithm.class).value(),
						algorithm.newInstance());
				}
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
	}
}
