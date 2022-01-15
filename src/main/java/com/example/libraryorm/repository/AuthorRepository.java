package com.example.libraryorm.repository;

import com.example.libraryorm.entities.Author;

import java.util.List;

public interface AuthorRepository {
    List<Author> findAll();
    Author findById(int id);
    Author addAuthor(Author author);
}
