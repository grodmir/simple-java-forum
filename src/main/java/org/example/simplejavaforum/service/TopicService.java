package org.example.simplejavaforum.service;

import org.example.simplejavaforum.dao.TopicDao;
import org.example.simplejavaforum.model.Topic;

import java.util.List;

public class TopicService {
    private final TopicDao topicDao = new TopicDao();

    public List<Topic> getTopicsSortedBy(String sort, int page, int pageSize) {
        switch (sort) {
            case "likes":
                return topicDao.findSortedByPopularityWithPagination(page, pageSize);
            case "data":
                return topicDao.findSortedByDateWithPagination(page, pageSize);
            default:
                return topicDao.findSortedByDateWithPagination(page, pageSize);
        }
    }

    public int getTopicsCount() {
        return topicDao.getTopicCount();
    }
}
