package designpatterns.behavioral.chainofresponsibility;

import java.math.BigDecimal;

/** Concrete handler: can approve expenses up to 10000 TL. */
public class Manager extends Approver {

    private static final BigDecimal LIMIT = new BigDecimal("10000");

    @Override
    protected boolean canApprove(BigDecimal amount) {
        return amount.compareTo(LIMIT) <= 0;
    }

    @Override
    protected String title() {
        return "Manager";
    }
}
