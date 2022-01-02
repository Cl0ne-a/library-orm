package com.example.libraryorm.repository;

import com.example.libraryorm.entities.Book;

import java.util.Optional;

public interface BookRepository {
    Book save(Book book);
    Optional<Book> findById(int id);
}
