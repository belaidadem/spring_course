package spring.course.models;

import java.util.Date;

public abstract class Book {
    private Long id;
    private String title;
    private String author;
    private boolean borrowed;
    private Date borrowedDate;
    private User borrowedBy;

    // Constructor, getters, setters, and methods as provided before
    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.borrowed = false;
        this.borrowedDate = null;
        this.borrowedBy = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public Date getBorrowedDate() {
        return borrowedDate;
    }

    public User getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    public void setBorrowedDate(Date borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public void setBorrowedBy(User borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    public abstract String getBookDetails();

    public String getBorrowingDetails() {
        if (!borrowed) {
            return "Book is available";
        }
        return "Borrowed by " + borrowedBy.getName() + " on " + borrowedDate.toString();
    }

    public boolean borrow(User user) {
        if (!borrowed && user.canBorrow()) {
            this.borrowed = true;
            this.borrowedDate = new Date();
            this.borrowedBy = user;
            user.addBorrowedBook(this);
            return true;
        }
        return false;
    }

    public void returnBook() {
        if (borrowed && borrowedBy != null) {
            borrowedBy.removeBorrowedBook(this);
            this.borrowed = false;
            this.borrowedDate = null;
            this.borrowedBy = null;
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", borrowed=" + borrowed +
                '}';
    }
}