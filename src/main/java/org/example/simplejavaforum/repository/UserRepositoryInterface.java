package org.example.simplejavaforum.repository;

import org.example.simplejavaforum.model.User;

import java.util.List;

public interface UserRepositoryInterface {
    User findById(Long id);
    List<User> findAll();
    User findByUsername(String username);
    void save(User user);
    void update(User user);
    void delete(Long id);
    boolean isRoleValid(String role);
}
