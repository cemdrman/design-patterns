package designpatterns.microservice.cqrs;

import designpatterns.common.Console;

public class CqrsDemo {

    public static void main(String[] args) {
        Console.title("CQRS");

        ProductCommandService commands = new ProductCommandService();
        ProductQueryService queries = new ProductQueryService();
        commands.subscribe(queries::on);

        Console.section("Commands change state");
        commands.create("KB-1", "Mechanical keyboard", "1249.90");
        commands.create("MS-1", "Wireless mouse", "450.00");
        commands.create("CB-1", "USB-C cable", "120.00");
        commands.addStock("KB-1", 10);
        commands.addStock("MS-1", 1);
        commands.addStock("CB-1", 50);
        commands.sell("MS-1", 1);
        commands.changePrice("KB-1", "1099.90");

        try {
            commands.sell("MS-1", 1);
        } catch (IllegalStateException e) {
            Console.print("command rejected", e.getMessage());
        }

        Console.section("Queries read the projection");
        queries.findAll().forEach(view -> Console.print(view.sku(), view.label()));
        Console.print("available, cheapest first", queries.findAvailable().stream().map(ProductView::name).toList());
    }
}
