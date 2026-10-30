package designpatterns.spring.decorator;

import org.springframework.stereotype.Component;

/** The real component. */
@Component("emailNotifier")
class EmailNotifier implements Notifier {

    @Override
    public String send(String to, String message) {
        return "e-mail to " + to + ": " + message;
    }
}
