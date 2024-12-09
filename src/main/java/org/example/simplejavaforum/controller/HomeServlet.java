package org.example.simplejavaforum.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.simplejavaforum.model.Topic;
import org.example.simplejavaforum.model.User;
import org.example.simplejavaforum.service.TopicService;

import java.io.IOException;
import java.util.List;

@Slf4j
@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private final TopicService topicService = new TopicService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sort = req.getParameter("sort");
        if (sort == null) {
            sort = "date";
        }

        String pageParam = req.getParameter("page");
        int page = pageParam != null ? Integer.parseInt(pageParam) : 1;
        int pageSize = 5;

        List<Topic> topics = topicService.getTopicsSortedBy(sort, page, pageSize);

        int totalTopics = topicService.getTopicsCount();
        boolean hasNextPage = page * pageSize < totalTopics;

        /* Тестовая часть для проверки работоспособности авторизации */
        HttpSession session = req.getSession(false); // Не создаем новую сессию
        String username = "";

        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                username = user.getUsername(); // Получаем имя пользователя
            }
        }
        req.setAttribute("username", username);

        req.setAttribute("topics", topics);
        req.setAttribute("currentPage", page);
        req.setAttribute("hasNextPage", hasNextPage);

        req.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(req, resp);
    }
}
