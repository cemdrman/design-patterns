package designpatterns.microservice.saga;

import java.math.BigDecimal;
import java.util.List;

/** Defines the steps of the "place order" saga and their compensations. */
public class PlaceOrderSaga {

    private final InventoryService inventory;
    private final PaymentService payment;
    private final ShippingService shipping;
    private final SagaOrchestrator orchestrator = new SagaOrchestrator();

    public PlaceOrderSaga(InventoryService inventory, PaymentService payment, ShippingService shipping) {
        this.inventory = inventory;
        this.payment = payment;
        this.shipping = shipping;
    }

    public SagaResult place(String orderId, String customer, String product, int quantity, BigDecimal amount, String city) {
        return orchestrator.execute(List.of(
                new SagaStep("reserve stock",
                        () -> inventory.reserve(product, quantity),
                        () -> inventory.release(product, quantity)),
                new SagaStep("charge payment",
                        () -> payment.charge(customer, amount),
                        () -> payment.refund(customer, amount)),
                new SagaStep("schedule shipping",
                        () -> shipping.schedule(orderId, city),
                        () -> shipping.cancel(orderId))));
    }
}
