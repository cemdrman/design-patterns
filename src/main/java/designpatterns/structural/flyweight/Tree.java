package designpatterns.structural.flyweight;

/** Context: stores the <b>extrinsic</b> (unique) state and a reference to a shared flyweight. */
public record Tree(int x, int y, TreeType type) {

    public String draw() {
        return type.draw(x, y);
    }
}
