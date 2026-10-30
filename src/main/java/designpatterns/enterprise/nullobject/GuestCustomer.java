package designpatterns.enterprise.nullobject;

/**
 * Null Object: a do-nothing / neutral implementation used instead of {@code null}.
 * It has no state, so one shared instance is enough.
 */
public final class GuestCustomer implements Customer {

    public static final GuestCustomer INSTANCE = new GuestCustomer();

    private GuestCustomer() {
    }

    @Override
    public String name() {
        return "Guest";
    }

    @Override
    public int discountPercent() {
        return 0;
    }

    @Override
    public boolean isGuest() {
        return true;
    }
}
