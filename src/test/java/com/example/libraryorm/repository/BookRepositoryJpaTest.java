package com.example.libraryorm.repository;

import com.example.libraryorm.entities.Author;
import com.example.libraryorm.entities.Book;
import com.example.libraryorm.entities.Genre;
import com.example.libraryorm.repository.jpa.BookRepositoryJpa;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("BookRepository methods testing: ")
@DataJpaTest
@Import(BookRepositoryJpa.class)
public class BookRepositoryJpaTest {

    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("finds correct entity by id")
    @Test
    void findById() {
        System.out.println("findById()");

        int idUnderTest = 1;
        Book expected = Book.builder()
                .id(idUnderTest)
                .title("White magic")
                .author(Author.builder().id(idUnderTest).name("Mr.Pool").build())
                .genre(Genre.builder().id(idUnderTest).genre("comedy").build())
                .build();

        var actual = bookRepositoryJpa.findById(idUnderTest);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("finds correct entity by name")
    @Test
    void findByName() {
        System.out.println("findByName()");
        val title = "White magic";
        int idUnderTest = 1;
        val expected = testEntityManager.find(Book.class, idUnderTest);
        val actual = bookRepositoryJpa.findByTitle(title);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("display all books and according data")
    @Test
    void findAll() {
        System.out.println("findAll()");
        int firstIddUnderTest = 1;
        int secondIddUnderTest = 2;
        int thirdIddUnderTest = 3;
        SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        List<Book> expected = List.of(
                Book.builder()
                        .id(firstIddUnderTest)
                        .title("White magic")
                        .author(Author.builder().id(firstIddUnderTest).name("Mr.Pool").build())
                        .genre(Genre.builder().id(firstIddUnderTest).genre("comedy").build())
                        .build(),
                Book.builder()
                        .id(secondIddUnderTest)
                        .title("Black magic")
                        .author(Author.builder().id(secondIddUnderTest).name("Mr. Smith").build())
                        .genre(Genre.builder().id(secondIddUnderTest).genre("horror").build())
                        .build(),
                Book.builder()
                        .id(thirdIddUnderTest)
                        .title("Test Book")
                        .author(Author.builder().id(secondIddUnderTest).name("Mr. Smith").build())
                        .genre(Genre.builder().id(firstIddUnderTest).genre("comedy").build())
                        .build()
        );

        val actual = bookRepositoryJpa.findAll();
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        Assertions.assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(1);
    }

    @DisplayName("the entity is saved to db")
    @Test
    void save() {
        System.out.println("save()");
        Book testBook = Book.builder()
                .title("TestBook")
                .author(Author.builder().name("Test author").build())
                .genre(Genre.builder().genre("Horror").build())
                .build();

        Book actual = bookRepositoryJpa.save(testBook);
        int actualId = actual.getId();

        Book expected = testEntityManager.find(Book.class, actualId);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("deleting book from db")
    @Test
    void delete() {
        System.out.println("delete()");
        Book expectedToDelete = bookRepositoryJpa.findByTitle("Test Book");
        bookRepositoryJpa.deleteById(expectedToDelete.getId());

        assertThat(testEntityManager.find(Book.class, 3)).isNull();
    }
}