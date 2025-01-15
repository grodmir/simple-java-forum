package org.example.simplejavaforum.service;

import org.example.simplejavaforum.model.Comment;
import org.example.simplejavaforum.repository.CommentRepository;

import java.util.List;

public class CommentService {

    private CommentRepository commentRepository = CommentRepository.getInstance();

    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Comment> findAllByTopicId(Long topicId) {
        return commentRepository.findByTopicId(topicId);
    }
}
