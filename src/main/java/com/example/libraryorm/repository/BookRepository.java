package com.example.libraryorm.repository;

import com.example.libraryorm.entities.Book;

import java.util.List;

public interface BookRepository {
    boolean notExist(Book book);

    boolean present(int bookId);

    boolean present(String bookTitle);

    Book save(Book book);

    Book findById(int id);

    Book findByTitle(String title);

    void updateTitleById(int id, String title);

    List<Book> findAll();

    void deleteById(int id);
}
