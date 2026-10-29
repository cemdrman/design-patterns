package designpatterns.behavioral;

import designpatterns.behavioral.iterator.Book;
import designpatterns.behavioral.iterator.BookShelf;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IteratorTest {

    private final BookShelf shelf = new BookShelf();

    IteratorTest() {
        for (int year = 2001; year <= 2005; year++) {
            shelf.add(new Book("B" + year, "author", year));
        }
    }

    private static List<Integer> years(Iterator<Book> iterator) {
        List<Integer> years = new ArrayList<>();
        iterator.forEachRemaining(book -> years.add(book.year()));
        return years;
    }

    @Test
    void traversesInDifferentWays() {
        assertEquals(List.of(2001, 2002, 2003, 2004, 2005), years(shelf.iterator()));
        assertEquals(List.of(2005, 2004, 2003, 2002, 2001), years(shelf.reverseIterator()));
        assertEquals(List.of(2002, 2004), years(shelf.filteredIterator(book -> book.year() % 2 == 0)));
    }

    @Test
    void throwsWhenExhausted() {
        Iterator<Book> iterator = shelf.filteredIterator(book -> false);
        assertThrows(NoSuchElementException.class, iterator::next);
    }
}
