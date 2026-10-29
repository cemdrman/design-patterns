package designpatterns.creational.prototype;

import java.util.HashMap;
import java.util.Map;

/**
 * Prototype registry: keeps pre-configured prototypes and hands out copies of them.
 */
public class AddressRegistry {

    private final Map<String, Address> prototypes = new HashMap<>();

    public void register(String key, Address prototype) {
        prototypes.put(key, prototype);
    }

    public Address create(String key) {
        Address prototype = prototypes.get(key);
        if (prototype == null) {
            throw new IllegalArgumentException("No prototype registered for " + key);
        }
        return prototype.copy();
    }
}
