package com.example.libraryorm.service;

import com.example.libraryorm.exceptions.BookAlreadyExistException;
import com.example.libraryorm.entities.Book;
import com.example.libraryorm.exceptions.BookNotFoundException;

import java.util.List;

public interface BookService {
    Book addBook(Book book) throws BookAlreadyExistException;

    List<Book> findAll();

    Book findById(int id) throws BookNotFoundException;

    Book findByTitle(String title) throws BookNotFoundException;

    void updateTitleById(int id, String title) throws BookNotFoundException;

    void deleteById(int id) throws BookNotFoundException;
}
