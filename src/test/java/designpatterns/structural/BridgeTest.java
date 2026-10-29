package designpatterns.structural;

import designpatterns.structural.bridge.AlertNotification;
import designpatterns.structural.bridge.EmailSender;
import designpatterns.structural.bridge.ReminderNotification;
import designpatterns.structural.bridge.SmsSender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BridgeTest {

    @Test
    void abstractionAndImplementationVaryIndependently() {
        assertEquals("EMAIL to cem@mail.com: [ALERT] DISK FULL",
                new AlertNotification(new EmailSender()).notify("cem", "disk full"));
        assertTrue(new ReminderNotification(new SmsSender())
                .notify("cem", "a very long reminder text that will be cut").endsWith("..."));
    }
}
