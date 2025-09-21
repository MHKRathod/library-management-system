package com.lms.service;

import com.lms.model.Book;
import com.lms.model.Patron;
import com.lms.model.LendingRecord;  // NEW

import java.util.Optional;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles book checkout and return operations.
 * Demonstrates OOP, SRP, and Logger integration.
 */
public class LendingService {
    private final Library library;
    private final Logger logger = Logger.getLogger(LendingService.class.getName());
    private ReservationService reservationService;  // optional integration

    // NEW: stores all lending transactions
    private final List<LendingRecord> lendingRecords = new ArrayList<>();

    public LendingService(Library library) {
        this.library = library;
    }

    public void setReservationService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * Checkout a book for a patron.
     */
    public boolean checkoutBook(String patronId, String isbn) {
        Optional<Patron> patronOpt = library.getPatron(patronId);
        Optional<Book> bookOpt = library.getBook(isbn);

        if (patronOpt.isEmpty()) {
            logger.warning("Patron not found: " + patronId);
            return false;
        }
        if (bookOpt.isEmpty()) {
            logger.warning("Book not found: " + isbn);
            return false;
        }

        Book book = bookOpt.get();
        Patron patron = patronOpt.get();

        if (!book.isAvailable()) {
            logger.info("Book not available for checkout: " + isbn);
            return false;
        }

        // Mark book unavailable and update patron borrow list
        book.setAvailable(false);
        patron.borrow(isbn);

        // NEW: create LendingRecord
        LendingRecord record = new LendingRecord(isbn, patronId, book.getTitle());
        lendingRecords.add(record);
        logger.info("LendingRecord created: " + record.getRecordId());

        logger.info("Book checked out: " + isbn + " by Patron: " + patronId);
        return true;
    }

    /**
     * Return a book from a patron.
     */
    public boolean returnBook(String patronId, String isbn) {
        Optional<Patron> patronOpt = library.getPatron(patronId);
        Optional<Book> bookOpt = library.getBook(isbn);

        if (patronOpt.isEmpty()) {
            logger.warning("Patron not found: " + patronId);
            return false;
        }
        if (bookOpt.isEmpty()) {
            logger.warning("Book not found: " + isbn);
            return false;
        }

        Book book = bookOpt.get();
        Patron patron = patronOpt.get();

        if (!patron.getBorrowedIsbns().contains(isbn)) {
            logger.warning("Patron " + patronId + " did not borrow book: " + isbn);
            return false;
        }

        // Mark book available and update patron borrow list
        book.setAvailable(true);
        patron.returned(isbn);

        // NEW: find LendingRecord and mark returned
        lendingRecords.stream()
                .filter(r -> r.getBookIsbn().equals(isbn)
                        && r.getPatronId().equals(patronId)
                        && r.getStatus().equals("BORROWED"))
                .findFirst()
                .ifPresent(LendingRecord::markReturned);

        if (reservationService != null) {
            reservationService.handleBookReturn(isbn);
        }

        logger.info("Book returned: " + isbn + " by Patron: " + patronId);
        return true;
    }

    /**
     * Optional helper to get all lending records
     */
    public List<LendingRecord> getLendingRecords() {
        return lendingRecords;
    }

    public List<LendingRecord> getAllLendingRecords() {
    return lendingRecords;
}

}

