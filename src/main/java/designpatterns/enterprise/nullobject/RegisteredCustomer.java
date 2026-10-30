package designpatterns.enterprise.nullobject;

public record RegisteredCustomer(String name, int discountPercent) implements Customer {

    @Override
    public boolean isGuest() {
        return false;
    }
}
