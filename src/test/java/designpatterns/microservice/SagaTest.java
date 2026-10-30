package designpatterns.microservice;

import designpatterns.microservice.saga.InventoryService;
import designpatterns.microservice.saga.PaymentService;
import designpatterns.microservice.saga.PlaceOrderSaga;
import designpatterns.microservice.saga.SagaResult;
import designpatterns.microservice.saga.ShippingService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SagaTest {

    private final InventoryService inventory = new InventoryService();
    private final PaymentService payment = new PaymentService();
    private final ShippingService shipping = new ShippingService();
    private final PlaceOrderSaga saga = new PlaceOrderSaga(inventory, payment, shipping);
    private final BigDecimal price = new BigDecimal("1000");

    @Test
    void allStepsSucceed() {
        SagaResult result = saga.place("O1", "cem", "laptop", 2, price, "Izmir");

        assertTrue(result.completed());
        assertEquals(3, inventory.stockOf("laptop"));
        assertEquals(new BigDecimal("99000"), payment.balanceOf("cem"));
        assertTrue(shipping.isScheduled("O1"));
    }

    @Test
    void failureCompensatesCompletedStepsInReverseOrder() {
        SagaResult result = saga.place("O2", "cem", "laptop", 1, price, "Paris");

        assertFalse(result.completed());
        assertEquals("schedule shipping", result.failedStep());
        assertEquals("↩ compensate charge payment", result.log().get(3));
        assertEquals("↩ compensate reserve stock", result.log().get(4));
        assertEquals(5, inventory.stockOf("laptop"));
        assertEquals(new BigDecimal("100000"), payment.balanceOf("cem"));
    }

    @Test
    void firstStepFailureNeedsNoCompensation() {
        SagaResult result = saga.place("O3", "cem", "phone", 1, price, "Ankara");

        assertEquals("reserve stock", result.failedStep());
        assertEquals(1, result.log().size());
    }
}
