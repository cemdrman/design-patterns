package designpatterns.enterprise;

import designpatterns.enterprise.nullobject.CheckoutService;
import designpatterns.enterprise.nullobject.CustomerDirectory;
import designpatterns.enterprise.nullobject.GuestCustomer;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class NullObjectTest {

    @Test
    void unknownCustomersBecomeTheNullObject() {
        CustomerDirectory directory = new CustomerDirectory();
        assertSame(GuestCustomer.INSTANCE, directory.find("nope"));
        assertSame(GuestCustomer.INSTANCE, directory.find(null));
    }

    @Test
    void clientCodeNeedsNoNullChecks() {
        CheckoutService checkout = new CheckoutService(new CustomerDirectory());
        assertEquals("Cem pays 90.00 (10% off)", checkout.receipt("C1", new BigDecimal("100")));
        assertEquals("Guest pays 100.00 (0% off)", checkout.receipt(null, new BigDecimal("100")));
    }
}
