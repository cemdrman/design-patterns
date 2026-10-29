package designpatterns.creational.prototype;

/** Concrete prototype for a company. */
public class CompanyAddress extends Address {

    private String companyName;

    public CompanyAddress(String street, String number, String district, String city, String companyName) {
        super(street, number, district, city);
        this.companyName = companyName;
    }

    private CompanyAddress(CompanyAddress source) {
        super(source);
        this.companyName = source.companyName;
    }

    @Override
    public CompanyAddress copy() {
        return new CompanyAddress(this);
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    protected String owner() {
        return companyName;
    }
}
