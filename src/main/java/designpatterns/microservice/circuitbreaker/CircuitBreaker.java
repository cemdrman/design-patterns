package designpatterns.microservice.circuitbreaker;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

/**
 * Protects the caller from a failing remote service.
 * <ul>
 *   <li><b>CLOSED</b> – calls go through; consecutive failures are counted.</li>
 *   <li><b>OPEN</b> – after {@code failureThreshold} failures calls are rejected immediately
 *       (fail fast) for {@code openDuration}; the remote service gets time to recover.</li>
 *   <li><b>HALF_OPEN</b> – after the wait one trial call is allowed. Success closes the circuit,
 *       failure opens it again.</li>
 * </ul>
 */
public class CircuitBreaker {

    public enum State { CLOSED, OPEN, HALF_OPEN }

    private final int failureThreshold;
    private final Duration openDuration;
    private final Clock clock;

    private State state = State.CLOSED;
    private int consecutiveFailures;
    private Instant openedAt;

    public CircuitBreaker(int failureThreshold, Duration openDuration, Clock clock) {
        this.failureThreshold = failureThreshold;
        this.openDuration = openDuration;
        this.clock = clock;
    }

    public synchronized <T> T call(Supplier<T> action) {
        if (state == State.OPEN) {
            if (clock.instant().isBefore(openedAt.plus(openDuration))) {
                throw new CallNotPermittedException("Circuit is OPEN, call rejected without hitting the service");
            }
            state = State.HALF_OPEN;
        }
        try {
            T result = action.get();
            onSuccess();
            return result;
        } catch (RuntimeException e) {
            onFailure();
            throw e;
        }
    }

    /** Same as {@link #call(Supplier)} but returns a fallback value instead of throwing. */
    public <T> T call(Supplier<T> action, Supplier<T> fallback) {
        try {
            return call(action);
        } catch (RuntimeException e) {
            return fallback.get();
        }
    }

    private void onSuccess() {
        consecutiveFailures = 0;
        state = State.CLOSED;
    }

    private void onFailure() {
        consecutiveFailures++;
        if (state == State.HALF_OPEN || consecutiveFailures >= failureThreshold) {
            state = State.OPEN;
            openedAt = clock.instant();
        }
    }

    public synchronized State state() {
        return state;
    }
}
