package designpatterns.behavioral.iterator;

import designpatterns.common.Console;

import java.util.Iterator;

public class IteratorDemo {

    public static void main(String[] args) {
        Console.title("Iterator");

        BookShelf shelf = new BookShelf();
        shelf.add(new Book("Design Patterns", "Gang of Four", 1994));
        shelf.add(new Book("Refactoring", "Martin Fowler", 1999));
        shelf.add(new Book("Clean Code", "Robert C. Martin", 2008));
        shelf.add(new Book("Effective Java", "Joshua Bloch", 2018));

        Console.section("for-each (Iterable)");
        for (Book book : shelf) {
            Console.print(String.valueOf(book.year()), book.title());
        }

        Console.section("Reverse iterator");
        print(shelf.reverseIterator());

        Console.section("Filtered iterator (published after 2000)");
        print(shelf.filteredIterator(book -> book.year() > 2000));
    }

    private static void print(Iterator<Book> iterator) {
        while (iterator.hasNext()) {
            Book book = iterator.next();
            Console.print(String.valueOf(book.year()), book.title());
        }
    }
}
