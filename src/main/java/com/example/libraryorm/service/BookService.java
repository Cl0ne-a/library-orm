package com.example.libraryorm.service;

import com.example.libraryorm.exceptions.BookPersistingException;
import com.example.libraryorm.entities.Book;

import java.util.List;

public interface BookService {
    Book addBook(Book book) throws BookPersistingException;

    List<Book> findAll();

    Book findById(int id) throws BookPersistingException;

    Book findByTitle(String title) throws BookPersistingException;

    void updateTitleById(int id, String title) throws BookPersistingException;

    void deleteById(int id) throws BookPersistingException;
}
