package designpatterns.enterprise.specification;

import designpatterns.common.Console;

import java.math.BigDecimal;
import java.util.List;

import static designpatterns.enterprise.specification.ProductSpecifications.cheaperThan;
import static designpatterns.enterprise.specification.ProductSpecifications.inCategory;
import static designpatterns.enterprise.specification.ProductSpecifications.inStock;
import static designpatterns.enterprise.specification.ProductSpecifications.ratedAtLeast;

public class SpecificationDemo {

    public static void main(String[] args) {
        Console.title("Specification");

        ProductCatalog catalog = new ProductCatalog(List.of(
                new Product("Mechanical keyboard", "electronics", new BigDecimal("1249.90"), 12, 4.7),
                new Product("Wireless mouse", "electronics", new BigDecimal("450.00"), 0, 4.4),
                new Product("USB-C cable", "electronics", new BigDecimal("120.00"), 80, 3.9),
                new Product("Clean Code", "books", new BigDecimal("390.00"), 5, 4.8),
                new Product("Design Patterns", "books", new BigDecimal("650.00"), 0, 4.6)));

        Specification<Product> cheapElectronics = inCategory("electronics").and(cheaperThan("500"));
        Specification<Product> buyableAndGood = inStock().and(ratedAtLeast(4.5));
        Specification<Product> soldOutBooks = inCategory("books").and(inStock().not());

        Console.print("cheap electronics", names(catalog.findAll(cheapElectronics)));
        Console.print("in stock and rated >= 4.5", names(catalog.findAll(buyableAndGood)));
        Console.print("sold out books", names(catalog.findAll(soldOutBooks)));
        Console.print("books or < 200 TL", names(catalog.findAll(inCategory("books").or(cheaperThan("200")))));
    }

    private static List<String> names(List<Product> products) {
        return products.stream().map(Product::name).toList();
    }
}
