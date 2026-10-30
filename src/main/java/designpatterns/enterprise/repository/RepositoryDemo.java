package designpatterns.enterprise.repository;

import designpatterns.common.Console;

public class RepositoryDemo {

    public static void main(String[] args) {
        Console.title("Repository");

        CustomerRepository repository = new InMemoryCustomerRepository();
        CustomerService service = new CustomerService(repository);

        Customer cem = service.register("Cem", "cem@example.com", "Istanbul");
        service.register("Ayşe", "ayse@example.com", "Ankara");
        service.register("Mehmet", "mehmet@example.com", "Istanbul");

        Console.print("saved", cem);
        Console.print("count", repository.count());
        Console.print("customers in Istanbul", repository.findByCity("Istanbul").stream().map(Customer::name).toList());

        Console.section("Business rules use the repository");
        try {
            service.register("Cem 2", "CEM@example.com", "Izmir");
        } catch (IllegalArgumentException e) {
            Console.print("duplicate rejected", e.getMessage());
        }
        service.moveTo(cem.id(), "Izmir");
        Console.print("after moving", repository.findById(cem.id()).orElseThrow());
        Console.print("customers in Istanbul", repository.findByCity("Istanbul").stream().map(Customer::name).toList());
    }
}
