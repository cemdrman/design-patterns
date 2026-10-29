package designpatterns.structural;

import designpatterns.structural.facade.InventoryService;
import designpatterns.structural.facade.NotificationService;
import designpatterns.structural.facade.OrderFacade;
import designpatterns.structural.facade.PaymentService;
import designpatterns.structural.facade.ShippingService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FacadeTest {

    @Test
    void placesOrderThroughAllSubsystems() {
        NotificationService notifications = new NotificationService();
        OrderFacade facade = new OrderFacade(new InventoryService(), new PaymentService(),
                new ShippingService(), notifications);

        assertTrue(facade.placeOrder("cem", "BOOK-1", 5, BigDecimal.TEN).success());
        assertFalse(facade.placeOrder("cem", "BOOK-1", 1, BigDecimal.TEN).success()); // stock is now empty
        assertEquals(1, notifications.sent().size());
    }
}
