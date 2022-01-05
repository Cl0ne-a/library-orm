package com.example.libraryorm.service;

import com.example.libraryorm.entities.Book;
import com.example.libraryorm.exceptions.BookAlreadyExistException;
import com.example.libraryorm.exceptions.BookNotFoundException;
import com.example.libraryorm.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book addBook(Book book) throws BookAlreadyExistException {
        if (bookRepository.notExist(book)) {
            return bookRepository.save(book);
        } else {
            throw new BookAlreadyExistException("book already exists");
        }
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(int id) throws BookNotFoundException {
       if(bookRepository.present(id)) {
           return bookRepository.findById(id);
       } else {
           throw new BookNotFoundException("no book present by that id");
       }
    }

    @Override
    public Book findByTitle(String title) throws BookNotFoundException {
        if (bookRepository.present(title)) {
            return bookRepository.findByTitle(title);
        } else {
            throw new BookNotFoundException("no book present by that id");
        }
    }

    @Override
    public void updateTitleById(int id, String title) throws BookNotFoundException {
        if(bookRepository.present(id)) {
            bookRepository.updateTitleById(id, title);
        } else {
            throw new BookNotFoundException("no book present by that id");
        }
    }

    @Override
    public void deleteById(int id) throws BookNotFoundException {
        if(bookRepository.present(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new BookNotFoundException("no book present by that id");
        }
    }
}
