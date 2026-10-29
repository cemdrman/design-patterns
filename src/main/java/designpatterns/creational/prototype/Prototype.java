package designpatterns.creational.prototype;

/**
 * Prototype interface. Using our own {@code copy()} method is preferred over
 * {@link Object#clone()} and {@link java.lang.Cloneable}, which are considered broken
 * (see Effective Java, Item 13).
 */
public interface Prototype<T extends Prototype<T>> {

    T copy();
}
