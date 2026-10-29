package designpatterns.creational.singleton;

/**
 * Lazy singleton using the <b>Initialization-on-demand holder</b> idiom (Bill Pugh singleton).
 * <p>
 * The JVM loads {@code Holder} only when {@link #getInstance()} is called for the first time
 * and class initialization is thread safe by the language specification, so no locking is needed.
 */
public final class AppSettings {

    private final String environment;

    private AppSettings() {
        this.environment = System.getProperty("app.env", "development");
    }

    private static final class Holder {
        private static final AppSettings INSTANCE = new AppSettings();
    }

    public static AppSettings getInstance() {
        return Holder.INSTANCE;
    }

    public String environment() {
        return environment;
    }
}
