package com.example.libraryorm;

import com.example.libraryorm.exceptions.BookPersistingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryOrmApplication {

    public static void main(String[] args) throws BookPersistingException {
        SpringApplication.run(LibraryOrmApplication.class, args);
    }
}
