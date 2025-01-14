package org.example.simplejavaforum.util;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Slf4j
public class HibernateUtil {
    private static HibernateUtil instance;
    private SessionFactory sessionFactory;

    private HibernateUtil() {
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Exception e) {
            log.error("SessionFactory initialization failed: {}", e.getMessage());
            throw new ExceptionInInitializerError("SessionFactory initialization failed: " + e);
        }
    }

    public static synchronized HibernateUtil getInstance() {
        if (instance == null) {
            log.info("Creating new instance of HibernateUtil");
            instance = new HibernateUtil();
        }
        return instance;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    public void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
