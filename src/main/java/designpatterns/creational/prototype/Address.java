package designpatterns.creational.prototype;

import java.util.ArrayList;
import java.util.List;

/**
 * Base prototype. The copy constructor performs a <b>deep copy</b>:
 * the mutable phone list is copied instead of being shared.
 */
public abstract class Address implements Prototype<Address> {

    private String street;
    private String number;
    private String district;
    private String city;
    private final List<String> phones;

    protected Address(String street, String number, String district, String city) {
        this.street = street;
        this.number = number;
        this.district = district;
        this.city = city;
        this.phones = new ArrayList<>();
    }

    /** Copy constructor used by {@link #copy()}. */
    protected Address(Address source) {
        this.street = source.street;
        this.number = source.number;
        this.district = source.district;
        this.city = source.city;
        this.phones = new ArrayList<>(source.phones); // deep copy
    }

    public String street() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String number() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String district() {
        return district;
    }

    public String city() {
        return city;
    }

    public List<String> phones() {
        return phones;
    }

    protected abstract String owner();

    @Override
    public String toString() {
        return "%s -> %s No:%s, %s/%s, phones=%s".formatted(owner(), street, number, district, city, phones);
    }
}
