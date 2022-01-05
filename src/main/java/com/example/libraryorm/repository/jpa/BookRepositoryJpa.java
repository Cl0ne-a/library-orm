package com.example.libraryorm.repository.jpa;

import com.example.libraryorm.entities.Book;
import com.example.libraryorm.repository.BookRepository;
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
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public BookRepositoryJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean notExist(Book book) {
        String title = book.getTitle();
        String sql = "select b from Book b where b.title = :title";
        TypedQuery<Book> query = entityManager.createQuery(sql, Book.class).setParameter("title", title);
        return query.getResultList().isEmpty();
    }


    @Override
    public boolean present(int bookId) {
        String sql = "select b from Book b where b.id = :bookId";
        TypedQuery<Book> query = entityManager
                .createQuery(sql, Book.class)
                .setParameter("bookId", bookId);

        return !query.getResultList().isEmpty();
    }

    @Override
    public boolean present(String bookTitle) {
        String sql = "select b from Book b where b.title = :bookTitle";
        TypedQuery<Book> query = entityManager
                .createQuery(sql, Book.class)
                .setParameter("bookTitle", bookTitle);

        return !query.getResultList().isEmpty();
    }

    @Override
    public Book save(Book book) {
        val id = book.getId();
        if(id == 0) {
            entityManager.persist(book);
            return book;
        }else {
            return entityManager.merge(book);
        }
    }

    @Override
    public Book findById(int id) {
        return entityManager.find(Book.class, id);
    }

    @Override
    public Book findByTitle(String title) {
        TypedQuery<Book> query = entityManager
                .createQuery("select b from Book b where b.title = :title", Book.class)
                .setParameter("title", title);
        return query.getSingleResult();
    }

    @Override
    public void updateTitleById(int id, String title) {
        String sql = "update Book b set b.title = :title where b.id = :id";
        Query query = entityManager.createQuery(sql);
        query.setParameter("id", id);
        query.setParameter("title", title);
        query.executeUpdate();
    }

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("book-graph");
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b " +
                "join fetch b.genre join fetch b.author", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public void deleteById(int id) {
        val book = entityManager.find(Book.class, id);
        entityManager.remove(book);
    }
}
