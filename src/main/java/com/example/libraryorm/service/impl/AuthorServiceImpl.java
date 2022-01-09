package com.example.libraryorm.service.impl;

import com.example.libraryorm.entities.Author;
import com.example.libraryorm.repository.AuthorRepository;
import com.example.libraryorm.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> listAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author addAuthor(Author author) {
        return authorRepository.addAuthor(author);
    }

    @Override
    public Author findById(int authorId) {
        return authorRepository.findById(authorId);
    }
}
