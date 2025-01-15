package org.example.simplejavaforum.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.simplejavaforum.model.Comment;
import org.example.simplejavaforum.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

@Slf4j
public class CommentRepository extends AbstractHibernateRepository<Comment> implements CommentRepositoryInterface {
    private static CommentRepository instance;

    public CommentRepository() {
        super(Comment.class);
    }

    public static CommentRepository getInstance() {
        if (instance == null) {
            instance = new CommentRepository();
        }
        return instance;
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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
}
