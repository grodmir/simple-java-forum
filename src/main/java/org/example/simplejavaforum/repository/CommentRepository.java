package org.example.simplejavaforum.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.simplejavaforum.model.Comment;
import org.example.simplejavaforum.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

@Slf4j
public class CommentRepository {

    private static CommentRepository instance;

    public static synchronized CommentRepository getInstance() {
        if (instance == null) {
            instance = new CommentRepository();
        }
        return instance;
    }

    public Comment findById(Long id) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT c FROM Comment c JOIN FETCH c.topic JOIN FETCH c.author WHERE c.id = :id",
                            Comment.class)
                    .setParameter("id", id)
                    .uniqueResult();
        } catch (Exception e) {
            log.error("Error finding comment by ID: {}", id, e);
            throw e;
        }
    }

    public List<Comment> findAll() {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT c FROM Comment c JOIN FETCH c.topic JOIN FETCH c.author ORDER BY c.createdAt DESC",
                            Comment.class)
                    .list();
        } catch (Exception e) {
            log.error("Error finding all comments", e);
            throw e;
        }
    }

    public List<Comment> findByTopicId(Long topicId) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT c FROM Comment c JOIN FETCH c.topic JOIN FETCH c.author WHERE c.topic.id = :topicId ORDER BY c.createdAt ASC",
                            Comment.class)
                    .setParameter("topicId", topicId)
                    .list();
        } catch (Exception e) {
            log.error("Error finding comments by topic ID: {}", topicId, e);
            throw e;
        }
    }

    public List<Comment> findByAuthorId(Long authorId) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT c FROM Comment c JOIN FETCH c.topic JOIN FETCH c.author WHERE c.author.id = :userId ORDER BY c.createdAt ASC",
                            Comment.class)
                    .setParameter("userId", authorId)
                    .list();
        } catch (Exception e) {
            log.error("Error finding comments by author ID: {}", authorId, e);
            throw e;
        }
    }

    public void save(Comment comment) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            if (comment.getId() == null) {
                session.persist(comment);
                log.info("Comment created: {}", comment);
            } else {
                session.merge(comment);
                log.info("Comment updated: {}", comment);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Error saving comment: {}", comment, e);
        }
    }

    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Comment comment = session.get(Comment.class, id);
            if (comment != null) {
                session.remove(comment);
                log.info("Comment deleted: {}", comment);
            } else {
                log.warn("Comment not found: {}", id);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Error deleting comment: {}", id, e);
        }
    }
}
