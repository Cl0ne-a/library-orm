package com.example.libraryorm.service.impl;

import com.example.libraryorm.entities.Book;
import com.example.libraryorm.entities.Comment;
import com.example.libraryorm.repository.BookRepository;
import com.example.libraryorm.repository.CommentRepository;
import com.example.libraryorm.service.CommentService;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Comment addComment(String comment, int bookId) {
        Book book = bookRepository.findById(bookId);
        Comment newComment = Comment.builder().comment(comment).book(book).build();
        return commentRepository.save(newComment);
    }

    @Override
    public List<Comment> findByBookId(int bookId) {
        return commentRepository.findAllByBookId(bookId);
    }
}
