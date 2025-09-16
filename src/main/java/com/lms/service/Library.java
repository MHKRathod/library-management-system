package com.lms.service;

import com.lms.model.Book;
import com.lms.model.Patron;

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

    public Library() { }

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

    // ---------- Search functionality ----------
    public List<Book> searchByTitle(String query) {
        if (query == null || query.isBlank()) return Collections.emptyList();
        final String q = query.toLowerCase();
        return books.values().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    public List<Book> searchByAuthor(String query) {
        if (query == null || query.isBlank()) return Collections.emptyList();
        final String q = query.toLowerCase();
        return books.values().stream()
                .filter(b -> b.getAuthor().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    public Optional<Book> searchByIsbn(String isbn) {
        return getBook(isbn);
    }

    // ---------- Inventory ----------
    public List<Book> availableBooks() {
        return books.values().stream().filter(Book::isAvailable).collect(Collectors.toList());
    }

    public List<Book> borrowedBooks() {
        return books.values().stream().filter(b -> !b.isAvailable()).collect(Collectors.toList());
    }
}
