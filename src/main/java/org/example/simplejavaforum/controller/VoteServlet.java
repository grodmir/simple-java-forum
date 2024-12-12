package org.example.simplejavaforum.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.simplejavaforum.model.User;
import org.example.simplejavaforum.service.TopicVoteService;

import java.io.IOException;

@Slf4j
@WebServlet("/home/topic/vote")
public class VoteServlet extends HttpServlet {
    private TopicVoteService topicVoteService = new TopicVoteService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String topicIdParam = req.getParameter("id");
        String voteType = req.getParameter("vote");

        if (topicIdParam == null || voteType == null) {
            log.error("id or voteType is null");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            Long topicId = Long.parseLong(topicIdParam);

            topicVoteService.vote(user.getId(), topicId, voteType);

            log.info("User {} successfully voted {} for topic {}", user.getId(), voteType, topicId);
            resp.sendRedirect(req.getContextPath() + "/home/topic?id=" + topicId);
        } catch (IllegalStateException e) {
            log.warn("User {} tried to vote multiple times for topic {}", user.getId(), topicIdParam);
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (NumberFormatException e) {
            log.error("Invalid topic ID: {}", topicIdParam, e);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid topic ID.");
        } catch (Exception e) {
            log.error("Unexpected error while processing vote", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
        }
    }
}
