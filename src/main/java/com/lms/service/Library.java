package com.lms.service;

import com.lms.model.Book;
import com.lms.model.Patron;
import com.lms.strategy.SearchStrategy;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Core in-memory repository for Books and Patrons.
 * Uses Map keyed by ISBN or Patron ID for O(1) lookup.
 *
 * Single Responsibility: data storage & CRUD operations.
 */
public class Library {
    private final Map<String, Book> books = new HashMap<>();
    private final Map<String, Patron> patrons = new HashMap<>();

    private SearchStrategy searchStrategy;

    public Library() { }

    // ---------- Search ----------
    public void setSearchStrategy(SearchStrategy strategy) {
        this.searchStrategy = strategy;
    }

    public List<Book> searchBooks(String query) {
        if (searchStrategy == null) return List.of();
        return searchStrategy.search(new ArrayList<>(books.values()), query);
    }

    // ---------- Book management ----------
    public void addBook(Book book) {
        Objects.requireNonNull(book);
        books.put(book.getIsbn(), book);
    }

    public Optional<Book> removeBook(String isbn) {
        if (isbn == null) return Optional.empty();
        return Optional.ofNullable(books.remove(isbn));
    }

    public Optional<Book> getBook(String isbn) {
        if (isbn == null) return Optional.empty();
        return Optional.ofNullable(books.get(isbn));
    }

    public void updateBook(Book book) {
        Objects.requireNonNull(book);
        if (!books.containsKey(book.getIsbn())) {
            throw new NoSuchElementException("Book not found: " + book.getIsbn());
        }
        books.put(book.getIsbn(), book);
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    // ---------- Patron management ----------
    public void addPatron(Patron patron) {
        Objects.requireNonNull(patron);
        patrons.put(patron.getId(), patron);
    }

    public Optional<Patron> getPatron(String id) {
        if (id == null) return Optional.empty();
        return Optional.ofNullable(patrons.get(id));
    }

    public void updatePatron(Patron patron) {
        Objects.requireNonNull(patron);
        if (!patrons.containsKey(patron.getId())) {
            throw new NoSuchElementException("Patron not found: " + patron.getId());
        }
        patrons.put(patron.getId(), patron);
    }

    public List<Patron> getAllPatrons() {
        return new ArrayList<>(patrons.values());
    }

    // ---------- Borrow / Return ----------
    public boolean borrowBook(String patronId, String isbn) {
        Book book = books.get(isbn);
        Patron patron = patrons.get(patronId);
        if (book != null && patron != null && book.isAvailable()) {
            book.setAvailable(false);
            book.incrementTimesBorrowed();
            patron.addToHistory(isbn);  // you should have this method in Patron
            return true;
        }
        return false;
    }

    public boolean returnBook(String patronId, String isbn) {
        Book book = books.get(isbn);
        Patron patron = patrons.get(patronId);
        if (book != null && patron != null && !book.isAvailable()) {
            book.setAvailable(true);
            return true;
        }
        return false;
    }

      // ---------- Inventory ----------
    public List<Book> availableBooks() {
        return books.values().stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }

    public List<Book> getMostBorrowedBooks(int limit) {
    return books.values().stream()
                .sorted(Comparator.comparingInt(Book::getTimesBorrowed).reversed())
                .limit(limit)
                .collect(Collectors.toList());
}

}
