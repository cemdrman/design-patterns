package designpatterns.microservice.cqrs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Command side (write model): validates business rules and changes state.
 * It returns nothing useful for display; it only publishes events.
 */
public class ProductCommandService {

    /** Write model: normalized and optimized for consistency, not for queries. */
    private record Product(String sku, String name, BigDecimal price, int stock) {
    }

    private final Map<String, Product> products = new HashMap<>();
    private final List<Consumer<ProductEvent>> subscribers = new ArrayList<>();

    public void subscribe(Consumer<ProductEvent> subscriber) {
        subscribers.add(subscriber);
    }

    public void create(String sku, String name, String price) {
        if (products.containsKey(sku)) {
            throw new IllegalArgumentException("SKU already exists: " + sku);
        }
        BigDecimal amount = positive(price);
        products.put(sku, new Product(sku, name, amount, 0));
        publish(new ProductEvent.ProductCreated(sku, name, amount));
    }

    public void changePrice(String sku, String price) {
        Product product = get(sku);
        BigDecimal amount = positive(price);
        products.put(sku, new Product(sku, product.name(), amount, product.stock()));
        publish(new ProductEvent.PriceChanged(sku, amount));
    }

    public void addStock(String sku, int quantity) {
        changeStock(sku, quantity);
    }

    public void sell(String sku, int quantity) {
        changeStock(sku, -quantity);
    }

    private void changeStock(String sku, int delta) {
        Product product = get(sku);
        int newStock = product.stock() + delta;
        if (newStock < 0) {
            throw new IllegalStateException("Not enough stock for " + sku);
        }
        products.put(sku, new Product(sku, product.name(), product.price(), newStock));
        publish(new ProductEvent.StockChanged(sku, newStock));
    }

    private Product get(String sku) {
        Product product = products.get(sku);
        if (product == null) {
            throw new IllegalArgumentException("Unknown SKU: " + sku);
        }
        return product;
    }

    private static BigDecimal positive(String price) {
        BigDecimal amount = new BigDecimal(price);
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        return amount;
    }

    private void publish(ProductEvent event) {
        subscribers.forEach(subscriber -> subscriber.accept(event));
    }
}
