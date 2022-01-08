package com.example.libraryorm.exceptions;

public class BookPersistingException extends RuntimeException{
    public BookPersistingException(String message) {
        super(message);
    }
}