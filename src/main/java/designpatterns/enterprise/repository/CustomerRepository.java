package designpatterns.enterprise.repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository: a collection-like interface for the domain. Callers don't know whether the data
 * lives in memory, in PostgreSQL, in MongoDB or behind a REST API.
 */
public interface CustomerRepository {

    Customer save(Customer customer);

    Optional<Customer> findById(long id);

    Optional<Customer> findByEmail(String email);

    List<Customer> findByCity(String city);

    List<Customer> findAll();

    boolean deleteById(long id);

    long count();
}
