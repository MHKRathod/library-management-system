package com.lms.model;

import java.time.LocalDate;
import java.util.UUID;

public class LendingRecord {
    private final String recordId;
    private final String bookIsbn;
    private final String patronId;
    private final String bookTitle;
    private final LocalDate borrowedDate;
    private final LocalDate dueDate;
    private LocalDate returnedDate;
    private String status; // "BORROWED" or "RETURNED"

    // Constructor
    public LendingRecord(String bookIsbn, String patronId, String bookTitle) {
        this.recordId = UUID.randomUUID().toString();
        this.bookIsbn = bookIsbn;
        this.patronId = patronId;
        this.bookTitle = bookTitle;
        this.borrowedDate = LocalDate.now();
        this.dueDate = borrowedDate.plusWeeks(2); // loan period 2 weeks
        this.status = "BORROWED";
    }

    // Mark book returned
    public void markReturned() {
        this.returnedDate = LocalDate.now();
        this.status = "RETURNED";
    }

    // --------- Getters ---------
    public String getRecordId() { return recordId; }
    public String getBookIsbn() { return bookIsbn; }
    public String getPatronId() { return patronId; }
    public String getBookTitle() { return bookTitle; }
    public LocalDate getBorrowedDate() { return borrowedDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnedDate() { return returnedDate; }
    public String getStatus() { return status; }
}
