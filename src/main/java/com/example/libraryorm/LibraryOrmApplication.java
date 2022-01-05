package com.example.libraryorm;

import com.example.libraryorm.entities.Author;
import com.example.libraryorm.entities.Book;
import com.example.libraryorm.entities.Comment;
import com.example.libraryorm.entities.Genre;
import com.example.libraryorm.repository.BookRepository;
import com.example.libraryorm.repository.CommentRepository;
import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryOrmApplication {

    public static void main(String[] args) {
        var ctx = SpringApplication.run(LibraryOrmApplication.class, args);
        val bookRepo = ctx.getBean(BookRepository.class);
        val commentRepo = ctx.getBean(CommentRepository.class);
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

        System.out.println(commentRepo.save(comment));
        System.out.println(commentRepo.findByComment("good"));
    }
}
