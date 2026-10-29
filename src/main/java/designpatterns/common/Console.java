package designpatterns.common;

/**
 * Tiny helper that keeps the output of every demo consistent and readable.
 */
public final class Console {

    private Console() {
    }

    /** Prints the title of a demo. */
    public static void title(String text) {
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println(" " + text);
        System.out.println("=".repeat(60));
    }

    /** Prints a sub section inside a demo. */
    public static void section(String text) {
        System.out.println();
        System.out.println("--- " + text + " ---");
    }

    /** Prints an aligned "label : value" line. */
    public static void print(String label, Object value) {
        System.out.printf("%-38s: %s%n", label, value);
    }

    /** Prints a plain line. */
    public static void line(Object value) {
        System.out.println(value);
    }
}
