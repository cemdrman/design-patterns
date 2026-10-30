package designpatterns.enterprise.nullobject;

import java.util.Map;

/** Never returns null: unknown ids map to the null object. */
public class CustomerDirectory {

    private final Map<String, Customer> customers = Map.of(
            "C1", new RegisteredCustomer("Cem", 10),
            "C2", new RegisteredCustomer("Ayşe", 5));

    public Customer find(String customerId) {
        return customerId == null ? GuestCustomer.INSTANCE : customers.getOrDefault(customerId, GuestCustomer.INSTANCE);
    }
}
