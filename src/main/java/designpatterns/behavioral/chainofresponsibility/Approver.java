package designpatterns.behavioral.chainofresponsibility;

import java.math.BigDecimal;

/**
 * Handler: either processes the request or passes it to the next handler.
 * The sender does not know which object will finally handle it.
 */
public abstract class Approver {

    private Approver next;

    /** Links the next handler and returns it, so the chain can be built fluently. */
    public Approver setNext(Approver next) {
        this.next = next;
        return next;
    }

    public final String approve(ExpenseRequest request) {
        if (canApprove(request.amount())) {
            return title() + " approved " + request.amount() + " TL for " + request.reason();
        }
        if (next == null) {
            return "Nobody can approve " + request.amount() + " TL, request rejected";
        }
        return next.approve(request);
    }

    protected abstract boolean canApprove(BigDecimal amount);

    protected abstract String title();
}
