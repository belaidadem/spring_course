package spring.course.observer;

import spring.course.models.Book;
import java.util.logging.Logger;

public class LibrarianObserver implements Observer {
    private static final Logger LOGGER = Logger.getLogger(LibrarianObserver.class.getName());

    @Override
    public void update(Book book) {
        logBookActivity(book);
    }

    public void logBookActivity(Book book) {
        if (book.isBorrowed()) {
            LOGGER.info("BOOK BORROWED: " + book.getTitle() + " by " + book.getBorrowedBy().getName());
        } else {
            LOGGER.info("BOOK RETURNED: " + book.getTitle());
        }
    }
}