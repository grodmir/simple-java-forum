package org.example.simplejavaforum.service;

import org.example.simplejavaforum.dao.CommentDao;
import org.example.simplejavaforum.model.Comment;

import java.util.List;

public class CommentService {
    private CommentDao commentDao = new CommentDao();

    public void save(Comment comment) {
        commentDao.save(comment);
    }

    public List<Comment> findAllByTopicId(Long topicId) {
        return commentDao.findByTopicId(topicId);
    }
}
