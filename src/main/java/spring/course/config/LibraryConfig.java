package spring.course.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import spring.course.factory.BookFactory;
import spring.course.factory.FictionBookFactory;
import spring.course.factory.NonFictionBookFactory;
import spring.course.observer.BookAvailabilitySubject;
import spring.course.observer.LibrarianObserver;
import spring.course.observer.UserNotificationObserver;

@Configuration
public class LibraryConfig {

    @Bean
    @Primary
    public BookFactory fictionBookFactory() {
        return new FictionBookFactory("General Fiction");
    }

    @Bean
    public BookFactory nonFictionBookFactory() {
        return new NonFictionBookFactory("General Knowledge");
    }

    @Bean
    public BookAvailabilitySubject bookAvailabilitySubject() {
        BookAvailabilitySubject subject = new BookAvailabilitySubject();
        subject.registerObserver(librarianObserver());
        subject.registerObserver(userNotificationObserver());
        return subject;
    }

    @Bean
    public LibrarianObserver librarianObserver() {
        return new LibrarianObserver();
    }

    @Bean
    public UserNotificationObserver userNotificationObserver() {
        return new UserNotificationObserver();
    }
}