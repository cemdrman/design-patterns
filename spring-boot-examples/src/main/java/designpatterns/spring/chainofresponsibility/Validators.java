package designpatterns.spring.chainofresponsibility;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

/** Concrete handlers. {@code @Order} defines their position in the chain. */
final class Validators {

    private Validators() {
    }

    @Component
    @Order(1)
    static class CustomerValidator implements OrderValidator {
        @Override
        public Optional<String> validate(OrderRequest request) {
            return request.customer() == null || request.customer().isBlank()
                    ? Optional.of("customer is required") : Optional.empty();
        }
    }

    @Component
    @Order(2)
    static class QuantityValidator implements OrderValidator {
        @Override
        public Optional<String> validate(OrderRequest request) {
            return request.quantity() < 1 || request.quantity() > 10
                    ? Optional.of("quantity must be between 1 and 10") : Optional.empty();
        }
    }

    @Component
    @Order(3)
    static class CouponValidator implements OrderValidator {
        @Override
        public Optional<String> validate(OrderRequest request) {
            return request.couponCode() != null && !request.couponCode().startsWith("DP-")
                    ? Optional.of("invalid coupon " + request.couponCode()) : Optional.empty();
        }
    }
}
