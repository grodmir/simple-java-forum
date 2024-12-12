package org.example.simplejavaforum.service;

import org.example.simplejavaforum.repository.TopicRepository;
import org.example.simplejavaforum.model.Topic;

import java.util.List;

public class TopicService {
    private final TopicRepository topicRepository = new TopicRepository();

    public List<Topic> getTopicsSortedBy(String sort, int page, int pageSize) {
        switch (sort) {
            case "likes":
                return topicRepository.findSortedByLikesWithPagination(page, pageSize);
            case "dislikes":
                return topicRepository.findSortedByDislikesWithPagination(page, pageSize);
            case "data":
                return topicRepository.findSortedByDateWithPagination(page, pageSize);
            default:
                return topicRepository.findSortedByDateWithPagination(page, pageSize);
        }
    }

    public int getTopicsCount() {
        return topicRepository.getTopicCount();
    }

    public Topic getTopicById(Long id) {
        return topicRepository.getTopicById(id);
    }

    public void save(Topic topic) {
        topicRepository.save(topic);
    }

    public void update(Topic topic) {
        topicRepository.update(topic);
    }
}
