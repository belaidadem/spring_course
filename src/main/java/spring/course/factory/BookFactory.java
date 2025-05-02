package spring.course.factory;

import spring.course.models.Book;

public interface BookFactory {
    Book createBook(Long id, String title, String author);
}