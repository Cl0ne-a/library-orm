package com.example.libraryorm.repository;

import com.example.libraryorm.entities.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);
    Optional<Book> findById(int id);

    Book findByTitle(String title);

    boolean updateTitleById(int id, String title);

    List<Book> findAll();
//    void deleteById(long id);
}
