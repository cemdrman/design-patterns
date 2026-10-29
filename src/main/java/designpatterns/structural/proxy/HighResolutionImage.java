package designpatterns.structural.proxy;

/** Real subject: loading it is expensive. */
public class HighResolutionImage implements Image {

    private static int loadCount;

    private final String fileName;

    public HighResolutionImage(String fileName) {
        this.fileName = fileName;
        loadFromDisk();
    }

    private void loadFromDisk() {
        loadCount++; // imagine reading 20 MB from disk here
    }

    public static int loadCount() {
        return loadCount;
    }

    @Override
    public String fileName() {
        return fileName;
    }

    @Override
    public String display() {
        return "Displaying " + fileName;
    }
}
