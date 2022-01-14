package com.example.libraryorm.repository;

import com.example.libraryorm.entities.Comment;

public interface CommentRepository {

    Comment findById(int id);
}
