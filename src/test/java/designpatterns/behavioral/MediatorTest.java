package designpatterns.behavioral;

import designpatterns.behavioral.mediator.ChatRoom;
import designpatterns.behavioral.mediator.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MediatorTest {

    @Test
    void mediatorRoutesMessages() {
        ChatRoom room = new ChatRoom();
        User a = new User("A", room);
        User b = new User("B", room);
        User c = new User("C", room);

        a.send("hi");
        b.send("spam!");
        c.sendPrivate("A", "secret");

        assertEquals(List.of("C: (private) secret"), a.inbox());
        assertEquals(List.of("A: hi"), b.inbox());
        assertEquals(List.of("A: hi"), c.inbox());
    }
}
