package com.example.libraryorm.repository.jpa;

import com.example.libraryorm.entities.Book;
import com.example.libraryorm.entities.Comment;
import com.example.libraryorm.repository.BookRepository;
import lombok.val;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public BookRepositoryJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public Book save(Book book) {
        val id = book.getId();
        if (id == 0) {
            entityManager.persist(book);
            return book;
        } else {
            return entityManager.merge(book);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Book findById(int id) {
        return entityManager.find(Book.class, id);
    }

    @Transactional(readOnly = true)
    @Override
    public Book findByTitle(String title) {
        TypedQuery<Book> query = entityManager
                .createQuery("select b from Book b where b.title = :title", Book.class)
                .setParameter("title", title);
        return query.getSingleResult();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("book-graph");
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b",  Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        val book = entityManager.find(Book.class, id);
        entityManager.remove(book);
    }

    @Override
    public List<Comment> findAllCommentsById(int id) {
        return Collections.emptyList();
    }
}
