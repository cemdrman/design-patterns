package designpatterns.microservice.cqrs;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Query side (read model): a projection updated from events. It never validates or changes
 * business state, it only answers questions fast. In production it often lives in another
 * database (Elasticsearch, Redis, a read replica) and is <b>eventually consistent</b>.
 */
public class ProductQueryService {

    private final Map<String, ProductView> views = new ConcurrentHashMap<>();

    /** Projection: applies one event to the read model. */
    public void on(ProductEvent event) {
        switch (event) {
            case ProductEvent.ProductCreated e -> views.put(e.sku(), view(e.sku(), e.name(), e.price(), false));
            case ProductEvent.PriceChanged e -> views.computeIfPresent(e.sku(),
                    (sku, v) -> view(sku, v.name(), e.newPrice(), v.available()));
            case ProductEvent.StockChanged e -> views.computeIfPresent(e.sku(),
                    (sku, v) -> view(sku, v.name(), v.price(), e.newStock() > 0));
        }
    }

    private static ProductView view(String sku, String name, BigDecimal price, boolean available) {
        return new ProductView(sku, name, price, available, name + " – " + price + " TL" + (available ? "" : " (sold out)"));
    }

    public Optional<ProductView> findBySku(String sku) {
        return Optional.ofNullable(views.get(sku));
    }

    public List<ProductView> findAvailable() {
        return views.values().stream()
                .filter(ProductView::available)
                .sorted(Comparator.comparing(ProductView::price))
                .toList();
    }

    public List<ProductView> findAll() {
        return views.values().stream().sorted(Comparator.comparing(ProductView::sku)).toList();
    }
}
