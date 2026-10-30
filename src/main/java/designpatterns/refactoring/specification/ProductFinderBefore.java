package designpatterns.refactoring.specification;

import designpatterns.enterprise.specification.Product;

import java.math.BigDecimal;
import java.util.List;

/**
 * BEFORE Specification – one finder method per combination of filters.
 * <p>The rule "in stock" is copied into several methods; the next screen needs
 * {@code findByCategoryAndInStockAndRatingAtLeast}, and so on.
 *
 * @see designpatterns.enterprise.specification.ProductCatalog the refactored version
 */
public class ProductFinderBefore {

    private final List<Product> products;

    public ProductFinderBefore(List<Product> products) {
        this.products = List.copyOf(products);
    }

    public List<Product> findByCategoryAndPriceLessThan(String category, String price) {
        BigDecimal limit = new BigDecimal(price);
        return products.stream()
                .filter(p -> p.category().equalsIgnoreCase(category) && p.price().compareTo(limit) < 0)
                .toList();
    }

    public List<Product> findInStockWithRatingAtLeast(double rating) {
        return products.stream().filter(p -> p.stock() > 0 && p.rating() >= rating).toList();
    }

    public List<Product> findByCategoryAndOutOfStock(String category) {
        return products.stream().filter(p -> p.category().equalsIgnoreCase(category) && p.stock() <= 0).toList();
    }
}
