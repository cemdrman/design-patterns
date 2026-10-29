package designpatterns.creational;

import designpatterns.creational.singleton.AppSettings;
import designpatterns.creational.singleton.ConnectionPool;
import designpatterns.creational.singleton.IdGenerator;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SingletonTest {

    @Test
    void returnsTheSameInstance() {
        assertSame(ConnectionPool.getInstance(), ConnectionPool.getInstance());
        assertSame(AppSettings.getInstance(), AppSettings.getInstance());
    }

    @Test
    void isThreadSafe() {
        Set<ConnectionPool> instances = ConcurrentHashMap.newKeySet();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 1_000; i++) {
                executor.submit(() -> instances.add(ConnectionPool.getInstance()));
            }
        }
        assertEquals(1, instances.size());
    }

    @Test
    void enumSingletonKeepsState() {
        long first = IdGenerator.INSTANCE.nextId();
        long second = IdGenerator.INSTANCE.nextId();
        assertTrue(second > first);
    }
}
