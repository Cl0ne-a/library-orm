package com.example.libraryorm.repository.jpa;

import com.example.libraryorm.entities.Comment;
import com.example.libraryorm.repository.CommentRepository;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Service
public class CommentRepositoryJpa implements CommentRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    public CommentRepositoryJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Comment save(Comment comment) {
        val id = comment.getId();
        if(id == 0) {
            entityManager.persist(comment);
            return comment;
        }else {
            return entityManager.merge(comment);
        }
    }

    @Override
    public Comment findById(int id) {
        return entityManager.find(Comment.class, id);
    }

    @Override
    public Comment findByComment(String comment) {
        TypedQuery<Comment> query =
                entityManager.createQuery(
                        "select com from Comment com where com.comment = :comment",
                        Comment.class);
        query.setParameter("comment", comment);
        return query.getSingleResult();
    }
}
