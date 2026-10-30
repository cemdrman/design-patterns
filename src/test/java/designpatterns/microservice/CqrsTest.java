package designpatterns.microservice;

import designpatterns.microservice.cqrs.ProductCommandService;
import designpatterns.microservice.cqrs.ProductQueryService;
import designpatterns.microservice.cqrs.ProductView;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CqrsTest {

    private final ProductCommandService commands = new ProductCommandService();
    private final ProductQueryService queries = new ProductQueryService();

    CqrsTest() {
        commands.subscribe(queries::on);
    }

    @Test
    void commandsUpdateTheReadModelThroughEvents() {
        commands.create("A", "Alpha", "50");
        commands.create("B", "Beta", "20");
        assertTrue(queries.findAvailable().isEmpty());

        commands.addStock("A", 1);
        commands.addStock("B", 1);
        commands.changePrice("A", "10");

        assertEquals(List.of("Alpha", "Beta"), queries.findAvailable().stream().map(ProductView::name).toList());
        assertEquals(new BigDecimal("10"), queries.findBySku("A").orElseThrow().price());

        commands.sell("A", 1);
        ProductView alpha = queries.findBySku("A").orElseThrow();
        assertFalse(alpha.available());
        assertEquals("Alpha – 10 TL (sold out)", alpha.label());
    }

    @Test
    void invalidCommandsAreRejectedAndDoNotReachTheReadModel() {
        commands.create("A", "Alpha", "50");
        assertThrows(IllegalStateException.class, () -> commands.sell("A", 1));
        assertThrows(IllegalArgumentException.class, () -> commands.changePrice("A", "-1"));
        assertThrows(IllegalArgumentException.class, () -> commands.create("A", "Again", "1"));
        assertEquals(new BigDecimal("50"), queries.findBySku("A").orElseThrow().price());
    }
}
