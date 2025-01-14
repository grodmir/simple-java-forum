package org.example.simplejavaforum.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.simplejavaforum.model.TopicVote;
import org.example.simplejavaforum.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Slf4j
public class TopicVoteRepository {

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

    public void save(TopicVote vote) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(vote);
            transaction.commit();
            log.info("Vote saved: User {} voted {} for Topic {}",
                    vote.getUser().getId(), vote.getVoteType(), vote.getTopic().getId());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Error saving vote: {}", e.getMessage(), e);
        }
    }

    public void delete(TopicVote vote) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(vote);
            transaction.commit();
            log.info("Vote deleted: {}", vote);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Error deleting vote: {}", e.getMessage(), e);
        }
    }
}
