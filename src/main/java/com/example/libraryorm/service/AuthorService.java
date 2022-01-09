package com.example.libraryorm.service;

import com.example.libraryorm.entities.Author;

import java.util.List;

public interface AuthorService {
    List<Author> listAuthors();
    Author addAuthor(Author author);
    Author findById(int authorId);
}
