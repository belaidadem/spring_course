package spring.course.factory;

import spring.course.models.Book;
import spring.course.models.FictionBook;

public class FictionBookFactory implements BookFactory {
    private String defaultGenre;

    public FictionBookFactory(String defaultGenre) {
        this.defaultGenre = defaultGenre;
    }

    @Override
    public Book createBook(Long id, String title, String author) {
        return new FictionBook(id, title, author, defaultGenre);
    }

    public Book createBook(Long id, String title, String author, String genre) {
        return new FictionBook(id, title, author, genre);
    }
}