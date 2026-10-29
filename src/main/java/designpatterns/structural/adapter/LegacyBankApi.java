package designpatterns.structural.adapter;

/**
 * Adaptee: a third party / legacy API that we cannot change.
 * It works with cents as {@code long} and returns status codes instead of objects.
 */
public class LegacyBankApi {

    /** @return "00" on success, "51" when the amount is too high. */
    public String transfer(String accountNo, long amountInCents) {
        return amountInCents <= 1_000_000 ? "00" : "51";
    }

    public String lastReference() {
        return "BANK-0042";
    }
}
