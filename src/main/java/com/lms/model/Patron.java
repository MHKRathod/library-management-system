package com.lms.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a library patron (member).
 * Tracks current borrowed ISBNs, reservations and historical borrows.
 */
public class Patron {
    private final String id;
    private String name;
    private String email;

    // lists hold ISBN strings
    private final List<String> borrowedIsbns = new ArrayList<>();
    private final List<String> reservationIsbns = new ArrayList<>();
    private final List<String> historyIsbns = new ArrayList<>();

    public Patron(String id, String name, String email) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("Patron id required");
        this.id = id.trim();
        this.name = name == null ? "" : name.trim();
        this.email = email == null ? "" : email.trim();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public void setName(String name) { this.name = name == null ? "" : name.trim(); }
    public void setEmail(String email) { this.email = email == null ? "" : email.trim(); }

    // Borrowing operations
    public void borrow(String isbn) {
        if (!borrowedIsbns.contains(isbn)) {
            borrowedIsbns.add(isbn);
        }
        historyIsbns.add(isbn);
    }
    public void returned(String isbn) {
        borrowedIsbns.remove(isbn);
    }

    // Reservation operations
    public void reserve(String isbn) {
        if (!reservationIsbns.contains(isbn)) reservationIsbns.add(isbn);
    }
    public void cancelReservation(String isbn) {
        reservationIsbns.remove(isbn);
    }

    // Read-only views to preserve encapsulation
    public List<String> getBorrowedIsbns() {
        return Collections.unmodifiableList(borrowedIsbns);
    }
    public List<String> getReservationIsbns() {
        return Collections.unmodifiableList(reservationIsbns);
    }
    public List<String> getHistoryIsbns() {
        return Collections.unmodifiableList(historyIsbns);
    }

    @Override
    public String toString() {
        return String.format("Patron{id='%s', name='%s', email='%s'}", id, name, email);
    }
}
