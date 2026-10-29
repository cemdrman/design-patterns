package designpatterns.structural.proxy;

/**
 * Virtual proxy: postpones creating the expensive real object until it is really needed.
 */
public class LazyImageProxy implements Image {

    private final String fileName;
    private HighResolutionImage realImage;

    public LazyImageProxy(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String fileName() {
        return fileName; // answered without loading the image
    }

    @Override
    public String display() {
        if (realImage == null) {
            realImage = new HighResolutionImage(fileName);
        }
        return realImage.display();
    }
}
