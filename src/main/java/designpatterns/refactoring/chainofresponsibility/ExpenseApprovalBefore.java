package designpatterns.refactoring.chainofresponsibility;

import designpatterns.behavioral.chainofresponsibility.ExpenseRequest;

import java.math.BigDecimal;

/**
 * BEFORE Chain of Responsibility – an if/else ladder.
 * <p>The approval hierarchy is hard-coded; adding a "CFO" level or changing the order means
 * editing this method, and the rules can't be reconfigured per department.
 *
 * @see designpatterns.behavioral.chainofresponsibility.Approver the refactored version
 */
public class ExpenseApprovalBefore {

    public String approve(ExpenseRequest request) {
        BigDecimal amount = request.amount();
        if (amount.compareTo(new BigDecimal("1000")) <= 0) {
            return "Team lead approved " + amount + " TL for " + request.reason();
        } else if (amount.compareTo(new BigDecimal("10000")) <= 0) {
            return "Manager approved " + amount + " TL for " + request.reason();
        } else if (amount.compareTo(new BigDecimal("100000")) <= 0) {
            return "Director approved " + amount + " TL for " + request.reason();
        } else {
            return "Nobody can approve " + amount + " TL, request rejected";
        }
    }
}
