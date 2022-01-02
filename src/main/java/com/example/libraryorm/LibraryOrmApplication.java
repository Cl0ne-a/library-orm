package com.example.libraryorm;

import com.example.libraryorm.entities.Author;
import com.example.libraryorm.entities.Book;
import com.example.libraryorm.entities.Comment;
import com.example.libraryorm.entities.Genre;
import com.example.libraryorm.repository.BookRepository;
import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryOrmApplication {

    public static void main(String[] args) {
        var ctx = SpringApplication.run(LibraryOrmApplication.class, args);
        val bookRepo = ctx.getBean(BookRepository.class);

        Author author = Author.builder()
                .name("Name1")
                .build();

        Genre genre = Genre.builder()
                .genre("comedy")
                .build();

        Book book = Book.builder()
                .title("some new book")
                .genre(genre)
                .author(author)
                .build();

        Comment comment = Comment.builder().comment("good")
                .book(book)
                .build();

        bookRepo.save(book);
        System.out.println(bookRepo.findById(1));
    }
}
