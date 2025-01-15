package org.example.simplejavaforum.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.simplejavaforum.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

@Slf4j
public abstract class AbstractHibernateRepository<T> {

    private final Class<T> entityClass;

    public AbstractHibernateRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T findById(Long id) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.get(entityClass, id);
        } catch (Exception e) {
            log.error("Error finding entity by ID: {}", id, e);
            throw e;
        }
    }

    public List<T> findAll() {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.createQuery("FROM " + entityClass.getName(), entityClass).list();
        } catch (Exception e) {
            log.error("Error finding all entities", e);
            throw e;
        }
    }

    public void save(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            log.info("Entity saved: {}", entity);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Error saving entity: {}", entity, e);
        }
    }

    public void update(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
            log.info("Entity updated: {}", entity);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Error updating entity: {}", entity, e);
        }
    }

    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            T entity = session.get(entityClass, id);
            if (entity != null) {
                session.remove(entity);
                log.info("Entity deleted: {}", entity);
            } else {
                log.warn("Entity with ID {} not found", id);
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Error deleting entity with ID: {}", id, e);
        }
    }

}
