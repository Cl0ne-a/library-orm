package com.example.libraryorm.repository;

import com.example.libraryorm.entities.Author;
import com.example.libraryorm.entities.Book;
import com.example.libraryorm.entities.Genre;
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

@DisplayName("Book repository provides following options: ")
@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {

    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("finds correct entity by id")
    @Test
    void findById() {
        System.out.println("findById()");
        Book expectedBook = Book.builder()
                .author(Author.builder()
                        .name("Test author")
                        .build())
                .genre(Genre.builder()
                        .genre("Horror")
                        .build())
                .build();
        testEntityManager.persist(expectedBook);

        var actual = bookRepositoryJpa.findById(1);
        var expected = testEntityManager.find(Book.class, 1);

        assertThat(actual).isPresent().get().isEqualTo(expected);
    }

    @DisplayName("finds correct entity by name")
    @Test
    void findByName() {
        System.out.println("findByName()");
        val title = "TestBook";
        val author = "Test author";
        val genre = "Horror";
        Book expectedBook = Book.builder()
                .title(title)
                .author(Author.builder()
                        .name(author)
                        .build())
                .genre(Genre.builder()
                        .genre(genre)
                        .build())
                .build();
        bookRepositoryJpa.save(expectedBook);

        val actual = bookRepositoryJpa.findByTitle(title);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("update book title by id")
    @Test
    void updateTitleById() {
        System.out.println("updateTitleById()");
        Book expected = Book.builder()
                .id(3)
                .title("Updated TestBook")
                .author(Author.builder().id(2).name("Mr. Smith").build())
                .genre(Genre.builder().id(1).genre("comedy").build())
                .build();

        String newTitle = "Updated TestBook";
        bookRepositoryJpa.updateTitleById(3, newTitle);

        Book actual = testEntityManager.find(Book.class, 3);
        assertThat(actual).isEqualTo(expected);
    }


    @DisplayName("display all books and according data")
    @Test
    void findAll() {
        System.out.println("findAll()");
        SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        List<Book> expected = List.of(
                Book.builder()
                        .id(1)
                        .title("White magic")
                        .author(Author.builder().id(1).name("Mr.Pool").build())
                        .genre(Genre.builder().id(1).genre("comedy").build())
                        .build(),
                Book.builder()
                        .id(2)
                        .title("Black magic")
                        .author(Author.builder().id(2).name("Mr. Smith").build())
                        .genre(Genre.builder().id(2).genre("horror").build())
                        .build(),
                Book.builder()
                        .id(3)
                        .title("Test Book")
                        .author(Author.builder().id(2).name("Mr. Smith").build())
                        .genre(Genre.builder().id(1).genre("comedy").build())
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
        Book expected = Book.builder()
                .id(4)
                .title("expected")
                .author(Author.builder().id(1).build())
                .genre(Genre.builder().id(1).build())
                .build();
        Book actual = bookRepositoryJpa.save(expected);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}