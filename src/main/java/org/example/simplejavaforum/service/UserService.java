package org.example.simplejavaforum.service;

import org.example.simplejavaforum.dao.UserDao;
import org.example.simplejavaforum.model.User;

public class UserService {
    private final UserDao userDao = new UserDao();

    public User getUserById(Long id) {
        return userDao.findById(id);
    }

    public void save(User user) {
        userDao.save(user);
    }
}
