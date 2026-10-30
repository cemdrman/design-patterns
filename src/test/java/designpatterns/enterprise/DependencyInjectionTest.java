package designpatterns.enterprise;

import designpatterns.enterprise.dependencyinjection.Container;
import designpatterns.enterprise.dependencyinjection.Notifier;
import designpatterns.enterprise.dependencyinjection.RecordingNotifier;
import designpatterns.enterprise.dependencyinjection.WelcomeService;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DependencyInjectionTest {

    private static Clock at(String time) {
        return Clock.fixed(Instant.parse("2026-10-30T" + time + "Z"), ZoneId.of("UTC"));
    }

    @Test
    void injectedDependenciesMakeTheServiceTestable() {
        RecordingNotifier notifier = new RecordingNotifier();
        WelcomeService service = new WelcomeService(notifier, at("14:00:00"));

        assertEquals("Good afternoon, Cem!", service.welcome("cem@example.com", "Cem"));
        assertEquals(List.of("cem@example.com: Good afternoon, Cem!"), notifier.sent());
    }

    @Test
    void containerBuildsTheGraphOnceAndReusesIt() {
        RecordingNotifier notifier = new RecordingNotifier();
        Container container = new Container()
                .register(Clock.class, c -> at("08:00:00"))
                .register(Notifier.class, c -> notifier)
                .register(WelcomeService.class, c -> new WelcomeService(c.get(Notifier.class), c.get(Clock.class)));

        assertSame(container.get(WelcomeService.class), container.get(WelcomeService.class));
        assertEquals("Good morning, Ali!", container.get(WelcomeService.class).welcome("a@b.c", "Ali"));
        assertThrows(IllegalStateException.class, () -> container.get(String.class));
    }
}
