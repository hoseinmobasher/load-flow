package xyz.hossein.configuration;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.hossein.standard.annotations.*;

import java.lang.reflect.Field;
import java.util.Set;

public class LoadFlowConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(LoadFlowConfiguration.class);

    public static Context run(Class<?> clazz) {
        if (clazz == null) {
            throw new RuntimeException("clazz should be passed as argument");
        }

        if (!clazz.isAnnotationPresent(Configuration.class)) {
            throw new RuntimeException(String.format("%s doesn't annotate as Configuration.", clazz.getName()));
        }

        Configuration configuration = clazz.getAnnotation(Configuration.class);
        Context context = new Context();

        Reflections reflections = new Reflections(configuration.defaultPackage());
        initializeAlgorithms(context, reflections);
        initializeRequests(context, reflections);
        initializeResponses(context, reflections);
        initializeProcessors(context, reflections);
        initializePojos(context, reflections);

        reflections = new Reflections(configuration.defaultPackage(), new FieldAnnotationsScanner());
        initializeWires(context, reflections);

        return context;
    }

    private static void initializePojos(Context context, Reflections reflections) {
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Pojo.class);

        annotated.forEach(pojo -> {
            try {
                logger.info("Making an instance from POJO {}", pojo.getName());
                context.putElement(pojo.getAnnotation(Pojo.class).hashCode(),
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
                Wire wire = field.getAnnotation(Wire.class);

                String key = wire.value();
                if (wire.value().isEmpty()) {
                    key = field.getDeclaringClass().getTypeName() + "."
                            + field.getName();
                }

                logger.info("Class= {}, Key= {}", field.getDeclaringClass().getName(), key);
            }
        }
    }

    private static void initializeProcessors(Context context, Reflections reflections) {
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Processor.class);

        annotated.forEach(processor -> {
            try {
                logger.info("Making an instance from processor {}", processor.getName());
                context.putElement(processor.getAnnotation(Processor.class).hashCode(),
                        processor.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private static void initializeResponses(Context context, Reflections reflections) {
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Response.class);

        annotated.forEach(request -> {
            try {
                logger.info("Making an instance from response {}", request.getName());
                context.putElement(request.getAnnotation(Response.class).hashCode(),
                        request.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private static void initializeRequests(Context context, Reflections reflections) {
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Request.class);

        annotated.forEach(request -> {
            try {
                logger.info("Making an instance from request {}", request.getName());
                context.putElement(request.getAnnotation(Request.class).hashCode(),
                        request.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private static void initializeAlgorithms(Context context, Reflections reflections) {
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Algorithm.class);

        annotated.forEach(algorithm -> {
            try {
                logger.info("Making an instance from algorithm {}, {}", algorithm.getAnnotation(Algorithm.class).value(), algorithm.getName());

                if (algorithm.getAnnotation(Algorithm.class).value().isEmpty()) {
                    context.putElement(algorithm.getName(), algorithm.newInstance());
                } else {
                    context.putElement(algorithm.getAnnotation(Algorithm.class).value(),
                            algorithm.newInstance());
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}
