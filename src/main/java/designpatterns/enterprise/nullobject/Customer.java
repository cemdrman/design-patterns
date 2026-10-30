package designpatterns.enterprise.nullobject;

/** Common interface for real customers and the "no customer" case. */
public interface Customer {

    String name();

    /** Discount rate in percent. */
    int discountPercent();

    boolean isGuest();
}
