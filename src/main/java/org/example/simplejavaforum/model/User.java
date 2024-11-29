package org.example.simplejavaforum.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String password_hash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "user_role DEFAULT 'user'")
    private UserRole role = UserRole.USER;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topic> topics;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    public User() {
    }

    public User(String username, String password_hash, UserRole role) {
        this.username = username;
        this.password_hash = password_hash;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username;
    }
}
