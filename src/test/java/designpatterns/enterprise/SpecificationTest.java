package designpatterns.enterprise;

import designpatterns.enterprise.specification.Product;
import designpatterns.enterprise.specification.ProductCatalog;
import designpatterns.enterprise.specification.Specification;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static designpatterns.enterprise.specification.ProductSpecifications.cheaperThan;
import static designpatterns.enterprise.specification.ProductSpecifications.inCategory;
import static designpatterns.enterprise.specification.ProductSpecifications.inStock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SpecificationTest {

    private final Product cable = new Product("cable", "electronics", new BigDecimal("100"), 3, 4.0);
    private final Product book = new Product("book", "books", new BigDecimal("300"), 0, 4.9);

    @Test
    void specificationsCombine() {
        Specification<Product> cheapAndAvailable = cheaperThan("200").and(inStock());

        assertTrue(cheapAndAvailable.isSatisfiedBy(cable));
        assertFalse(cheapAndAvailable.isSatisfiedBy(book));
        assertTrue(inCategory("books").or(inStock()).isSatisfiedBy(cable));
        assertTrue(inStock().not().isSatisfiedBy(book));
    }

    @Test
    void catalogFiltersWithAnySpecification() {
        ProductCatalog catalog = new ProductCatalog(List.of(cable, book));
        assertEquals(List.of(book), catalog.findAll(inCategory("books")));
        assertEquals(List.of(cable, book), catalog.findAll(p -> true));
    }
}
