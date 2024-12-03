package org.example.simplejavaforum.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.simplejavaforum.model.Topic;
import org.example.simplejavaforum.model.User;
import org.example.simplejavaforum.service.TopicService;
import org.example.simplejavaforum.service.UserService;

import java.io.IOException;

@Slf4j
@WebServlet("/home/save-topic")
public class SaveTopicServlet extends HttpServlet {
    private TopicService topicService = new TopicService();
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8"); // Поддержка кириллицы
        String title = req.getParameter("title");
        String content = req.getParameter("description");

        if (title == null || content == null || title.isEmpty() || content.isEmpty()) {
            log.warn("title and content are empty");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        User user = userService.getUserById(1L);
        System.out.println("user = " + user);
        Topic topic = new Topic(title, content, user);

        try {
            topicService.save(topic);
            log.info("saved topic {}", topic);
            resp.sendRedirect(req.getContextPath() + "/home");
        } catch (IOException e) {
            log.error("Ошибка сохранения топика: {}", e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка сохранения топика");
        }
    }
}
