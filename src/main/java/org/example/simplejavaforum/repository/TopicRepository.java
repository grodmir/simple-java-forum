package org.example.simplejavaforum.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.simplejavaforum.model.Topic;
import org.example.simplejavaforum.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

@Slf4j
public class TopicRepository extends AbstractHibernateRepository<Topic> implements TopicRepositoryInterface {

    private static TopicRepository instance;

    private TopicRepository() {
        super(Topic.class);
    }

    public static synchronized TopicRepository getInstance() {
        if (instance == null) {
            instance = new TopicRepository();
        }
        return instance;
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
    public List<Topic> findByTitle(String title) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT t FROM Topic t JOIN FETCH t.author WHERE LOWER(t.title) LIKE :title ORDER BY t.createdAt DESC", Topic.class)
                    .setParameter("title", "%" + title.toLowerCase() + "%")
                    .list();
        }
    }

    @Override
    public List<Topic> findMostLiked(int limit) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT t FROM Topic t JOIN FETCH t.author ORDER BY t.likes DESC", Topic.class)
                    .setMaxResults(limit)
                    .list();
        }
    }

    @Override
    public int getTopicCount() {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Long count = session.createQuery("SELECT COUNT(t) FROM Topic t", Long.class).uniqueResult();
            return count != null ? count.intValue() : 0;
        }
    }
}
