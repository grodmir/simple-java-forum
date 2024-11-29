package org.example.simplejavaforum.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JpaUtil {
    public static JpaUtil instance;
    private EntityManagerFactory emf;

    private JpaUtil() {
        try {
            emf = Persistence.createEntityManagerFactory("defaultPU");
        } catch (Exception ex) {
            log.error("EntityManagerFactory initialization failed: {}", ex.getMessage());
            throw new RuntimeException("Failed to initialize EntityManagerFactory", ex);
        }
    }

    public static synchronized JpaUtil getInstance() {
        if (instance == null) {
            log.info("Creating new instance of JpaUtil");
            instance = new JpaUtil();
        }
        return instance;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            log.info("EntityManagerFactory closed");
        }
    }
}
