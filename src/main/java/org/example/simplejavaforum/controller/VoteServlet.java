package org.example.simplejavaforum.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.simplejavaforum.model.Topic;
import org.example.simplejavaforum.service.TopicService;

import java.io.IOException;

@Slf4j
@WebServlet("/home/topic/vote")
public class VoteServlet extends HttpServlet {
    private TopicService topicService = new TopicService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        String voteType = req.getParameter("vote");

        if (idParam == null || voteType == null) {
            log.error("id or voteType is null");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            int topicId = Integer.parseInt(idParam);
            Topic topic = topicService.getTopicById(topicId);

            if (topic == null) {
                log.error("topic not found");
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            if ("like".equals(voteType)) {
                topic.setLikes(topic.getLikes() + 1);
            } else if ("dislike".equals(voteType)) {
                topic.setDislikes(topic.getDislikes() + 1);
            } else {
                log.error("voteType not supported");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            topicService.save(topic);
            resp.sendRedirect(req.getContextPath() + "/home/topic?id=" + topicId);
        } catch (NumberFormatException e) {
            log.error("id or voteType is null");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
