package designpatterns.enterprise.objectpool;

import java.util.concurrent.atomic.AtomicInteger;

/** Pretend this is expensive to create (loads fonts, starts a native engine...). */
public class PdfRenderer {

    private static final AtomicInteger INSTANCES = new AtomicInteger();

    private final int id;
    private int pagesRendered;

    public PdfRenderer() {
        this.id = INSTANCES.incrementAndGet();
    }

    public String render(String document) {
        pagesRendered++;
        return "renderer-" + id + " rendered " + document;
    }

    /** Clears per-use state before the renderer goes back to the pool. */
    public void reset() {
        pagesRendered = 0;
    }

    public int pagesRendered() {
        return pagesRendered;
    }

    public int id() {
        return id;
    }
}
