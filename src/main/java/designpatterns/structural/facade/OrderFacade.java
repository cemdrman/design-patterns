package designpatterns.structural.facade;

import java.math.BigDecimal;

/**
 * Facade: offers one simple method for a process that needs four subsystems.
 * Clients do not need to know the order of the calls or the subsystem classes.
 */
public class OrderFacade {

    private final InventoryService inventory;
    private final PaymentService payment;
    private final ShippingService shipping;
    private final NotificationService notification;

    public OrderFacade(InventoryService inventory, PaymentService payment,
                       ShippingService shipping, NotificationService notification) {
        this.inventory = inventory;
        this.payment = payment;
        this.shipping = shipping;
        this.notification = notification;
    }

    public OrderResult placeOrder(String customerId, String productId, int quantity, BigDecimal price) {
        if (!inventory.isAvailable(productId, quantity)) {
            return OrderResult.failed("Out of stock: " + productId);
        }
        if (!payment.charge(customerId, price.multiply(BigDecimal.valueOf(quantity)))) {
            return OrderResult.failed("Payment rejected");
        }
        inventory.reserve(productId, quantity);
        String trackingNo = shipping.createShipment(customerId, productId);
        notification.sendEmail(customerId, "Your order is on its way: " + trackingNo);
        return new OrderResult(true, trackingNo);
    }

    public record OrderResult(boolean success, String detail) {

        static OrderResult failed(String reason) {
            return new OrderResult(false, reason);
        }
    }
}
