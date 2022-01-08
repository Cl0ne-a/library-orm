package com.example.libraryorm;

import com.example.libraryorm.entities.Book;
import com.example.libraryorm.exceptions.BookPersistingException;
import com.example.libraryorm.service.BookService;
import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryOrmApplication {

    public static void main(String[] args) throws BookPersistingException {
        var ctx = SpringApplication.run(LibraryOrmApplication.class, args);
        val service = ctx.getBean(BookService.class);
        Book book = service.addBook(Book.builder().build());
        service.findAll().forEach(System.out::println);
    }
}
