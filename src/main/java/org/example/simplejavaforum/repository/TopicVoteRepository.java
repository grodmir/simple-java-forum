package org.example.simplejavaforum.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.simplejavaforum.model.TopicVote;
import org.example.simplejavaforum.util.HibernateUtil;
import org.hibernate.Session;

@Slf4j
public class TopicVoteRepository extends AbstractHibernateRepository<TopicVote> implements TopicVoteRepositoryInterface {

    private static TopicVoteRepository instance;

    private TopicVoteRepository() {
        super(TopicVote.class);
    }

    public static synchronized TopicVoteRepository getInstance() {
        if (instance == null) {
            instance = new TopicVoteRepository();
        }
        return instance;
    }

    @Override
    public TopicVote findByUserAndTopic(Long userId, Long topicId) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM TopicVote WHERE user.id = :userId AND topic.id = :topicId", TopicVote.class)
                    .setParameter("userId", userId)
                    .setParameter("topicId", topicId)
                    .uniqueResult();
        } catch (Exception e) {
            log.error("Error finding vote by user {} and topic {}: {}", userId, topicId, e.getMessage());
            return null;
        }
    }
}
