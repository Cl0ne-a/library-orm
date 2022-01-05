package com.example.libraryorm.repository;

import com.example.libraryorm.entities.Comment;
import lombok.val;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class CommentRepositoryJpa implements CommentRepository{
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
    public Optional<Comment> findById(int id) {
        val comment = entityManager.find(Comment.class, id);
        return Optional.ofNullable(comment);
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

    @Override
    public void updateTitleById(int id, String comment) {
        String sql = "update Comment c set c.comment = :comment where c.id = :id";
        Query query = entityManager.createQuery(sql);
        query.setParameter("id", id);
        query.setParameter("comment", comment);
        query.executeUpdate();
    }

    @Override
    public List<Comment> findAll() {
        String sql = "select com from Comment com join fetch com.book";
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("comment-graph");
        TypedQuery<Comment> query = entityManager.createQuery(sql, Comment.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public void deleteById(int id) {
        Comment comment = entityManager.find(Comment.class, id);
        entityManager.remove(comment);
    }

    @Override
    public List<Comment> findAllByBookId(int id) {
        String sql = "select com from Comment com join fetch com.book where com.book.id = :id";
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("comment-graph");
        TypedQuery<Comment> query = entityManager
                .createQuery(sql, Comment.class)
                .setParameter("id", id)
                .setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }
}
