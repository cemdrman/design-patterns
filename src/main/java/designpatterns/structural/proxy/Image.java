package designpatterns.structural.proxy;

/** Subject interface shared by the real object and the proxy. */
public interface Image {

    String fileName();

    String display();
}
