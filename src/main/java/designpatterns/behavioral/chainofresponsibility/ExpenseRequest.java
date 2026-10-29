package designpatterns.behavioral.chainofresponsibility;

import java.math.BigDecimal;

/** The request that travels along the chain. */
public record ExpenseRequest(String employee, String reason, BigDecimal amount) {
}
