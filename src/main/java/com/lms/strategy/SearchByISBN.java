package com.lms.strategy;

import com.lms.model.Book;
import java.util.List;
import java.util.stream.Collectors;

public class SearchByISBN implements SearchStrategy {
    @Override
    public List<Book> search(List<Book> books, String query) {
        return books.stream()
                    .filter(b -> b.getIsbn().equals(query))
                    .collect(Collectors.toList());
    }
}
