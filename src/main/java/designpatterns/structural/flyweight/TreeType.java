package designpatterns.structural.flyweight;

/**
 * Flyweight: holds the <b>intrinsic</b> (shared, immutable) state.
 * Imagine a texture of several megabytes here instead of a short string.
 */
public record TreeType(String name, String color, String texture) {

    public String draw(int x, int y) {
        return "%s tree (%s) at (%d, %d)".formatted(name, color, x, y);
    }
}
