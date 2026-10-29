package designpatterns.behavioral.chainofresponsibility;

import designpatterns.common.Console;

import java.math.BigDecimal;

public class ChainOfResponsibilityDemo {

    public static void main(String[] args) {
        Console.title("Chain of Responsibility");

        Approver chain = new TeamLead();
        chain.setNext(new Manager()).setNext(new Director());

        for (String amount : new String[]{"750", "8500", "45000", "250000"}) {
            ExpenseRequest request = new ExpenseRequest("cem", "conference trip", new BigDecimal(amount));
            Console.print(amount + " TL", chain.approve(request));
        }
    }
}
