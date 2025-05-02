package spring.course.observer;

import spring.course.models.Book;
import java.util.ArrayList;
import java.util.List;

public class BookAvailabilitySubject implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private Book book;

    @Override
    public void registerObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(book);
        }
    }

    public void updateBookAvailability(Book book) {
        this.book = book;
        notifyObservers();
    }
}