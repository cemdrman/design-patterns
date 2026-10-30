package designpatterns.microservice.saga;

import java.util.HashSet;
import java.util.Set;

/** Shipping microservice (simulated). */
public class ShippingService {

    private static final Set<String> SUPPORTED_CITIES = Set.of("Istanbul", "Ankara", "Izmir");

    private final Set<String> scheduled = new HashSet<>();

    public void schedule(String orderId, String city) {
        if (!SUPPORTED_CITIES.contains(city)) {
            throw new IllegalStateException("no delivery to " + city);
        }
        scheduled.add(orderId);
    }

    public void cancel(String orderId) {
        scheduled.remove(orderId);
    }

    public boolean isScheduled(String orderId) {
        return scheduled.contains(orderId);
    }
}
