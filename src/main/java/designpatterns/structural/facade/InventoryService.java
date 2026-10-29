package designpatterns.structural.facade;

import java.util.HashMap;
import java.util.Map;

/** Subsystem class. */
public class InventoryService {

    private final Map<String, Integer> stock = new HashMap<>(Map.of("BOOK-1", 5, "LAPTOP-1", 0));

    public boolean isAvailable(String productId, int quantity) {
        return stock.getOrDefault(productId, 0) >= quantity;
    }

    public void reserve(String productId, int quantity) {
        stock.merge(productId, -quantity, Integer::sum);
    }
}
