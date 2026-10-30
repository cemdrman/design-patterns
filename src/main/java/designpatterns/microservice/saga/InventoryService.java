package designpatterns.microservice.saga;

import java.util.HashMap;
import java.util.Map;

/** Inventory microservice (simulated). */
public class InventoryService {

    private final Map<String, Integer> stock = new HashMap<>(Map.of("laptop", 5, "phone", 0));

    public void reserve(String product, int quantity) {
        int available = stock.getOrDefault(product, 0);
        if (available < quantity) {
            throw new IllegalStateException("out of stock: " + product);
        }
        stock.put(product, available - quantity);
    }

    public void release(String product, int quantity) {
        stock.merge(product, quantity, Integer::sum);
    }

    public int stockOf(String product) {
        return stock.getOrDefault(product, 0);
    }
}
