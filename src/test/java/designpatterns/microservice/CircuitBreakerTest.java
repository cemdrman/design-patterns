package designpatterns.microservice;

import designpatterns.common.ManualClock;
import designpatterns.microservice.circuitbreaker.CallNotPermittedException;
import designpatterns.microservice.circuitbreaker.CircuitBreaker;
import designpatterns.microservice.circuitbreaker.CircuitBreaker.State;
import designpatterns.microservice.circuitbreaker.ExchangeRateClient;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CircuitBreakerTest {

    private final ManualClock clock = new ManualClock(Instant.parse("2026-01-01T00:00:00Z"));
    private final ExchangeRateClient client = new ExchangeRateClient();
    private final CircuitBreaker breaker = new CircuitBreaker(2, Duration.ofSeconds(10), clock);

    private void failTwice() {
        client.setAvailable(false);
        assertThrows(IllegalStateException.class, () -> breaker.call(client::usdToTry));
        assertThrows(IllegalStateException.class, () -> breaker.call(client::usdToTry));
    }

    @Test
    void opensAfterThresholdAndFailsFast() {
        failTwice();
        assertEquals(State.OPEN, breaker.state());

        assertThrows(CallNotPermittedException.class, () -> breaker.call(client::usdToTry));
        assertEquals(BigDecimal.ONE, breaker.call(client::usdToTry, () -> BigDecimal.ONE));
        assertEquals(2, client.requests());
    }

    @Test
    void halfOpenTrialClosesTheCircuitOnSuccess() {
        failTwice();
        clock.advance(Duration.ofSeconds(10));
        client.setAvailable(true);

        assertEquals(new BigDecimal("41.85"), breaker.call(client::usdToTry));
        assertEquals(State.CLOSED, breaker.state());
    }

    @Test
    void halfOpenTrialReopensTheCircuitOnFailure() {
        failTwice();
        clock.advance(Duration.ofSeconds(10));

        assertThrows(IllegalStateException.class, () -> breaker.call(client::usdToTry));
        assertEquals(State.OPEN, breaker.state());
    }
}
