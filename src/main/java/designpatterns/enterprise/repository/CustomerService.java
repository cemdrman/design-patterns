package designpatterns.enterprise.repository;

/** Business logic talks to the repository interface, never to the storage technology. */
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer register(String name, String email, String city) {
        if (repository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("E-mail already registered: " + email);
        }
        return repository.save(Customer.newCustomer(name, email, city));
    }

    public Customer moveTo(long customerId, String newCity) {
        Customer customer = repository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + customerId));
        return repository.save(new Customer(customer.id(), customer.name(), customer.email(), newCity));
    }
}
