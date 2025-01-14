package org.example.simplejavaforum.service;

import lombok.extern.slf4j.Slf4j;
import org.example.simplejavaforum.model.Topic;
import org.example.simplejavaforum.model.TopicVote;
import org.example.simplejavaforum.repository.TopicRepository;
import org.example.simplejavaforum.repository.TopicVoteRepository;
import org.example.simplejavaforum.repository.UserRepository;

@Slf4j
public class TopicVoteService {

    private final TopicVoteRepository topicVoteRepository = TopicVoteRepository.getInstance();
    private final TopicRepository topicRepository = TopicRepository.getInstance();
    private final UserRepository userRepository = UserRepository.getInstance();

    public boolean hasUserVotedForTopic(Long userId, Long topicId) {
        return topicVoteRepository.findByUserAndTopic(userId, topicId) != null;
    }

    public void vote(Long userId, Long topicId, String voteType) {
        if (hasUserVotedForTopic(userId, topicId)) {
            log.info("The user has already rated topic");
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
            log.info("Like topic");
            topic.setLikes(topic.getLikes() + 1);
        } else if ("dislike".equals(voteType)) {
            log.info("Dislike topic");
            topic.setDislikes(topic.getDislikes() + 1);
        }

        // Сохраняем изменения в топике
        topicRepository.update(topic);
    }
}
