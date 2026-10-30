package designpatterns.enterprise.specification;

import java.math.BigDecimal;

/** Reusable, named business rules. */
public final class ProductSpecifications {

    private ProductSpecifications() {
    }

    public static Specification<Product> inCategory(String category) {
        return product -> product.category().equalsIgnoreCase(category);
    }

    public static Specification<Product> cheaperThan(String price) {
        BigDecimal limit = new BigDecimal(price);
        return product -> product.price().compareTo(limit) < 0;
    }

    public static Specification<Product> inStock() {
        return product -> product.stock() > 0;
    }

    public static Specification<Product> ratedAtLeast(double rating) {
        return product -> product.rating() >= rating;
    }
}
