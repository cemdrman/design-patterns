package designpatterns.structural.proxy;

import java.util.ArrayList;
import java.util.List;

/**
 * Protection proxy: checks permissions and logs every access before delegating
 * to the real service. The client does not know it is talking to a proxy.
 */
public class ProtectedDocumentProxy implements DocumentService {

    public enum Role { VIEWER, ADMIN }

    private final DocumentService target;
    private final Role role;
    private final List<String> auditLog = new ArrayList<>();

    public ProtectedDocumentProxy(DocumentService target, Role role) {
        this.target = target;
        this.role = role;
    }

    @Override
    public String read(String documentId) {
        auditLog.add(role + " read " + documentId);
        return target.read(documentId);
    }

    @Override
    public void delete(String documentId) {
        if (role != Role.ADMIN) {
            auditLog.add(role + " tried to delete " + documentId);
            throw new SecurityException(role + " is not allowed to delete documents");
        }
        auditLog.add(role + " deleted " + documentId);
        target.delete(documentId);
    }

    public List<String> auditLog() {
        return List.copyOf(auditLog);
    }
}
