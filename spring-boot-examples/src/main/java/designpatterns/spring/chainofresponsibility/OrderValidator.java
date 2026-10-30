package designpatterns.spring.chainofresponsibility;

import java.util.Optional;

/** Handler. Returns an error message, or empty to pass the request to the next validator. */
public interface OrderValidator {

    Optional<String> validate(OrderRequest request);
}
