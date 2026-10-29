package designpatterns.creational;

import designpatterns.creational.prototype.Address;
import designpatterns.creational.prototype.AddressRegistry;
import designpatterns.creational.prototype.CompanyAddress;
import designpatterns.creational.prototype.PersonAddress;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PrototypeTest {

    @Test
    void copyIsDeep() {
        PersonAddress original = new PersonAddress("Street", "1", "District", "City", "Cem");
        original.phones().add("111");

        PersonAddress copy = original.copy();
        copy.phones().add("222");

        assertNotSame(original, copy);
        assertEquals(1, original.phones().size());
        assertEquals(2, copy.phones().size());
    }

    @Test
    void registryReturnsNewCopies() {
        AddressRegistry registry = new AddressRegistry();
        registry.register("office", new CompanyAddress("Street", "1", "District", "City", "Acme"));

        Address first = registry.create("office");
        Address second = registry.create("office");
        second.setNumber("2");

        assertNotSame(first, second);
        assertEquals("1", first.number());
        assertTrue(second.toString().contains("No:2"));
    }
}
