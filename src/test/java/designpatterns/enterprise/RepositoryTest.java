package designpatterns.enterprise;

import designpatterns.enterprise.repository.Customer;
import designpatterns.enterprise.repository.CustomerRepository;
import designpatterns.enterprise.repository.CustomerService;
import designpatterns.enterprise.repository.InMemoryCustomerRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RepositoryTest {

    private final CustomerRepository repository = new InMemoryCustomerRepository();
    private final CustomerService service = new CustomerService(repository);

    @Test
    void savesAndFindsCustomers() {
        Customer cem = service.register("Cem", "cem@example.com", "Istanbul");
        service.register("Ayşe", "ayse@example.com", "Ankara");

        assertNotNull(cem.id());
        assertEquals(cem, repository.findById(cem.id()).orElseThrow());
        assertEquals(1, repository.findByCity("istanbul").size());
        assertEquals(2, repository.count());
    }

    @Test
    void businessRulesWorkThroughTheRepository() {
        Customer cem = service.register("Cem", "cem@example.com", "Istanbul");

        assertThrows(IllegalArgumentException.class, () -> service.register("Other", "CEM@example.com", "Izmir"));
        assertEquals("Izmir", service.moveTo(cem.id(), "Izmir").city());
        assertTrue(repository.deleteById(cem.id()));
        assertFalse(repository.findById(cem.id()).isPresent());
    }
}
