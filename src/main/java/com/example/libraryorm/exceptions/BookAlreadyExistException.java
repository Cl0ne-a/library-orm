package com.example.libraryorm.exceptions;

public class BookAlreadyExistException extends Exception{
    public BookAlreadyExistException(String message) {
        super(message);
    }
}