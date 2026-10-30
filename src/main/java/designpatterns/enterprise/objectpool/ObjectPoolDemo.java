package designpatterns.enterprise.objectpool;

import designpatterns.common.Console;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ObjectPoolDemo {

    public static void main(String[] args) throws Exception {
        Console.title("Object Pool");

        ObjectPool<PdfRenderer> pool = new ObjectPool<>(3, PdfRenderer::new, PdfRenderer::reset);

        Console.section("Borrow and return with try-with-resources");
        try (var lease = pool.borrow(Duration.ofSeconds(1))) {
            Console.line(lease.get().render("invoice-1.pdf"));
        }
        Console.print("created / idle", pool.createdCount() + " / " + pool.idleCount());

        Console.section("50 concurrent requests, pool size 3");
        Set<Integer> usedRenderers = ConcurrentHashMap.newKeySet();
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 1; i <= 50; i++) {
                String document = "report-" + i + ".pdf";
                executor.submit(() -> {
                    try (var lease = pool.borrow(Duration.ofSeconds(5))) {
                        lease.get().render(document);
                        usedRenderers.add(lease.get().id());
                        Thread.sleep(5);
                    }
                    return null;
                });
            }
        }
        Console.print("renderers ever created", pool.createdCount());
        Console.print("renderer ids used", usedRenderers.stream().sorted().toList());
        Console.print("idle after all requests", pool.idleCount());
    }
}
