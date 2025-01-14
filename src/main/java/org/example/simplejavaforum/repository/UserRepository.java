package org.example.simplejavaforum.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.simplejavaforum.model.User;
import org.example.simplejavaforum.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

@Slf4j
public class UserRepository {

    private static UserRepository instance;

    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public User findById(Long id) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.find(User.class, id);
        } catch (Exception e) {
            log.error("Error finding user by id {}: {}", id, e.getMessage());
            return null;
        }
    }

    public List<User> findAll() {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.createQuery("SELECT u FROM User u", User.class).getResultList();
        } catch (Exception e) {
            log.error("Error finding all users: {}", e.getMessage());
            return List.of(); // Возвращаем пустой список в случае ошибки
        }
    }

    public User findByUsername(String username) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResult();
        } catch (Exception e) {
            log.error("User not found with username {}: {}", username, e.getMessage());
            return null;
        }
    }

    public void save(User user) {
        if (!isRoleValid(user.getRole())) {
            log.warn("Invalid role provided: {}", user.getRole());
            throw new IllegalArgumentException("Invalid role provided: " + user.getRole());
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            if (user.getId() == null) {
                session.persist(user);
                log.info("User created: {}", user.getUsername());
            } else {
                session.merge(user); // Update
                log.info("User updated: {}", user.getUsername());
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Error saving user: {}", e.getMessage());
        }
    }

    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.find(User.class, id);
            if (user != null) {
                session.remove(user);
                log.info("User deleted: {}", user.getUsername());
            } else {
                log.warn("User with id {} not found", id);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Error deleting user: {}", e.getMessage());
        }
    }

    private boolean isRoleValid(String role) {
        return role.equalsIgnoreCase("user") || role.equalsIgnoreCase("moderator");
    }
}
