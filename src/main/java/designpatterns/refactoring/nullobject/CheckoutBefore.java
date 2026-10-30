package designpatterns.refactoring.nullobject;

import designpatterns.enterprise.nullobject.Customer;
import designpatterns.enterprise.nullobject.RegisteredCustomer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * BEFORE Null Object – {@code null} means "guest", so every caller checks for it.
 * <p>Three checks in one small method; every new caller must remember them too.
 *
 * @see designpatterns.enterprise.nullobject.CheckoutService the refactored version
 */
public class CheckoutBefore {

    private final Map<String, Customer> customers = Map.of(
            "C1", new RegisteredCustomer("Cem", 10),
            "C2", new RegisteredCustomer("Ayşe", 5));

    public String receipt(String customerId, BigDecimal amount) {
        Customer customer = customerId == null ? null : customers.get(customerId);
        String name = customer != null ? customer.name() : "Guest";
        int discount = customer != null ? customer.discountPercent() : 0;
        BigDecimal rate = BigDecimal.valueOf(100 - discount).movePointLeft(2);
        BigDecimal total = amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
        return name + " pays " + total + " (" + discount + "% off)";
    }
}
