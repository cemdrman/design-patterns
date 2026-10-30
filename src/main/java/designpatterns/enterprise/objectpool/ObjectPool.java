package designpatterns.enterprise.objectpool;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Keeps a limited number of expensive objects and lends them out.
 * Objects are created lazily, never more than {@code maxSize}; when all of them are in use
 * the caller waits until one is returned (or the timeout expires).
 */
public class ObjectPool<T> {

    private final BlockingQueue<T> idle = new LinkedBlockingQueue<>();
    private final AtomicInteger created = new AtomicInteger();
    private final int maxSize;
    private final Supplier<T> factory;
    private final Consumer<T> reset;

    public ObjectPool(int maxSize, Supplier<T> factory, Consumer<T> reset) {
        if (maxSize < 1) {
            throw new IllegalArgumentException("maxSize must be positive");
        }
        this.maxSize = maxSize;
        this.factory = Objects.requireNonNull(factory);
        this.reset = Objects.requireNonNull(reset);
    }

    /** Borrows an object. Use it with try-with-resources so it always goes back to the pool. */
    public Lease borrow(Duration timeout) throws InterruptedException, TimeoutException {
        T object = idle.poll();
        if (object == null) {
            object = tryCreate();
        }
        if (object == null) {
            object = idle.poll(timeout.toMillis(), TimeUnit.MILLISECONDS);
            if (object == null) {
                throw new TimeoutException("No object available within " + timeout);
            }
        }
        return new Lease(object);
    }

    private T tryCreate() {
        while (true) {
            int current = created.get();
            if (current >= maxSize) {
                return null;
            }
            if (created.compareAndSet(current, current + 1)) {
                return factory.get();
            }
        }
    }

    private void release(T object) {
        reset.accept(object);
        idle.offer(object);
    }

    public int createdCount() {
        return created.get();
    }

    public int idleCount() {
        return idle.size();
    }

    /** A borrowed object. Closing the lease returns the object to the pool. */
    public final class Lease implements AutoCloseable {

        private final T object;
        private boolean released;

        private Lease(T object) {
            this.object = object;
        }

        public T get() {
            if (released) {
                throw new IllegalStateException("Lease already released");
            }
            return object;
        }

        @Override
        public void close() {
            if (!released) {
                released = true;
                release(object);
            }
        }
    }
}
