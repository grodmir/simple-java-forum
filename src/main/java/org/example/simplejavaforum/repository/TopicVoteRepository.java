package org.example.simplejavaforum.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.example.simplejavaforum.model.TopicVote;
import org.example.simplejavaforum.util.JpaUtil;

@Slf4j
public class TopicVoteRepository {

    public TopicVote findByUserAndTopic(Long userId, Long topicId) {
        try (EntityManager em = JpaUtil.getInstance().getEntityManager()) {
            return em.createQuery("FROM TopicVote WHERE user.id = :userId AND topic.id = :topicId",
                    TopicVote.class)
                    .setParameter("userId", userId)
                    .setParameter("topicId", topicId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void save(TopicVote vote) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(vote);
            em.getTransaction().commit();
            log.info("Vote saved: User {} voted {} for Topic {}", vote.getUser().getId(), vote.getVoteType(), vote.getTopic().getId());
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.error("Error saving vote: {}", e.getMessage());
        } finally {
            em.close();
        }
    }

    public void delete(TopicVote vote) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.contains(vote) ? vote : em.merge(vote));
            em.getTransaction().commit();
            log.info("Vote deleted: {}", vote);
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.error("Error deleting vote: {}", e.getMessage());
        } finally {
            em.close();
        }
    }
}
