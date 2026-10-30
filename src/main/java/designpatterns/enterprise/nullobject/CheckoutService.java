package designpatterns.enterprise.nullobject;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Thanks to the null object there is no {@code if (customer != null)} anywhere here. */
public class CheckoutService {

    private final CustomerDirectory directory;

    public CheckoutService(CustomerDirectory directory) {
        this.directory = directory;
    }

    public String receipt(String customerId, BigDecimal amount) {
        Customer customer = directory.find(customerId);
        BigDecimal rate = BigDecimal.valueOf(100 - customer.discountPercent()).movePointLeft(2);
        BigDecimal total = amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
        return customer.name() + " pays " + total + " (" + customer.discountPercent() + "% off)";
    }
}
