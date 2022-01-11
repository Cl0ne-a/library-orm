package com.example.libraryorm.service.impl;

import com.example.libraryorm.entities.Book;
import com.example.libraryorm.exceptions.BookPersistingException;
import com.example.libraryorm.repository.BookRepository;
import com.example.libraryorm.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book addBook(Book newBook) throws BookPersistingException {

        if(bookRepository.findById(newBook.getId()) == null) {
            return bookRepository.save(newBook);
        } else {
            throw new BookPersistingException("book already exists");
        }
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(int id) throws BookPersistingException {
       if (null!=bookRepository.findById(id)) {
           return bookRepository.findById(id);
       } else {
           throw new BookPersistingException("no book present by that id");
       }
    }

    @Override
    public Book findByTitle(String title) throws BookPersistingException {
        if (null != bookRepository.findByTitle(title)) {
            return bookRepository.findByTitle(title);
        } else {
            throw new BookPersistingException("no book present by that name");
        }
    }

    @Override
    public void deleteById(int id) throws BookPersistingException {
        if(null != bookRepository.findById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new BookPersistingException("no book present by that id");
        }
    }
}
