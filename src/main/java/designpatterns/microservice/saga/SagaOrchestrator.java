package designpatterns.microservice.saga;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Orchestration-based saga: runs the steps in order. If a step fails, the compensations of the
 * steps that already succeeded run in reverse order, so the system ends up consistent again
 * without a distributed (two-phase commit) transaction.
 */
public class SagaOrchestrator {

    public SagaResult execute(List<SagaStep> steps) {
        List<String> log = new ArrayList<>();
        Deque<SagaStep> completed = new ArrayDeque<>();

        for (SagaStep step : steps) {
            try {
                step.action().run();
                completed.push(step);
                log.add("✔ " + step.name());
            } catch (RuntimeException e) {
                log.add("✘ " + step.name() + ": " + e.getMessage());
                while (!completed.isEmpty()) {
                    SagaStep done = completed.pop();
                    done.compensation().run();
                    log.add("↩ compensate " + done.name());
                }
                return new SagaResult(false, step.name(), log);
            }
        }
        return new SagaResult(true, null, log);
    }
}
