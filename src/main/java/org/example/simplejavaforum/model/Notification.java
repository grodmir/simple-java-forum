package org.example.simplejavaforum.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @Column(nullable = false)
    private String message;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    public Notification() {}

    public Notification(Topic topic, String message) {
        this.topic = topic;
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Notification{id=" + id + ", topic=" + topic.getTitle() + ", message='" + message + "'}";
    }
}
