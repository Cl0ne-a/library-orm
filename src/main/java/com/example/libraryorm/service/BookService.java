package com.example.libraryorm.service;

import com.example.libraryorm.entities.Comment;
import com.example.libraryorm.exceptions.BookPersistingException;
import com.example.libraryorm.entities.Book;

import java.util.List;

public interface BookService {
    Book addBook(Book book) throws BookPersistingException;

    List<Comment> findAllComments(int bookId);

    List<Book> findAllBooks();

    Book findById(int id) throws BookPersistingException;

    Book findByTitle(String title) throws BookPersistingException;

    void deleteById(int id) throws BookPersistingException;
}
