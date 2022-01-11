package com.example.libraryorm.repository.jpa;

import com.example.libraryorm.entities.Genre;
import com.example.libraryorm.repository.GenreRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class GenreRepositoryJpa implements GenreRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    public GenreRepositoryJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> findAll() {
        return entityManager.createQuery("select g from Genre g", Genre.class)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Genre findById(int id) {
        return entityManager.find(Genre.class, id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Genre addGenre(Genre genre) {
        int id = genre.getId();
        if (id == 0) {
            entityManager.persist(genre);
            return genre;
        } else {
            return entityManager.merge(genre);
        }
    }
}

