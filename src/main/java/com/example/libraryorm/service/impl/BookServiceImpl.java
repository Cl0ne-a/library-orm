package com.example.libraryorm.service.impl;

import com.example.libraryorm.entities.Book;
import com.example.libraryorm.entities.Comment;
import com.example.libraryorm.exceptions.BookPersistingException;
import com.example.libraryorm.repository.BookRepository;
import com.example.libraryorm.service.BookService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    @Override
    public Book update(int id, String newTitle) {
        Book bookToUpdate = bookRepository.findById(id);
        bookToUpdate.setTitle(newTitle);
        return bookToUpdate;
    }

    @Transactional
    @Override
    public boolean removeCommentById(int bookId, int commentId) {
        val updateRemovedStatus = bookRepository.findAllCommentsById(bookId)
                .stream()
                .filter(comment -> comment.getId() == commentId)
                .map(comment -> {
                    comment.setRemoved(true);
                    return comment.isRemoved();
                }).findFirst();

        return updateRemovedStatus.isPresent();
    }

    @Transactional
    @Override
    public boolean addComment(int bookId, String feedback) {
        val bookComments = bookRepository.findById(bookId).getComments();
        Comment comment = Comment.builder().comment(feedback).build();
        return bookComments.add(comment);
    }

    @Transactional
    @Override
    public Book addBook(Book newBook) throws BookPersistingException {

        if(bookRepository.findById(newBook.getId()) == null) {
            return bookRepository.save(newBook);
        } else {
            throw new BookPersistingException("book already exists");
        }
    }

    @Override
    public List<Comment> findAllComments(int bookId) {
        return bookRepository
                .findAllCommentsById(bookId).stream()
                .filter(comment -> !comment.isRemoved())
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository
                .findAll()
                .stream()
                .filter(book -> {
                    var list = book.getComments()
                            .stream().filter(c -> !c.isRemoved())
                            .collect(Collectors.toList());
                    book.getComments().retainAll(list);
                    return !book.isRemoved();
                })
                .collect(Collectors.toList());
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

    @Transactional
    @Override
    public boolean deleteById(int id) throws BookPersistingException {
        if(null != bookRepository.findById(id)) {
            Book book = bookRepository.findById(id);
            book.setRemoved(true);
            return book.isRemoved();
        } else {
            throw new BookPersistingException("no book present by that id");
        }
    }
}
