package org.example.simplejavaforum.repository;

import org.example.simplejavaforum.model.Topic;

import java.util.List;

public interface TopicRepositoryInterface {
    Topic getTopicById(Long id);
    List<Topic> findAllSortedByDate();
    List<Topic> findSortedByDateWithPagination(int page, int pageSize);
    List<Topic> findSortedByLikesWithPagination(int page, int pageSize);
    List<Topic> findSortedByDislikesWithPagination(int page, int pageSize);
    List<Topic> findByTitle(String title);
    List<Topic> findMostLiked(int limit);
    int getTopicCount();
    void save(Topic topic);
    void update(Topic topic);
    void delete(Long id);
}
