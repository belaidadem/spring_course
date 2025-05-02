package spring.course.models;

public class NonFictionBook extends Book {
    private String subject;

    public NonFictionBook(Long id, String title, String author, String subject) {
        super(id, title, author);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String getBookDetails() {
        return "Non-Fiction Book: " + getTitle() + " by " + getAuthor() + " (Subject: " + subject + ")";
    }
}