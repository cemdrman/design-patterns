package designpatterns.enterprise.specification;

import java.util.List;

/** One generic finder instead of findByCategoryAndPriceLessThanAndStockGreaterThan... */
public class ProductCatalog {

    private final List<Product> products;

    public ProductCatalog(List<Product> products) {
        this.products = List.copyOf(products);
    }

    public List<Product> findAll(Specification<Product> specification) {
        return products.stream().filter(specification::isSatisfiedBy).toList();
    }
}
