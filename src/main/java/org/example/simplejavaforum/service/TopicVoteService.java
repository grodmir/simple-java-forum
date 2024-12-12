package org.example.simplejavaforum.service;

import org.example.simplejavaforum.model.Topic;
import org.example.simplejavaforum.model.TopicVote;
import org.example.simplejavaforum.repository.TopicRepository;
import org.example.simplejavaforum.repository.TopicVoteRepository;
import org.example.simplejavaforum.repository.UserRepository;

public class TopicVoteService {

    private final TopicVoteRepository topicVoteRepository = new TopicVoteRepository();
    private final TopicRepository topicRepository = new TopicRepository();
    private final UserRepository userRepository = new UserRepository();

    public boolean hasUserVotedForTopic(Long userId, Long topicId) {
        return topicVoteRepository.findByUserAndTopic(userId, topicId) != null;
    }

    public void vote(Long userId, Long topicId, String voteType) {
        if (hasUserVotedForTopic(userId, topicId)) {
            return;
        }

        TopicVote vote = new TopicVote();

        vote.setVoteType(voteType);
        vote.setUser(userRepository.findById(userId));
        vote.setTopic(topicRepository.getTopicById(topicId));
        vote.setVoteType(voteType);

        topicVoteRepository.save(vote);

        Topic topic = topicRepository.getTopicById(topicId);
        if ("like".equals(voteType)) {
            topic.setLikes(topic.getLikes() + 1);
        } else if ("dislike".equals(voteType)) {
            topic.setDislikes(topic.getDislikes() + 1);
        }

        // Сохраняем изменения в топике
        topicRepository.update(topic);
    }
}
