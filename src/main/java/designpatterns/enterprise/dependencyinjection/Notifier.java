package designpatterns.enterprise.dependencyinjection;

/** Abstraction the service depends on. The service never knows which implementation it gets. */
public interface Notifier {

    void send(String to, String message);
}
