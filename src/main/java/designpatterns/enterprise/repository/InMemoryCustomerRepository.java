package designpatterns.enterprise.repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/** One implementation of the repository. A JDBC or JPA version would implement the same interface. */
public class InMemoryCustomerRepository implements CustomerRepository {

    private final Map<Long, Customer> table = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    @Override
    public Customer save(Customer customer) {
        Customer saved = customer.id() == null ? customer.withId(sequence.incrementAndGet()) : customer;
        table.put(saved.id(), saved);
        return saved;
    }

    @Override
    public Optional<Customer> findById(long id) {
        return Optional.ofNullable(table.get(id));
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return table.values().stream().filter(c -> c.email().equalsIgnoreCase(email)).findFirst();
    }

    @Override
    public List<Customer> findByCity(String city) {
        return table.values().stream()
                .filter(c -> c.city().equalsIgnoreCase(city))
                .sorted(Comparator.comparing(Customer::id))
                .toList();
    }

    @Override
    public List<Customer> findAll() {
        return table.values().stream().sorted(Comparator.comparing(Customer::id)).toList();
    }

    @Override
    public boolean deleteById(long id) {
        return table.remove(id) != null;
    }

    @Override
    public long count() {
        return table.size();
    }
}
