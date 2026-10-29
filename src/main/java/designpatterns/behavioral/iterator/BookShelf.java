package designpatterns.behavioral.iterator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * Aggregate: stores its elements in a plain array, but never exposes that array.
 * Clients traverse it only through iterators, so the internal structure can change freely.
 */
public class BookShelf implements Iterable<Book> {

    private Book[] books = new Book[2];
    private int size;

    public void add(Book book) {
        if (size == books.length) {
            books = Arrays.copyOf(books, size * 2);
        }
        books[size++] = book;
    }

    /** Default iterator, makes the shelf usable in a for-each loop. */
    @Override
    public Iterator<Book> iterator() {
        return new ShelfIterator(book -> true, false);
    }

    public Iterator<Book> reverseIterator() {
        return new ShelfIterator(book -> true, true);
    }

    public Iterator<Book> filteredIterator(Predicate<Book> filter) {
        return new ShelfIterator(filter, false);
    }

    /** Concrete iterator: keeps the traversal state (the cursor) outside of the collection. */
    private class ShelfIterator implements Iterator<Book> {

        private final Predicate<Book> filter;
        private final boolean reverse;
        private int cursor;

        ShelfIterator(Predicate<Book> filter, boolean reverse) {
            this.filter = filter;
            this.reverse = reverse;
            this.cursor = reverse ? size - 1 : 0;
            skipNonMatching();
        }

        @Override
        public boolean hasNext() {
            return cursor >= 0 && cursor < size;
        }

        @Override
        public Book next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Book book = books[cursor];
            move();
            skipNonMatching();
            return book;
        }

        private void move() {
            cursor += reverse ? -1 : 1;
        }

        private void skipNonMatching() {
            while (hasNext() && !filter.test(books[cursor])) {
                move();
            }
        }
    }
}
