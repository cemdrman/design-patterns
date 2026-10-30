package designpatterns.microservice.circuitbreaker;

import designpatterns.common.Console;
import designpatterns.common.ManualClock;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

public class CircuitBreakerDemo {

    public static void main(String[] args) {
        Console.title("Circuit Breaker");

        ManualClock clock = new ManualClock(Instant.parse("2026-10-30T10:00:00Z"));
        ExchangeRateClient client = new ExchangeRateClient();
        CircuitBreaker breaker = new CircuitBreaker(3, Duration.ofSeconds(30), clock);
        BigDecimal cachedRate = new BigDecimal("41.50");

        Console.print("service up", breaker.call(client::usdToTry, () -> cachedRate) + " / " + breaker.state());

        Console.section("Service goes down");
        client.setAvailable(false);
        for (int i = 1; i <= 6; i++) {
            BigDecimal rate = breaker.call(client::usdToTry, () -> cachedRate);
            Console.print("call " + i, rate + " (fallback) / " + breaker.state());
        }
        Console.print("requests that reached the service", client.requests());

        Console.section("30 seconds later, service is back");
        clock.advance(Duration.ofSeconds(30));
        client.setAvailable(true);
        Console.print("trial call (HALF_OPEN)", breaker.call(client::usdToTry, () -> cachedRate) + " / " + breaker.state());
    }
}
