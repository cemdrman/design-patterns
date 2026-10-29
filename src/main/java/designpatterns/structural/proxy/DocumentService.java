package designpatterns.structural.proxy;

/** Subject interface for the protection proxy example. */
public interface DocumentService {

    String read(String documentId);

    void delete(String documentId);
}
