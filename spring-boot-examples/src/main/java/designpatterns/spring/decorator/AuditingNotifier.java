package designpatterns.spring.decorator;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Decorator. {@code @Primary} makes every client that asks for a {@link Notifier} receive this
 * bean; {@code @Qualifier} gives it the real implementation to wrap.
 */
@Primary
@Component
public class AuditingNotifier implements Notifier {

    private final Notifier delegate;
    private final List<String> auditLog = new CopyOnWriteArrayList<>();

    public AuditingNotifier(@Qualifier("emailNotifier") Notifier delegate) {
        this.delegate = delegate;
    }

    @Override
    public String send(String to, String message) {
        auditLog.add("notification sent to " + to);
        return delegate.send(to, message) + " [audited]";
    }

    public List<String> auditLog() {
        return List.copyOf(auditLog);
    }
}
