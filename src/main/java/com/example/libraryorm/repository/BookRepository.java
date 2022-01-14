package com.example.libraryorm.repository;

import com.example.libraryorm.entities.Book;
import com.example.libraryorm.entities.Comment;

import java.util.List;

public interface BookRepository {

    Book save(Book book);

    Book findById(int id);

    Book findByTitle(String title);

    List<Comment> findAllCommentsById(int id);

    List<Book> findAll();
}
