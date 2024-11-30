package org.example.simplejavaforum.dao;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.example.simplejavaforum.model.Topic;
import org.example.simplejavaforum.util.JpaUtil;

import java.util.List;

@Slf4j
public class TopicDao {

    public Topic getTopicById(int id) {
        try (EntityManager em = JpaUtil.getInstance().getEntityManager()) {
            return em.find(Topic.class, id);
        }
    }

    public List<Topic> findAll() {
        try (EntityManager em = JpaUtil.getInstance().getEntityManager()) {
            return em.createQuery("SELECT t FROM Topic t ORDER BY t.createdAt DESC", Topic.class)
                    .getResultList();
        }
    }

    public List<Topic> findByTitle(String title) {
        try (EntityManager em = JpaUtil.getInstance().getEntityManager()) {
            return em.createQuery("SELECT t FROM Topic t WHERE LOWER(t.title) LIKE :title ORDER BY t.createdAt DESC", Topic.class)
                    .setParameter("title", "%" + title.toLowerCase() + "%")
                    .getResultList();
        }
    }

    public List<Topic> findMostLiked(int limit) {
        try (EntityManager em = JpaUtil.getInstance().getEntityManager()) {
            return em.createQuery("SELECT t FROM Topic t ORDER BY t.likes DESC", Topic.class)
                    .setMaxResults(limit)
                    .getResultList();
        }
    }

    public void save(Topic topic) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            if (topic.getId() == null) {
                em.persist(topic);
                log.info("Topic created: {}", topic);
            } else {
                em.merge(topic);
                log.info("Topic updated: {}", topic);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            log.error("Error saving topic: {}", e.getMessage());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            Topic topic = em.find(Topic.class, id);
            if (topic != null) {
                em.remove(topic);
                log.info("Topic deleted: {}", topic);
            } else {
                log.warn("Topic not found with id: {}", id);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            log.error("Error deleting topic: {}", e.getMessage());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
