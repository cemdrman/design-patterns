package designpatterns.creational.singleton;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Singleton implemented with an {@code enum}.
 * <p>
 * Recommended by Joshua Bloch (Effective Java): it is thread safe, survives serialization
 * and cannot be broken with reflection.
 */
public enum IdGenerator {
    INSTANCE;

    private final AtomicLong counter = new AtomicLong();

    public long nextId() {
        return counter.incrementAndGet();
    }
}
