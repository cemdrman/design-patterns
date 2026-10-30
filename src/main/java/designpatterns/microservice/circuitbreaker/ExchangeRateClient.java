package designpatterns.microservice.circuitbreaker;

import java.math.BigDecimal;

/** Simulates a remote HTTP service that can go down. */
public class ExchangeRateClient {

    private boolean available = true;
    private int requests;

    public BigDecimal usdToTry() {
        requests++;
        if (!available) {
            throw new IllegalStateException("503 Service Unavailable");
        }
        return new BigDecimal("41.85");
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    /** How many requests actually reached the remote service. */
    public int requests() {
        return requests;
    }
}
