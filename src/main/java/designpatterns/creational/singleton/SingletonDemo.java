package designpatterns.creational.singleton;

import designpatterns.common.Console;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

public class SingletonDemo {

    public static void main(String[] args) throws InterruptedException {
        Console.title("Singleton");

        Console.section("Double-checked locking");
        ConnectionPool first = ConnectionPool.getInstance();
        ConnectionPool second = ConnectionPool.getInstance();
        Console.print("first == second", first == second);
        String connection = first.acquire();
        Console.print("acquired", connection);
        Console.print("available (seen from second)", second.available());
        second.release(connection);

        Console.section("Same instance from 100 threads");
        Set<Integer> identities = ConcurrentHashMap.newKeySet();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 100; i++) {
                executor.submit(() -> identities.add(System.identityHashCode(ConnectionPool.getInstance())));
            }
        }
        Console.print("distinct instances", identities.size());

        Console.section("Holder idiom");
        Console.print("environment", AppSettings.getInstance().environment());
        Console.print("same instance", AppSettings.getInstance() == AppSettings.getInstance());

        Console.section("Enum singleton");
        Console.print("id", IdGenerator.INSTANCE.nextId());
        Console.print("id", IdGenerator.INSTANCE.nextId());
    }
}
