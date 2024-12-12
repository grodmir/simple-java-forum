package org.example.simplejavaforum.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.simplejavaforum.model.Comment;
import org.example.simplejavaforum.model.Topic;
import org.example.simplejavaforum.model.User;
import org.example.simplejavaforum.service.CommentService;
import org.example.simplejavaforum.service.TopicService;
import org.example.simplejavaforum.service.UserService;

import java.io.IOException;

@Slf4j
@WebServlet("/home/topic/comment")
public class AddCommentServlet extends HttpServlet {
    private final CommentService commentService = new CommentService();
    private final TopicService topicService = new TopicService();
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);

        String content = req.getParameter("content");
        String topicIdParam  = req.getParameter("topicId");

        if (content == null || content.isEmpty() || topicIdParam == null) {
            log.warn("Invalid input for comment creation");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (session == null) {
            log.warn("Invalid session");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            Long topicId = Long.valueOf(topicIdParam);
            Topic topic = topicService.getTopicById(topicId);
            User author = (User) session.getAttribute("user");

            Comment comment = new Comment(topic, author, content);
            commentService.save(comment);

            log.info("Comment saved successfully: {}", comment);
            resp.sendRedirect(req.getContextPath() + "/home/topic?id=" + topicId);
        } catch (Exception e) {
            log.error("Error saving comment: {}", e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error saving comment");
        }
    }
}
