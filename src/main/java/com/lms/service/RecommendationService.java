package com.lms.service;

import com.lms.model.Book;
import com.lms.model.Patron;

import java.util.*;
import java.util.stream.Collectors;

public class RecommendationService {
    private final Library library;

    public RecommendationService(Library library) {
        this.library = library;
    }

    public List<Book> recommendBooks(String patronId) {
        Optional<Patron> patronOpt = library.getPatron(patronId);
        if (patronOpt.isEmpty()) return List.of();

        Patron patron = patronOpt.get();
        List<String> borrowed = patron.getHistoryIsbns();
        if (borrowed == null) borrowed = new ArrayList<>();
        Set<String> borrowedSet = new HashSet<>(borrowed);

        // Recommend books not yet borrowed, sorted by popularity
        return library.getAllBooks().stream()
                .filter(b -> !borrowedSet.contains(b.getIsbn()))
                .sorted(Comparator.comparingInt(Book::getTimesBorrowed).reversed())
                .limit(5) // optional: only top 5
                .collect(Collectors.toList());
    }
}
