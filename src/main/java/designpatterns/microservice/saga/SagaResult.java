package designpatterns.microservice.saga;

import java.util.List;

public record SagaResult(boolean completed, String failedStep, List<String> log) {
}
