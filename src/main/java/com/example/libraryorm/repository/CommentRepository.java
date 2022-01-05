package com.example.libraryorm.repository;

import com.example.libraryorm.entities.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);

    Optional<Comment> findById(int id);

    Comment findByComment(String title);

    void updateTitleById(int id, String title);

    List<Comment> findAll();

    void deleteById(int id);
}
