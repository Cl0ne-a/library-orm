package com.example.libraryorm.service;

import com.example.libraryorm.entities.Author;
import com.example.libraryorm.entities.Book;
import com.example.libraryorm.entities.Genre;
import com.example.libraryorm.exceptions.BookPersistingException;
import com.example.libraryorm.repository.BookRepository;
import com.example.libraryorm.repository.CommentRepository;
import com.example.libraryorm.service.impl.BookServiceImpl;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookServiceImplTest {

    private static final BookRepository bookRepository = mock(BookRepository.class);

    private static BookServiceImpl bookService;


    @Configuration
    static class ServiceConfiguration {

        @Bean
        BookServiceImpl bookService() {
            bookService = new BookServiceImpl(bookRepository);
            return bookService;
        }
    }

    @DisplayName("throws exception in case book by the id already exists")
    @Test
    void addBookThrowsException() {
        Book book = Book.builder().id(1).build();

        when(bookRepository.findById(1)).thenReturn(book);

        assertThrows(BookPersistingException.class,
                () -> bookService.addBook(book));
    }

    @DisplayName("finds by id exact instances")
    @Test
    void findById() throws BookPersistingException {
        int id = 1;
        String title = "White magic";
        Author author = Author.builder().id(1).name("Mr.Pool").build();
        Genre genre = Genre.builder().id(1).genre("comedy").build();

        Book expected = Book.builder()
                .id(1)
                .title(title)
                .author(author)
                .genre(genre)
                .build();
        when(bookRepository.findById(id)).thenReturn(expected);

        val actual = bookService.findById(id);

        verify(bookRepository, times(2)).findById(id);
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("finds by title exact instances")
    @Test
    void findByTitle() throws BookPersistingException {
        int id = 1;
        String title = "White magic";
        Author author = Author.builder().id(1).name("Mr.Pool").build();
        Genre genre = Genre.builder().id(1).genre("comedy").build();

        Book expected = Book.builder()
                .id(id)
                .title(title)
                .author(author)
                .genre(genre)
                .build();
        when(bookRepository.findByTitle(title)).thenReturn(expected);

        val actual = bookService.findByTitle(title);

        verify(bookRepository, times(2)).findByTitle(title);
        assertThat(actual).isEqualTo(expected);
    }


    @DisplayName("deletes by id correct instance")
    @Test
    void deleteById() throws BookPersistingException {
        int id = 1;
        BookService mockService = mock(BookServiceImpl.class);

        doNothing().when(mockService).deleteById(id);
        mockService.deleteById(id);

        verify(mockService, times(1)).deleteById(id);
    }

    @DisplayName("successfully addes new book in case it is not yet in db")
    @Test
    public void addBook() throws BookPersistingException {
        Book expected = Book.builder()
                .title("TestBook")
                .author(Author.builder().name("Test author").build())
                .genre(Genre.builder().genre("Horror").build())
                .build();
        when(bookRepository.save(expected)).thenReturn(expected);

        val actual = bookService.addBook(expected);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("returns list of existing instances")
    @Test
    void findAll() {
        int firstIdUnderTest = 1;
        int secondIdUnderTest = 2;
        int thirdIdUnderTest = 3;

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

        when(bookRepository.findAll()).thenReturn(expected);

        val actual = bookService.findAllBooks();

        assertThat(actual).isEqualTo(expected);
    }
}