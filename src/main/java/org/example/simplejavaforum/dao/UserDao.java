package org.example.simplejavaforum.dao;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.example.simplejavaforum.model.User;
import org.example.simplejavaforum.util.JpaUtil;

@Slf4j
public class UserDao {

    public void save(User user) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            log.info("User saved: " + user.toString());
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.error("Saving user failed: {}", e.getMessage());
        } finally {
            em.close();
        }
    }
}
