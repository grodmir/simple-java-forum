package org.example.simplejavaforum.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.simplejavaforum.model.User;
import org.example.simplejavaforum.util.HibernateUtil;
import org.hibernate.Session;

@Slf4j
public class UserRepository extends AbstractHibernateRepository<User> implements UserRepositoryInterface {

    private static UserRepository instance;

    public UserRepository() {
        super(User.class);
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    @Override
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

    @Override
    public void save(User user) {
        if (!isRoleValid(user.getRole())) {
            log.warn("Invalid role provided: {}", user.getRole());
            throw new IllegalArgumentException("Invalid role provided: " + user.getRole());
        }
        super.save(user);
    }

    @Override
    public boolean isRoleValid(String role) {
        return role.equalsIgnoreCase("user") || role.equalsIgnoreCase("moderator");
    }
}
