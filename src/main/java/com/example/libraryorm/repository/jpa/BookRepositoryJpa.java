package com.example.libraryorm.repository.jpa;

import com.example.libraryorm.entities.Book;
import com.example.libraryorm.entities.Comment;
import com.example.libraryorm.repository.BookRepository;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public BookRepositoryJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

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
    public List<Book> findAll() {
        TypedQuery<Book> query = entityManager
                .createQuery("select b from Book b join fetch b.author join fetch b.genre",  Book.class);
        return query.getResultList();
    }

    @Override
    public boolean deleteById(int id) {
        entityManager.find(Book.class, id).setRemoved(true);
        return entityManager.find(Book.class, id).isRemoved();
    }

    @Override
    public List<Comment> findAllCommentsById(int id) {
        val book = entityManager.find(Book.class, id);
        return book.getComments();
    }
}
