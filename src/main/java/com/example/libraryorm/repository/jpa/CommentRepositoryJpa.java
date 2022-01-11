package com.example.libraryorm.repository.jpa;

import com.example.libraryorm.entities.Comment;
import com.example.libraryorm.repository.CommentRepository;
import lombok.val;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CommentRepositoryJpa implements CommentRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    public CommentRepositoryJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
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

    @Transactional(readOnly = true)
    @Override
    public Comment findById(int id) {
        return entityManager.find(Comment.class, id);
    }

    @Transactional(readOnly = true)
    @Override
    public Comment findByComment(String comment) {
        TypedQuery<Comment> query =
                entityManager.createQuery(
                        "select com from Comment com where com.comment = :comment",
                        Comment.class);
        query.setParameter("comment", comment);
        return query.getSingleResult();
    }

    @Transactional
    @Override
    public void updateCommentById(int id, String comment) {
        String sql = "update Comment c set c.comment = :comment where c.id = :id";
        Query query = entityManager.createQuery(sql);
        query.setParameter("id", id);
        query.setParameter("comment", comment);
        query.executeUpdate();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findAll() {
        String sql = "select com from Comment com join fetch com.book";
        TypedQuery<Comment> query = entityManager.createQuery(sql, Comment.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        Comment comment = entityManager.find(Comment.class, id);
        entityManager.remove(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findAllByBookId(int id) {
        String sql = "select com from Comment com join fetch com.book where com.book.id = :id";

        TypedQuery<Comment> query = entityManager.createQuery(sql, Comment.class)
                .setParameter("id", id);

        return query.getResultList();
    }
}
