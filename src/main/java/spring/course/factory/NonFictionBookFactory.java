package spring.course.factory;

import spring.course.models.Book;
import spring.course.models.NonFictionBook;

public class NonFictionBookFactory implements BookFactory {
    private String defaultSubject;

    public NonFictionBookFactory(String defaultSubject) {
        this.defaultSubject = defaultSubject;
    }

    @Override
    public Book createBook(Long id, String title, String author) {
        return new NonFictionBook(id, title, author, defaultSubject);
    }

    public Book createBook(Long id, String title, String author, String subject) {
        return new NonFictionBook(id, title, author, subject);
    }
}