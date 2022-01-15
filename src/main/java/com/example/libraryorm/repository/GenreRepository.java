package com.example.libraryorm.repository;

import com.example.libraryorm.entities.Genre;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GenreRepository {
    @Transactional(readOnly = true)
    List<Genre> findAll();

    @Transactional(readOnly = true)
    Genre findById(int id);

    @Transactional(propagation = Propagation.REQUIRED)
    Genre addGenre(Genre genre);
}
