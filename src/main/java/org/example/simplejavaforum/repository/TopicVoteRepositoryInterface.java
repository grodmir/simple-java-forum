package org.example.simplejavaforum.repository;

import org.example.simplejavaforum.model.TopicVote;

public interface TopicVoteRepositoryInterface {
    TopicVote findByUserAndTopic(Long userId, Long topicId);
    void save(TopicVote vote);
    void update(TopicVote vote);
    void delete(Long id);
}
