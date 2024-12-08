package org.example.simplejavaforum.repository;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.example.simplejavaforum.model.Comment;
import org.example.simplejavaforum.util.JpaUtil;

import java.util.List;

@Slf4j
public class CommentRepository {

    public Comment findById(Long id) {
        try (EntityManager em = JpaUtil.getInstance().getEntityManager()) {
            return em.createQuery(
                            "SELECT c FROM Comment c JOIN FETCH c.topic JOIN FETCH c.author WHERE c.id = :id",
                            Comment.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }
    }

    public List<Comment> findAll() {
        try (EntityManager em = JpaUtil.getInstance().getEntityManager()) {
            return em.createQuery(
                            "SELECT c FROM Comment c JOIN FETCH c.topic JOIN FETCH c.author ORDER BY c.createdAt DESC",
                            Comment.class)
                    .getResultList();
        }
    }

    public List<Comment> findByTopicId(Long topicId) {
        try (EntityManager em = JpaUtil.getInstance().getEntityManager()) {
            return em.createQuery(
                            "SELECT c FROM Comment c JOIN FETCH c.topic JOIN FETCH c.author WHERE c.topic.id = :topicId ORDER BY c.createdAt ASC",
                            Comment.class)
                    .setParameter("topicId", topicId)
                    .getResultList();
        }
    }

    public List<Comment> findByAuthorId(Long authorId) {
        try (EntityManager em = JpaUtil.getInstance().getEntityManager()) {
            return em.createQuery(
                            "SELECT c FROM Comment c JOIN FETCH c.topic JOIN FETCH c.author WHERE c.author.id = :userId ORDER BY c.createdAt ASC",
                            Comment.class)
                    .setParameter("userId", authorId)
                    .getResultList();
        }
    }

    public void save(Comment comment) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            if (comment.getId() == null) {
                em.persist(comment);
                log.info("Comment created: {}", comment);
            } else {
                em.merge(comment);
                log.info("Comment updated: {}", comment);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            log.error("Error saving comment: {}", comment, e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            Comment comment = em.find(Comment.class, id);
            if (comment != null) {
                em.remove(comment);
                log.info("Comment deleted: {}", comment);
            } else {
                log.warn("Comment not found: {}", id);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            log.error("Error deleting comment: {}", id, e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
