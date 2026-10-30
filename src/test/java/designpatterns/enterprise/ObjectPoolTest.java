package designpatterns.enterprise;

import designpatterns.enterprise.objectpool.ObjectPool;
import designpatterns.enterprise.objectpool.PdfRenderer;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ObjectPoolTest {

    @Test
    void returnedObjectsAreResetAndReused() throws Exception {
        ObjectPool<PdfRenderer> pool = new ObjectPool<>(2, PdfRenderer::new, PdfRenderer::reset);

        PdfRenderer first;
        try (var lease = pool.borrow(Duration.ofMillis(100))) {
            first = lease.get();
            first.render("a.pdf");
        }
        assertEquals(0, first.pagesRendered());

        try (var lease = pool.borrow(Duration.ofMillis(100))) {
            assertSame(first, lease.get());
        }
        assertEquals(1, pool.createdCount());
    }

    @Test
    void neverCreatesMoreThanMaxSize() throws Exception {
        ObjectPool<PdfRenderer> pool = new ObjectPool<>(3, PdfRenderer::new, PdfRenderer::reset);
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 100; i++) {
                executor.submit(() -> {
                    try (var lease = pool.borrow(Duration.ofSeconds(5))) {
                        lease.get().render("x.pdf");
                        Thread.sleep(1);
                    }
                    return null;
                });
            }
        }
        assertEquals(3, pool.createdCount());
        assertEquals(3, pool.idleCount());
    }

    @Test
    void borrowTimesOutWhenThePoolIsExhausted() throws Exception {
        ObjectPool<PdfRenderer> pool = new ObjectPool<>(1, PdfRenderer::new, PdfRenderer::reset);
        try (var ignored = pool.borrow(Duration.ofMillis(10))) {
            assertThrows(TimeoutException.class, () -> pool.borrow(Duration.ofMillis(10)));
        }
    }
}
