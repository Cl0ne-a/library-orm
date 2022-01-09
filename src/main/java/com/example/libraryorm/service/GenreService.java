package com.example.libraryorm.service;

import com.example.libraryorm.entities.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> listGenres();

    Genre addGenre(Genre genre);

    Genre findById(int genreId);
}
