package com.example.libraryorm.service;

import com.example.libraryorm.exceptions.BookAlreadyExistException;
import com.example.libraryorm.entities.Author;
import com.example.libraryorm.entities.Book;
import com.example.libraryorm.entities.Genre;
import com.example.libraryorm.exceptions.BookNotFoundException;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BookServiceImplTest {

    private final BookServiceImpl bookService;

    @Autowired
    BookServiceImplTest(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

//    void updateTitleById(int id, String title) throws BookNotFoundException;

//    void deleteById(int id) throws BookNotFoundException;

    @Test
    void findById() throws BookNotFoundException {
        int id = 1;
        String title = "White magic";
        Author author = Author.builder().id(1).name("Mr.Pool").build();
        Genre genre = Genre.builder().id(1).genre("comedy").build();
        Book actual = bookService.findById(id);

        Book expected = Book.builder()
                .id(1)
                .title(title)
                .author(author)
                .genre(genre)
                .build();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findByTitle() throws BookNotFoundException {
        String title = "White magic";
        Author author = Author.builder().id(1).name("Mr.Pool").build();
        Genre genre = Genre.builder().id(1).genre("comedy").build();
        Book actual = bookService.findByTitle(title);

        Book expected = Book.builder()
                .id(1)
                .title(title)
                .author(author)
                .genre(genre)
                .build();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findAll() {
        int firstIdUnderTest = 1;
        int secondIdUnderTest = 2;
        int thirdIdUnderTest = 3;
        List<Book> actual = bookService.findAll();
        List<Book> expected = List.of(
                Book.builder()
                        .id(firstIdUnderTest)
                        .title("White magic")
                        .author(Author.builder().id(firstIdUnderTest).name("Mr.Pool").build())
                        .genre(Genre.builder().id(firstIdUnderTest).genre("comedy").build())
                        .build(),
                Book.builder()
                        .id(secondIdUnderTest)
                        .title("Black magic")
                        .author(Author.builder().id(secondIdUnderTest).name("Mr. Smith").build())
                        .genre(Genre.builder().id(secondIdUnderTest).genre("horror").build())
                        .build(),
                Book.builder()
                        .id(thirdIdUnderTest)
                        .title("Test Book")
                        .author(Author.builder().id(secondIdUnderTest).name("Mr. Smith").build())
                        .genre(Genre.builder().id(firstIdUnderTest).genre("comedy").build())
                        .build()
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void addBook() throws BookAlreadyExistException {
        Book book = Book.builder()
                .title("TestBook")
                .author(Author.builder().name("Test author").build())
                .genre(Genre.builder().genre("Horror").build())
                .build();
        val actual = bookService.addBook(book);

        assertThat(actual).isEqualTo(book);
    }

    @Test
    void addBookThrowsException() {
        Book book = Book.builder()
                .title("Test Book")
                .author(Author.builder().name("Test author").build())
                .genre(Genre.builder().genre("Horror").build())
                .build();

        assertThrows(BookAlreadyExistException.class,
                () -> bookService.addBook(book));
    }
}