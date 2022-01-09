package com.example.libraryorm.repository.jpa;

import com.example.libraryorm.entities.Author;
import com.example.libraryorm.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class AuthorRepositoryJpa implements AuthorRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    public AuthorRepositoryJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> findAll() {
        return entityManager.createQuery("select a from Author a", Author.class)
                .getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public Author findById(int id) {
        return entityManager.find(Author.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Author addAuthor(Author author) {
        int id = author.getId();
        if (id == 0) {
            entityManager.persist(author);
            return author;
        } else {
            return entityManager.merge(author);
        }
    }
}

