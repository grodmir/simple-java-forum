package org.example.simplejavaforum.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.simplejavaforum.model.Comment;
import org.example.simplejavaforum.model.Topic;
import org.example.simplejavaforum.service.CommentService;
import org.example.simplejavaforum.service.TopicService;

import java.io.IOException;
import java.util.List;

@WebServlet("/home/topic")
public class TopicDetailsServlet extends HttpServlet {
    private TopicService topicService = new TopicService();
    private CommentService commentService = new CommentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam  = req.getParameter("id");
        if (idParam  == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID темы не указан");
            return;
        }

        try {
            Long topicId = Long.parseLong(idParam);
            Topic topic = topicService.getTopicById(topicId);
            List<Comment> comments = commentService.findAllByTopicId(topicId);

            if (topic == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Тема не найдена");
                return;
            }

            req.setAttribute("topic", topic);
            req.setAttribute("comments", comments);
            req.getRequestDispatcher("/WEB-INF/views/topic.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректный ID темы");
        }
    }
}
