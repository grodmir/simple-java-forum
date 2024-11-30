package org.example.simplejavaforum.dao;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.example.simplejavaforum.model.User;
import org.example.simplejavaforum.util.JpaUtil;

import java.util.List;

@Slf4j
public class UserDao {

    public User findById(Long id) {
        try (EntityManager em = JpaUtil.getInstance().getEntityManager()) {
            return em.find(User.class, id);
        }
    }

    public List<User> findAll() {
        try (EntityManager em = JpaUtil.getInstance().getEntityManager()) {
            return em.createQuery("SELECT u FROM User u", User.class).getResultList();
        }
    }

    public User findByUsername(String username) {
        try (EntityManager em = JpaUtil.getInstance().getEntityManager()) {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            log.error("User not found with username {}: {}", username, e.getMessage());
            return null;
        }
    }

    public List<User> findByRole(String role) {
        if (!isRoleValid(role)) {
            log.warn("Invalid role provided: {}", role);
            return List.of();
        }

        try (EntityManager em = JpaUtil.getInstance().getEntityManager()) {
            return em.createQuery("SELECT u FROM User u WHERE u.role = :role", User.class)
                    .setParameter("role", role)
                    .getResultList();
        }
    }

    public void save(User user) {
        if(!isRoleValid(user.getRole())) {
            log.warn("Invalid role provided: {}", user.getRole());
            throw new IllegalArgumentException("Invalid role provided: " + user.getRole());
        }

        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            if (user.getId() == null) {
                em.persist(user);
                log.info("User created: {}", user.getUsername());
            } else {
                em.merge(user); // Update
                log.info("User updated: {}", user.getUsername());
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            log.error("Error saving user: {}", e.getMessage());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, id);
            if (user != null) {
                em.remove(user);
                log.info("User deleted: {}", user.getUsername());
            } else {
                log.warn("User with id {} not found", id);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            log.error("Error deleting user: {}", e.getMessage());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    private boolean isRoleValid(String role) {
        return role.equalsIgnoreCase("user") || role.equalsIgnoreCase("moderator");
    }
}
