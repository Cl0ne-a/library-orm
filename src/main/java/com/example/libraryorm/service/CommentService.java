package com.example.libraryorm.service;

import com.example.libraryorm.entities.Comment;

import java.util.List;

public interface CommentService {
    Comment addComment(String comment, int bookId);

    List<Comment> findByBookId(int bookId);
}
