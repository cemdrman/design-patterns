package designpatterns.microservice.saga;

/**
 * One local transaction of the saga and the compensating action that semantically undoes it.
 * In a real system each step is a call to another microservice with its own database.
 */
public record SagaStep(String name, Runnable action, Runnable compensation) {
}
