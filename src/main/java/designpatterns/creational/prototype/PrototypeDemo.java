package designpatterns.creational.prototype;

import designpatterns.common.Console;

public class PrototypeDemo {

    public static void main(String[] args) {
        Console.title("Prototype");

        Console.section("Copying an object");
        PersonAddress original = new PersonAddress("Atatürk St.", "10", "Ataşehir", "İstanbul", "Cem Dırman");
        original.phones().add("+90 555 000 00 00");

        PersonAddress neighbour = original.copy();
        neighbour.setNumber("12");
        neighbour.setFullName("Ayşe Yılmaz");
        neighbour.phones().add("+90 555 111 11 11");

        Console.print("original", original);
        Console.print("copy", neighbour);
        Console.print("same object?", original == neighbour);
        Console.print("phones shared? (deep copy)", original.phones() == neighbour.phones());

        Console.section("Prototype registry");
        AddressRegistry registry = new AddressRegistry();
        registry.register("istanbul-office", new CompanyAddress("Büyükdere Ave.", "100", "Şişli", "İstanbul", "Acme Inc."));

        Address branch1 = registry.create("istanbul-office");
        Address branch2 = registry.create("istanbul-office");
        branch2.setNumber("102");
        Console.print("branch 1", branch1);
        Console.print("branch 2", branch2);
    }
}
