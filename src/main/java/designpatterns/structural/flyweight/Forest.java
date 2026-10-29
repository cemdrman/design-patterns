package designpatterns.structural.flyweight;

import java.util.ArrayList;
import java.util.List;

/** Client that creates a huge number of trees. */
public class Forest {

    private final List<Tree> trees = new ArrayList<>();

    public void plant(int x, int y, String name, String color, String texture) {
        trees.add(new Tree(x, y, TreeTypeFactory.get(name, color, texture)));
    }

    public List<Tree> trees() {
        return trees;
    }
}
