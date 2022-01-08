package com.example.libraryorm.service;

import com.example.libraryorm.entities.Book;
import com.example.libraryorm.exceptions.BookPersistingException;
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
    public Book addBook(Book book) throws BookPersistingException {
        if(bookRepository.findById(book.getId()) == null) {
            return bookRepository.save(book);
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
       if(bookRepository.present(id)) {
           return bookRepository.findById(id);
       } else {
           throw new BookPersistingException("no book present by that id");
       }
    }

    @Override
    public Book findByTitle(String title) throws BookPersistingException {
        if (bookRepository.present(title)) {
            return bookRepository.findByTitle(title);
        } else {
            throw new BookPersistingException("no book present by that name");
        }
    }

    @Override
    public void updateTitleById(int id, String title) throws BookPersistingException {
        if(bookRepository.present(id)) {
            bookRepository.updateTitleById(id, title);
        } else {
            throw new BookPersistingException("no book present by that id");
        }
    }

    @Override
    public void deleteById(int id) throws BookPersistingException {
        if(bookRepository.findById(id) != null) {
            bookRepository.deleteById(id);
        } else {
            throw new BookPersistingException("no book present by that id");
        }
    }
}
