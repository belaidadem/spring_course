package spring.course.models;

public class FictionBook extends Book {
    private String genre;

    public FictionBook(Long id, String title, String author, String genre) {
        super(id, title, author);
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String getBookDetails() {
        return "Fiction Book: " + getTitle() + " by " + getAuthor() + " (Genre: " + genre + ")";
    }
}