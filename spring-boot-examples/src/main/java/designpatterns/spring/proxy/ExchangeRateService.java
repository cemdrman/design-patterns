package designpatterns.spring.proxy;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Spring doesn't inject this class itself but a generated subclass (CGLIB proxy) that checks the
 * cache before calling {@link #rate(String)}. {@code @Transactional}, {@code @Async},
 * {@code @Retryable} and Spring Security's {@code @PreAuthorize} work the same way.
 */
@Service
public class ExchangeRateService {

    private final AtomicInteger remoteCalls = new AtomicInteger();

    @Cacheable("rates")
    public BigDecimal rate(String currency) {
        remoteCalls.incrementAndGet(); // pretend this is a slow HTTP call
        return switch (currency) {
            case "USD" -> new BigDecimal("41.85");
            case "EUR" -> new BigDecimal("48.70");
            default -> throw new IllegalArgumentException("Unknown currency " + currency);
        };
    }

    /**
     * Pitfall: {@code this.rate(...)} is a call on the real object, not on the proxy,
     * so the cache is <b>bypassed</b> (self-invocation). Same reason why {@code @Transactional}
     * on a method called from the same class does nothing.
     */
    public BigDecimal toTry(BigDecimal amount, String currency) {
        return amount.multiply(rate(currency));
    }

    public int remoteCalls() {
        return remoteCalls.get();
    }
}
