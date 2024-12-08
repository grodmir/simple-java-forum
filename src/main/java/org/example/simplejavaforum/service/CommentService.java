package org.example.simplejavaforum.service;

import org.example.simplejavaforum.repository.CommentRepository;
import org.example.simplejavaforum.model.Comment;

import java.util.List;

public class CommentService {
    private CommentRepository commentDao = new CommentRepository();

    public void save(Comment comment) {
        commentDao.save(comment);
    }

    public List<Comment> findAllByTopicId(Long topicId) {
        return commentDao.findByTopicId(topicId);
    }
}
