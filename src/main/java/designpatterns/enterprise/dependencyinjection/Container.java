package designpatterns.enterprise.dependencyinjection;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * A minimal IoC container, only to show the idea behind Spring / Guice / CDI:
 * you register how to build each type, the container builds the object graph
 * and keeps one instance per type (singleton scope).
 */
public class Container {

    private final Map<Class<?>, Function<Container, ?>> providers = new HashMap<>();
    private final Map<Class<?>, Object> instances = new HashMap<>();

    public <T> Container register(Class<T> type, Function<Container, ? extends T> provider) {
        providers.put(type, provider);
        return this;
    }

    public <T> T get(Class<T> type) {
        Object instance = instances.get(type);
        if (instance == null) {
            Function<Container, ?> provider = providers.get(type);
            if (provider == null) {
                throw new IllegalStateException("No provider registered for " + type.getSimpleName());
            }
            instance = provider.apply(this);
            instances.put(type, instance);
        }
        return type.cast(instance);
    }
}
