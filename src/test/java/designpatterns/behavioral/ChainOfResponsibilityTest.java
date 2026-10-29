package designpatterns.behavioral;

import designpatterns.behavioral.chainofresponsibility.Approver;
import designpatterns.behavioral.chainofresponsibility.Director;
import designpatterns.behavioral.chainofresponsibility.ExpenseRequest;
import designpatterns.behavioral.chainofresponsibility.Manager;
import designpatterns.behavioral.chainofresponsibility.TeamLead;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ChainOfResponsibilityTest {

    private String approve(String amount) {
        Approver chain = new TeamLead();
        chain.setNext(new Manager()).setNext(new Director());
        return chain.approve(new ExpenseRequest("cem", "test", new BigDecimal(amount)));
    }

    @Test
    void requestStopsAtTheFirstHandlerThatCanApprove() {
        assertTrue(approve("1000").startsWith("Team lead"));
        assertTrue(approve("1000.01").startsWith("Manager"));
        assertTrue(approve("100000").startsWith("Director"));
        assertTrue(approve("100001").contains("rejected"));
    }
}
