package designpatterns.structural.flyweight;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** Flyweight factory: returns an existing flyweight or creates it once. */
public final class TreeTypeFactory {

    private static final Map<String, TreeType> CACHE = new ConcurrentHashMap<>();

    private TreeTypeFactory() {
    }

    public static TreeType get(String name, String color, String texture) {
        return CACHE.computeIfAbsent(name + "|" + color + "|" + texture,
                key -> new TreeType(name, color, texture));
    }

    public static int cachedTypes() {
        return CACHE.size();
    }
}
