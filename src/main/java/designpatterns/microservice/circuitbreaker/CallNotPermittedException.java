package designpatterns.microservice.circuitbreaker;

import java.io.Serial;

public class CallNotPermittedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CallNotPermittedException(String message) {
        super(message);
    }
}
