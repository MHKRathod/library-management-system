package com.lms.model;

/**
 * Immutable ISBN value (final) with mutable metadata.
 * Demonstrates encapsulation and useful toString().
 */
public class Book {
    private final String isbn;
    private String title;
    private String author;
    private int publicationYear;
    private boolean available;

    public Book(String isbn, String title, String author, int publicationYear) {
        if (isbn == null || isbn.isBlank()) throw new IllegalArgumentException("ISBN required");
        this.isbn = isbn.trim();
        this.title = title == null ? "" : title.trim();
        this.author = author == null ? "" : author.trim();
        this.publicationYear = publicationYear;
        this.available = true;
    }

    // Getters (no setter for ISBN)
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPublicationYear() { return publicationYear; }
    public boolean isAvailable() { return available; }

    // Setters for mutable fields (encapsulation)
    public void setTitle(String title) { this.title = title == null ? "" : title.trim(); }
    public void setAuthor(String author) { this.author = author == null ? "" : author.trim(); }
    public void setPublicationYear(int publicationYear) { this.publicationYear = publicationYear; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return String.format("Book{isbn='%s', title='%s', author='%s', year=%d, available=%s}",
                isbn, title, author, publicationYear, available);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book other = (Book) o;
        return isbn.equals(other.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}
