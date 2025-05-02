package spring.course.observer;

import spring.course.models.Book;

public interface Observer {
    void update(Book book);
}