package com.example.libraryorm.repository;

import com.example.libraryorm.entities.Comment;

import java.util.List;

public interface CommentRepository {
    Comment save(Comment comment);

    Comment findById(int id);

    Comment findByComment(String comment);

    void updateCommentById(int id, String comment);

    List<Comment> findAll();

    void deleteById(int id);

    List<Comment> findAllByBookId(int id);
}
