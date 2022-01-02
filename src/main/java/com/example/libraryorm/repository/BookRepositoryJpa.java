package com.example.libraryorm.repository;

import com.example.libraryorm.entities.Book;
import lombok.val;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Repository
public class BookRepositoryJpa implements BookRepository{

    @PersistenceContext
    private final EntityManager entityManager;

    public BookRepositoryJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
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
    public Optional<Book> findById(int id) {
        return Optional.ofNullable(
                entityManager.find(Book.class, id));
    }
}
