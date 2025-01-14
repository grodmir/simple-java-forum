package org.example.simplejavaforum.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.simplejavaforum.model.Topic;
import org.example.simplejavaforum.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

@Slf4j
public class TopicRepository {

    private static TopicRepository instance;

    public static synchronized TopicRepository getInstance() {
        if (instance == null) {
            instance = new TopicRepository();
        }
        return instance;
    }

    public Topic getTopicById(Long id) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT t FROM Topic t JOIN FETCH t.author WHERE t.id = :id", Topic.class)
                    .setParameter("id", id)
                    .uniqueResult();
        } catch (Exception e) {
            log.error("Error fetching topic by ID: {}", id, e);
            return null; // Обрабатываем случай, когда тема не найдена
        }
    }

    public List<Topic> findAllSortedByDate() {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT t FROM Topic t JOIN FETCH t.author ORDER BY t.createdAt DESC", Topic.class)
                    .list();
        } catch (Exception e) {
            log.error("Error fetching all topics sorted by date", e);
            throw e;
        }
    }

    public List<Topic> findSortedByDateWithPagination(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT t FROM Topic t JOIN FETCH t.author ORDER BY t.createdAt DESC", Topic.class)
                    .setFirstResult(offset)
                    .setMaxResults(pageSize)
                    .list();
        }
    }

    public List<Topic> findSortedByLikesWithPagination(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT t FROM Topic t JOIN FETCH t.author ORDER BY t.likes DESC", Topic.class)
                    .setFirstResult(offset)
                    .setMaxResults(pageSize)
                    .list();
        }
    }

    public List<Topic> findSortedByDislikesWithPagination(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT t FROM Topic t JOIN FETCH t.author ORDER BY t.dislikes DESC", Topic.class)
                    .setFirstResult(offset)
                    .setMaxResults(pageSize)
                    .list();
        }
    }

    public List<Topic> findByTitle(String title) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT t FROM Topic t JOIN FETCH t.author WHERE LOWER(t.title) LIKE :title ORDER BY t.createdAt DESC", Topic.class)
                    .setParameter("title", "%" + title.toLowerCase() + "%")
                    .list();
        }
    }

    public List<Topic> findMostLiked(int limit) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT t FROM Topic t JOIN FETCH t.author ORDER BY t.likes DESC", Topic.class)
                    .setMaxResults(limit)
                    .list();
        }
    }

    public int getTopicCount() {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Long count = session.createQuery("SELECT COUNT(t) FROM Topic t", Long.class).uniqueResult();
            return count != null ? count.intValue() : 0;
        }
    }

    public void save(Topic topic) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(topic);
            transaction.commit();
            log.info("Topic saved: {}", topic.getTitle());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Error saving topic: {}", topic.getTitle(), e);
        }
    }

    public void update(Topic topic) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(topic);
            transaction.commit();
            log.info("Topic updated: {}", topic);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Error updating topic: {}", e.getMessage());
        }
    }

    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Topic topic = session.get(Topic.class, id);
            if (topic != null) {
                session.remove(topic);
                log.info("Topic deleted: {}", topic);
            } else {
                log.warn("Topic not found with ID: {}", id);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Error deleting topic with ID {}: {}", id, e.getMessage());
        }
    }
}
