package spring.course.observer;

import spring.course.models.Book;
import java.util.logging.Logger;

public class UserNotificationObserver implements Observer {
    private static final Logger LOGGER = Logger.getLogger(UserNotificationObserver.class.getName());

    @Override
    public void update(Book book) {
        sendNotification(book);
    }

    public void sendNotification(Book book) {
        if (!book.isBorrowed()) {
            LOGGER.info("NOTIFICATION: The book '" + book.getTitle() + "' is now available!");
        }
    }
}