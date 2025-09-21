package com.lms;

import com.lms.model.Book;
import com.lms.model.Patron;
import com.lms.service.Library;
import com.lms.service.LendingService;
import com.lms.strategy.SearchByAuthor;
import com.lms.strategy.SearchByISBN;
import com.lms.strategy.SearchByTitle;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // ---------- Initialize Library ----------
        Library library = new Library();

        // ---------- Add books ----------
        Book book1 = new Book("ISBN001", "Java Basics", "John Doe", 2020);
        Book book2 = new Book("ISBN002", "OOP in Java", "Jane Smith", 2021);
        Book book3 = new Book("ISBN003", "Advanced Java", "John Doe", 2022);
        Book book4 = new Book("ISBN004", "Python Basics", "Alice Brown", 2021);

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);

        // ---------- Add patrons ----------
        Patron patron1 = new Patron("P001", "Hari Rathod", "hari@example.com");
        Patron patron2 = new Patron("P002", "Alice Smith", "alice@example.com");

        library.addPatron(patron1);
        library.addPatron(patron2);

        // ---------- Initialize LendingService ----------
        LendingService lendingService = new LendingService(library);

        // ---------- Borrow / Return ----------
        System.out.println("=== Borrow / Return Demo ===");
        System.out.println("P001 borrows ISBN001: " + lendingService.checkoutBook("P001", "ISBN001"));
        System.out.println("P002 tries to borrow ISBN001: " + lendingService.checkoutBook("P002", "ISBN001"));
        System.out.println("P001 returns ISBN001: " + lendingService.returnBook("P001", "ISBN001"));
        System.out.println("P002 borrows ISBN001: " + lendingService.checkoutBook("P002", "ISBN001"));

        System.out.println("\nAvailable books:");
        library.availableBooks().forEach(b -> System.out.println(b.getTitle()));

        // ---------- Search Demo ----------
        System.out.println("\n=== Search Demo ===");

        // Search by Title
        library.setSearchStrategy(new SearchByTitle());
        List<Book> titleSearch = library.searchBooks("Java");
        System.out.println("Search by Title 'Java':");
        titleSearch.forEach(b -> System.out.println(b.getTitle()));

        // Search by Author
        library.setSearchStrategy(new SearchByAuthor());
        List<Book> authorSearch = library.searchBooks("John Doe");
        System.out.println("\nSearch by Author 'John Doe':");
        authorSearch.forEach(b -> System.out.println(b.getTitle()));

        // Search by ISBN
        library.setSearchStrategy(new SearchByISBN());
        List<Book> isbnSearch = library.searchBooks("ISBN004");
        System.out.println("\nSearch by ISBN 'ISBN004':");
        isbnSearch.forEach(b -> System.out.println(b.getTitle()));

        // ---------- Recommendations Demo ----------
        System.out.println("\n=== Recommendations Demo ===");
        System.out.println("Top 3 most borrowed books:");
        library.getMostBorrowedBooks(3).forEach(b ->
            System.out.println(b.getTitle() + " (Borrowed " + b.getTimesBorrowed() + " times)")
        );

        // ---------- Lending Records Demo ----------
        System.out.println("\nAll Lending Records:");
        lendingService.getAllLendingRecords().forEach(record ->
            System.out.println(
                record.getRecordId() + " | " +
                record.getBookTitle() + " | " +
                record.getPatronId() + " | " +
                record.getStatus() + " | " +
                record.getBorrowedDate() + " -> " +
                (record.getReturnedDate() != null ? record.getReturnedDate() : "Not returned")
            )
        );
    }
}
