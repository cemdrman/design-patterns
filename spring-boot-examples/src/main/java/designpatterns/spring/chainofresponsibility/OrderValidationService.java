package designpatterns.spring.chainofresponsibility;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Builds the chain from every {@link OrderValidator} bean, sorted by {@code @Order}.
 * The first validator that finds a problem stops the chain – like a servlet {@code Filter} chain
 * or Spring Security's {@code SecurityFilterChain}.
 */
@Service
public class OrderValidationService {

    private final List<OrderValidator> chain;

    public OrderValidationService(List<OrderValidator> chain) {
        this.chain = chain;
    }

    public Optional<String> validate(OrderRequest request) {
        return chain.stream()
                .map(validator -> validator.validate(request))
                .flatMap(Optional::stream)
                .findFirst();
    }

    public List<String> chainOrder() {
        return chain.stream().map(validator -> validator.getClass().getSimpleName()).toList();
    }
}
