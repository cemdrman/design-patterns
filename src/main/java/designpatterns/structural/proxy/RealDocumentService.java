package designpatterns.structural.proxy;

import java.util.HashMap;
import java.util.Map;

/** Real subject. */
public class RealDocumentService implements DocumentService {

    private final Map<String, String> documents = new HashMap<>(Map.of("salary-report", "Confidential numbers"));

    @Override
    public String read(String documentId) {
        return documents.getOrDefault(documentId, "<not found>");
    }

    @Override
    public void delete(String documentId) {
        documents.remove(documentId);
    }
}
