package com.example.libraryorm.service.impl;

import com.example.libraryorm.entities.Genre;
import com.example.libraryorm.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements com.example.libraryorm.service.GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> listGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Genre addGenre(Genre genre) {
        return genreRepository.addGenre(genre);
    }

    @Override
    public Genre findById(int genreId) {
        return genreRepository.findById(genreId);
    }
}
