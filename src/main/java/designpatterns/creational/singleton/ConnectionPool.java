package designpatterns.creational.singleton;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Lazy, thread safe singleton built with <b>double-checked locking</b>.
 * <p>
 * A connection pool is a classic example: opening database connections is expensive,
 * so the whole application must share exactly one pool.
 */
public final class ConnectionPool {

    // volatile is required: without it another thread may see a half constructed object.
    private static volatile ConnectionPool instance;

    private final Deque<String> connections = new ArrayDeque<>();

    // A private constructor prevents "new ConnectionPool()" from outside.
    private ConnectionPool() {
        for (int i = 1; i <= 3; i++) {
            connections.push("connection-" + i);
        }
    }

    public static ConnectionPool getInstance() {
        ConnectionPool result = instance; // read the volatile field only once
        if (result == null) {
            synchronized (ConnectionPool.class) {
                result = instance;
                if (result == null) {
                    instance = result = new ConnectionPool();
                }
            }
        }
        return result;
    }

    public synchronized String acquire() {
        if (connections.isEmpty()) {
            throw new IllegalStateException("No free connection left in the pool");
        }
        return connections.pop();
    }

    public synchronized void release(String connection) {
        connections.push(connection);
    }

    public synchronized int available() {
        return connections.size();
    }
}
