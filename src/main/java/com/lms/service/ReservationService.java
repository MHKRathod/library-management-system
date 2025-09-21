package com.lms.service;

import com.lms.model.Patron;
import java.util.*;
import java.util.logging.Logger;

/**
 * Handles book reservations using a queue per book.
 * Notifies the first patron in line when a book becomes available.
 */
public class ReservationService {
    private static final Logger logger = Logger.getLogger(ReservationService.class.getName());

    // Map of ISBN -> queue of patrons waiting for this book
    private final Map<String, Queue<Patron>> reservationMap = new HashMap<>();

    /**
     * Patron reserves a book.
     * Adds them to the queue for the ISBN.
     */
    public void reserveBook(String isbn, Patron patron) {
        reservationMap.computeIfAbsent(isbn, k -> new LinkedList<>());

        Queue<Patron> queue = reservationMap.get(isbn);
        if (!queue.contains(patron)) {
            queue.add(patron);
            patron.reserve(isbn);
            logger.info("Book reserved: " + isbn + " by Patron: " + patron.getId());
        } else {
            logger.info("Patron already in reservation queue: " + patron.getId());
        }
    }

    /**
     * Called when a book is returned.
     * Notifies the first patron in queue if any.
     */
    public void handleBookReturn(String isbn) {
        Queue<Patron> queue = reservationMap.get(isbn);
        if (queue == null || queue.isEmpty()) return;

        Patron nextPatron = queue.poll();
        if (nextPatron != null) {
            nextPatron.cancelReservation(isbn); // remove from patron's reserved list
            logger.info("Book available for patron: " + nextPatron.getId() + " (ISBN: " + isbn + ")");
        }
    }

    /**
     * Get the queue of patrons for a given ISBN (read-only)
     */
    public List<Patron> getReservationQueue(String isbn) {
        Queue<Patron> queue = reservationMap.get(isbn);
        if (queue == null) return List.of();
        return List.copyOf(queue);
    }
}
