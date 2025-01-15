package org.example.simplejavaforum.service;

import org.example.simplejavaforum.model.User;
import org.example.simplejavaforum.repository.UserRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserService {

    private final UserRepository userRepository = UserRepository.getInstance();

    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public boolean isUsernameTaken(String username) {
        return userRepository.findByUsername(username) != null;
    }

    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while hashing password", e);
        }
    }

    public User validateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null; // Пользователь не найден
        }

        String hashedPassword = hashPassword(password);

        if (hashedPassword.equals(user.getPassword_hash())) {
            return user; // Успешная валидация
        } else {
            return null; // Пароль неверный
        }
    }
}
