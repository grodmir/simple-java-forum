package org.example.simplejavaforum.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.simplejavaforum.model.User;
import org.example.simplejavaforum.service.UserService;

import java.io.IOException;

@Slf4j
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");
        String username  = req.getParameter("username");
        log.info("Username : " + username);
        log.info("Password : " + password);

        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            log.error("Username and password are required");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Имя пользователя и пароль обязательны");
            return;
        }

        if (userService.isUsernameTaken(username)) {
            log.error("Username already taken");
            req.setAttribute("error", "Имя пользователя уже занято");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }

        String passwordHash = userService.hashPassword(password);
        User newUser = new User(username, passwordHash, "user");

        userService.save(newUser);

        resp.sendRedirect(req.getContextPath() + "/login"); // Перенаправление на страницу входа
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }
}
