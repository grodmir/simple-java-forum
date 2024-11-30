package org.example.simplejavaforum.dao;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.example.simplejavaforum.model.Notification;
import org.example.simplejavaforum.util.JpaUtil;

import java.util.List;

@Slf4j
public class NotificationDao {

    public Notification findById(Long id) {
        try (EntityManager em = JpaUtil.getInstance().getEntityManager()) {
            return em.find(Notification.class, id);
        }
    }

    public List<Notification> findAll() {
        try (EntityManager em = JpaUtil.getInstance().getEntityManager()) {
            return em.createQuery("SELECT n FROM Notification n ORDER BY n.createdAt DESC", Notification.class)
                    .getResultList();
        }
    }

    public List<Notification> findByTopicId (Long topicId) {
        try (EntityManager em = JpaUtil.getInstance().getEntityManager()) {
            return em.createQuery("SELECT n FROM Notification n WHERE n.topic.id = :topicId ORDER BY n.createdAt DESC", Notification.class)
                    .setParameter("topicId", topicId)
                    .getResultList();
        }
    }

    public void save(Notification notification) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            if (notification.getId() == null) {
                em.persist(notification);
                log.info("Notification created: {}", notification);
            } else {
                em.merge(notification);
                log.info("Notification updated: {}", notification);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            log.error("Error saving notification: {}", notification, e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            Notification notification = em.find(Notification.class, id);
            if (notification != null) {
                em.remove(notification);
                log.info("Notification deleted: {}", notification);
            } else {
                log.warn("Notification not found: {}", id);
            }
        } catch (Exception e) {
            log.error("Error deleting notification: {}", id, e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
