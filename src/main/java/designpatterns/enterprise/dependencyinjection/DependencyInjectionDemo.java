package designpatterns.enterprise.dependencyinjection;

import designpatterns.common.Console;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class DependencyInjectionDemo {

    public static void main(String[] args) {
        Console.title("Dependency Injection");

        Console.section("Manual wiring in the composition root");
        Clock morning = Clock.fixed(Instant.parse("2026-10-30T07:30:00Z"), ZoneId.of("UTC"));
        WelcomeService service = new WelcomeService(new EmailNotifier(), morning);
        service.welcome("cem@example.com", "Cem");

        Console.section("Same service, different dependencies");
        Clock evening = Clock.fixed(Instant.parse("2026-10-30T20:00:00Z"), ZoneId.of("UTC"));
        RecordingNotifier recorder = new RecordingNotifier();
        new WelcomeService(recorder, evening).welcome("ayse@example.com", "Ayşe");
        Console.print("recorded (nothing sent)", recorder.sent());

        Console.section("Wiring with a tiny container");
        Container container = new Container()
                .register(Clock.class, c -> morning)
                .register(Notifier.class, c -> new EmailNotifier())
                .register(WelcomeService.class, c -> new WelcomeService(c.get(Notifier.class), c.get(Clock.class)));
        container.get(WelcomeService.class).welcome("mehmet@example.com", "Mehmet");
        Console.print("same instance every time", container.get(WelcomeService.class) == container.get(WelcomeService.class));
    }
}
