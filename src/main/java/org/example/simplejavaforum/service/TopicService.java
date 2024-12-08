package org.example.simplejavaforum.service;

import org.example.simplejavaforum.repository.TopicRepository;
import org.example.simplejavaforum.model.Topic;

import java.util.List;

public class TopicService {
    private final TopicRepository topicDao = new TopicRepository();

    public List<Topic> getTopicsSortedBy(String sort, int page, int pageSize) {
        switch (sort) {
            case "likes":
                return topicDao.findSortedByLikesWithPagination(page, pageSize);
            case "dislikes":
                return topicDao.findSortedByDislikesWithPagination(page, pageSize);
            case "data":
                return topicDao.findSortedByDateWithPagination(page, pageSize);
            default:
                return topicDao.findSortedByDateWithPagination(page, pageSize);
        }
    }

    public int getTopicsCount() {
        return topicDao.getTopicCount();
    }

    public Topic getTopicById(Long id) {
        return topicDao.getTopicById(id);
    }

    public void save(Topic topic) {
        topicDao.save(topic);
    }

    public void update(Topic topic) {
        topicDao.update(topic);
    }
}
