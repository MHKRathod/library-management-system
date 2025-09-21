package com.lms.strategy;

import com.lms.model.Book;
import java.util.List;

public interface SearchStrategy {
    List<Book> search(List<Book> books, String query);
}
