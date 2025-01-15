package org.example.simplejavaforum.repository;

import org.example.simplejavaforum.model.Comment;

import java.util.List;

public interface CommentRepositoryInterface {
    Comment findById(Long id);
    List<Comment> findAll();
    List<Comment> findByTopicId(Long topicId);
    List<Comment> findByAuthorId(Long authorId);
    void save(Comment comment);
    void update(Comment comment);
    void delete(Long id);
}
