package com.example.libraryorm.repository;

import com.example.libraryorm.entities.Author;
import com.example.libraryorm.entities.Book;
import com.example.libraryorm.entities.Comment;
import com.example.libraryorm.entities.Genre;
import com.example.libraryorm.repository.jpa.CommentRepositoryJpa;
import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("CommentRepository methods testing: ")
@DataJpaTest
@Import(CommentRepositoryJpa.class)
class CommentRepositoryJpaTest {

    @Autowired
    private CommentRepositoryJpa commentRepositoryJpa;

    @Autowired
    private TestEntityManager testEntityManager;


    @DisplayName("updating existing comment works")
    @Test
    void update() {
        String newComment = "awesome book";

        commentRepositoryJpa.updateCommentById(1, newComment);

        Comment actual = commentRepositoryJpa.findByComment(newComment);
        assertThat(actual.getComment()).isEqualTo(newComment);
    }

    @DisplayName("finding by comment works")
    @Test
    void findByComment() {
        String comment = "good book";
        Author author = Author.builder().id(1).name("Mr.Pool").build();
        Genre genre = Genre.builder().id(1).genre("comedy").build();

        Comment expected = Comment.builder()
                .id(1)
                .book(Book.builder()
                        .id(1)
                        .title("White magic")
                        .author(author)
                        .genre(genre)
                        .build())
                .comment(comment)
                .build();

        Comment actual = commentRepositoryJpa.findByComment(comment);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("finding by Id works")
    @Test
    void findById() {
        Comment actual = commentRepositoryJpa.findById(1);
        Comment expected = testEntityManager.find(Comment.class, 1);

        assertThat(actual).isExactlyInstanceOf(Comment.class);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("saving comment works")
    @Test
    void save() {
        Author author = Author.builder()
                .name("Name1")
                .build();

        Genre genre = Genre.builder()
                .genre("comedy")
                .build();

        Book book = Book.builder()
                .title("TestBook")
                .genre(genre)
                .author(author)
                .build();

        Comment comment = Comment.builder().comment("good")
                .book(book)
                .build();

        val actual = commentRepositoryJpa.save(comment);

        assertThat(actual).usingRecursiveComparison().isEqualTo(comment);
    }

    @DisplayName("find all available instances")
    @Test
    void findAll() {
        List<Comment> commentList = commentRepositoryJpa.findAll();

        assertEquals(commentList.size(), 1);
    }

    @DisplayName("delete instance of comment by id")
    @Test
    void deleteById() {
        Author author = Author.builder()
                .name("Name1")
                .build();

        Genre genre = Genre.builder()
                .genre("comedy")
                .build();

        Book book = Book.builder()
                .title("TestBook")
                .genre(genre)
                .author(author)
                .build();

        Comment comment = Comment.builder().comment("good")
                .book(book)
                .build();

        // adding new comment
        val actual = commentRepositoryJpa.save(comment);
        int actualId = actual.getId();

        // approve we have added  new comment
        val addedComment = commentRepositoryJpa.findById(actualId);

        // performing delete operation
        commentRepositoryJpa.deleteById(actualId);
        assertThat(testEntityManager.find(Comment.class, actualId)).isNull();
    }

    @DisplayName("find all comments by book id")
    @Test
    void findAllByBookId() {
        SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val commentList = commentRepositoryJpa.findAllByBookId(1);

        assertThat(commentList.size()).isEqualTo(1);
        assertThat(sessionFactory.getStatistics()
                .getPrepareStatementCount()).isEqualTo(1);
    }
}