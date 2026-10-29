package designpatterns.creational.prototype;

/** Concrete prototype for a person. */
public class PersonAddress extends Address {

    private String fullName;

    public PersonAddress(String street, String number, String district, String city, String fullName) {
        super(street, number, district, city);
        this.fullName = fullName;
    }

    private PersonAddress(PersonAddress source) {
        super(source);
        this.fullName = source.fullName;
    }

    @Override
    public PersonAddress copy() {
        return new PersonAddress(this);
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    protected String owner() {
        return fullName;
    }
}
