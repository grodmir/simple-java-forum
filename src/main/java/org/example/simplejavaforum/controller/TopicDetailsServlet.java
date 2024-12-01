package org.example.simplejavaforum.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.simplejavaforum.model.Topic;
import org.example.simplejavaforum.service.TopicService;

import java.io.IOException;

@WebServlet("/home/topic")
public class TopicDetailsServlet extends HttpServlet {
    private TopicService topicService = new TopicService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam  = req.getParameter("id");
        if (idParam  == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID темы не указан");
            return;
        }

        try {
            int topicId = Integer.parseInt(idParam);
            Topic topic = topicService.getTopicById(topicId);

            if (topic == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Тема не найдена");
                return;
            }

            req.setAttribute("topic", topic);
            req.getRequestDispatcher("/WEB-INF/views/topic.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректный ID темы");
        }
    }
}
