package com.example.libraryorm.repository;

import com.example.libraryorm.entities.Comment;

public interface CommentRepository {
    Comment save(Comment comment);

    Comment findById(int id);

    Comment findByComment(String comment);
}
