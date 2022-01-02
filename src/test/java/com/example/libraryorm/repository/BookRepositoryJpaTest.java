package com.example.libraryorm.repository;

import com.example.libraryorm.entities.Author;
import com.example.libraryorm.entities.Book;
import com.example.libraryorm.entities.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Book repository provides following options: ")
@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {

    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("the entity is saved to db")
    @Test
    void save() {
        Book expectedBook = Book.builder()
                .author(Author.builder()
                        .name("Test author")
                        .build())
                .genre(Genre.builder()
                        .genre("Horror")
                        .build())
                .build();
        bookRepositoryJpa.save(expectedBook);

        var actual = testEntityManager.find(Book.class, 1);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("finds correct entity")
    @Test
    void findById() {
        Book expectedBook = Book.builder()
                .author(Author.builder()
                        .name("Test author")
                        .build())
                .genre(Genre.builder()
                        .genre("Horror")
                        .build())
                .build();
        bookRepositoryJpa.save(expectedBook);

        var actual = bookRepositoryJpa.findById(1);
        var expected = testEntityManager.find(Book.class, 1);

        assertThat(actual).isPresent().get().isEqualTo(expected);
    }
}