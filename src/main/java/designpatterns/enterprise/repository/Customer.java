package designpatterns.enterprise.repository;

/** Domain object. {@code id} is null until the customer is saved. */
public record Customer(Long id, String name, String email, String city) {

    public static Customer newCustomer(String name, String email, String city) {
        return new Customer(null, name, email, city);
    }

    public Customer withId(Long id) {
        return new Customer(id, name, email, city);
    }
}
