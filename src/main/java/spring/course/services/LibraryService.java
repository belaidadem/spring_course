package spring.course.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import spring.course.factory.BookFactory;
import spring.course.models.Book;
import spring.course.models.User;
import spring.course.observer.BookAvailabilitySubject;
import spring.course.models.enums.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibraryService {
    private List<Book> books = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private BookFactory bookFactory;
    private BookAvailabilitySubject bookAvailabilitySubject;

    public LibraryService(@Qualifier("fictionBookFactory")
     BookFactory bookFactory, BookAvailabilitySubject bookAvailabilitySubject) {
        this.bookFactory = bookFactory;
        this.bookAvailabilitySubject = bookAvailabilitySubject;
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Book getBookById(Long id) {
        Optional<Book> book = books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();
        return book.orElse(null);
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Long id) {
        books.removeIf(book -> book.getId().equals(id));
    }

    public boolean borrowBook(Long bookId, Long userId) {
        Book book = getBookById(bookId);
        User user = getUserById(userId);

        if (book != null && user != null && book.borrow(user)) {
            bookAvailabilitySubject.updateBookAvailability(book);
            return true;
        }
        return false;
    }

    public boolean returnBook(Long bookId) {
        Book book = getBookById(bookId);

        if (book != null && book.isBorrowed()) {
            book.returnBook();
            bookAvailabilitySubject.updateBookAvailability(book);
            return true;
        }
        return false;
    }

    public Book updateBook(Long id, String title, String author) {
        Book bookToUpdate = getBookById(id);

        if (bookToUpdate != null) {
            if (title != null) bookToUpdate.setTitle(title);
            if (author != null) bookToUpdate.setAuthor(author);
            return bookToUpdate;
        }

        return null; // Book not found
    }


    public boolean deleteBook(Long id) {
        // Find the book in the list
        Book bookToDelete = books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (bookToDelete == null) {
            return false; // Book not found
        }

        // If book is borrowed, update the user who borrowed it
        if (bookToDelete.isBorrowed() && bookToDelete.getBorrowedBy() != null) {
            User borrower = bookToDelete.getBorrowedBy();
            // Remove the book from the user's borrowed books list
            if (borrower.getBorrowedBooks() != null) {
                borrower.setBorrowedBooks(
                        borrower.getBorrowedBooks().stream()
                                .filter(book -> !book.getId().equals(id))
                                .collect(Collectors.toList())
                );
            }
        }

        // Remove the book from the books list
        books.removeIf(book -> book.getId().equals(id));
        return true;
    }



    public List<User> getAllUsers() {
        return users;
    }

    public User getUserById(Long id) {
        Optional<User> user = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
        return user.orElse(null);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public User updateUser(Long id, String name, String email, String password, Role role) {
        User userToUpdate = getUserById(id);

        if (userToUpdate != null) {
            if (name != null) userToUpdate.setName(name);
            if (email != null) userToUpdate.setEmail(email);
            if (password != null) userToUpdate.setPassword(password);
            if (role != null) userToUpdate.setRole(role);
            return userToUpdate;
        }

        return null; // User not found
    }

    public boolean deleteUser(Long id) {
        // Find the user in the list
        User userToDelete = users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (userToDelete == null) {
            return false; // User not found
        }

        // Return books borrowed by the user
        if (userToDelete.getBorrowedBooks() != null) {
            for (Book book : userToDelete.getBorrowedBooks()) {
                book.setBorrowed(false);
                book.setBorrowedDate(null);
                book.setBorrowedBy(null);
            }
        }

        // Remove the user from the users list
        users.removeIf(user -> user.getId().equals(id));
        return true;
    }



}