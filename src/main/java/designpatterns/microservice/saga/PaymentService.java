package designpatterns.microservice.saga;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/** Payment microservice (simulated): customer wallets. */
public class PaymentService {

    private final Map<String, BigDecimal> balances = new HashMap<>();

    public PaymentService() {
        balances.put("cem", new BigDecimal("100000"));
        balances.put("ayse", new BigDecimal("500"));
    }

    public void charge(String customer, BigDecimal amount) {
        BigDecimal balance = balanceOf(customer);
        if (balance.compareTo(amount) < 0) {
            throw new IllegalStateException("insufficient funds");
        }
        balances.put(customer, balance.subtract(amount));
    }

    public void refund(String customer, BigDecimal amount) {
        balances.merge(customer, amount, BigDecimal::add);
    }

    public BigDecimal balanceOf(String customer) {
        return balances.getOrDefault(customer, BigDecimal.ZERO);
    }
}
