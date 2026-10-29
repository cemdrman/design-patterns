package designpatterns.behavioral.visitor;

/**
 * Element: every shape accepts a visitor and calls the visit method made for its own type
 * (double dispatch). New operations can be added without touching the shapes.
 */
public sealed interface Shape permits Circle, Rectangle, Triangle {

    <R> R accept(ShapeVisitor<R> visitor);
}
