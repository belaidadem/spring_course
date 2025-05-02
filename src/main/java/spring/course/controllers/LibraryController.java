package spring.course.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.course.factory.BookFactory;
import spring.course.models.Book;
import spring.course.models.User;
import spring.course.models.enums.Role;
import spring.course.services.LibraryService;

import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryController {

    private final LibraryService libraryService;
    private final BookFactory fictionBookFactory;
    private final BookFactory nonFictionBookFactory;

    @Autowired
    public LibraryController(LibraryService libraryService,
                             @Qualifier("fictionBookFactory") BookFactory fictionBookFactory,
                             @Qualifier("nonFictionBookFactory") BookFactory nonFictionBookFactory) {
        this.libraryService = libraryService;
        this.fictionBookFactory = fictionBookFactory;
        this.nonFictionBookFactory = nonFictionBookFactory;

        // Initialize some sample data
        initSampleData();
    }

    private void initSampleData() {
        // Add some users
        libraryService.addUser(new User(1L, "Admin User", "admin@library.com", "admin123", Role.ADMIN));
        libraryService.addUser(new User(2L, "Librarian User", "librarian@library.com", "lib123", Role.LIBRARIAN));
        libraryService.addUser(new User(3L, "Member User", "member@example.com", "mem123", Role.MEMBER));

        // Add some books
        libraryService.addBook(fictionBookFactory.createBook(1L, "1984", "George Orwell"));
        libraryService.addBook(fictionBookFactory.createBook(2L, "To Kill a Mockingbird", "Harper Lee"));
        libraryService.addBook(nonFictionBookFactory.createBook(3L, "A Brief History of Time", "Stephen Hawking"));
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(libraryService.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
        Book book = libraryService.getBookById(id);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/books/fiction")
    public ResponseEntity<Book> addFictionBook(@RequestBody FictionBookRequest request) {
        Book book = fictionBookFactory.createBook(request.getId(), request.getTitle(), request.getAuthor());
        libraryService.addBook(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @PostMapping("/books/nonfiction")
    public ResponseEntity<Book> addNonFictionBook(@RequestBody NonFictionBookRequest request) {
        Book book = nonFictionBookFactory.createBook(request.getId(), request.getTitle(), request.getAuthor());
        libraryService.addBook(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @PostMapping("/books/{bookId}/borrow/{userId}")
    public ResponseEntity<String> borrowBook(@PathVariable("bookId") Long bookId, @PathVariable("userId") Long userId) {
        boolean success = libraryService.borrowBook(bookId, userId);
        if (success) {
            return new ResponseEntity<>("Book borrowed successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to borrow book", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/books/{bookId}/return")
    public ResponseEntity<String> returnBook(@PathVariable("bookId") Long bookId) {
        boolean success = libraryService.returnBook(bookId);
        if (success) {
            return new ResponseEntity<>("Book returned successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to return book", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @RequestBody BookUpdateRequest request) {
        Book updatedBook = libraryService.updateBook(id, request.getTitle(), request.getAuthor());

        if (updatedBook != null) {
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        boolean deleted = libraryService.deleteBook(id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(libraryService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = libraryService.getUserById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody UserRequest request) {
        User user = new User(request.getId(), request.getName(), request.getEmail(),
                request.getPassword(), request.getRole());
        libraryService.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        boolean deleted = libraryService.deleteUser(id);

        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody UserUpdateRequest request) {
        User updatedUser = libraryService.updateUser(id, request.getName(), request.getEmail(),
                request.getPassword(), request.getRole());

        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Request DTOs for better API documentation
    static class FictionBookRequest {
        private Long id;
        private String title;
        private String author;

        // Getters and setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }
    }

    static class NonFictionBookRequest {
        private Long id;
        private String title;
        private String author;

        // Getters and setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }
    }

    // Request DTOs for updates
    static class BookUpdateRequest {
        private String title;
        private String author;

        // Getters and setters
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }
    }

    static class UserRequest {
        private Long id;
        private String name;
        private String email;
        private String password;
        private Role role;

        // Getters and setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public Role getRole() { return role; }
        public void setRole(Role role) { this.role = role; }
    }

    static class UserUpdateRequest {
        private String name;
        private String email;
        private String password;
        private Role role;

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public Role getRole() { return role; }
        public void setRole(Role role) { this.role = role; }
    }
}