package com.example.libraryorm.repository.jpa;

import com.example.libraryorm.entities.Comment;
import com.example.libraryorm.repository.CommentRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class CommentRepositoryJpa implements CommentRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    public CommentRepositoryJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Comment findById(int id) {
        return entityManager.find(Comment.class, id);
    }
}
