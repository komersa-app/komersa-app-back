package komersa.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class LoginAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "success")
    private boolean success;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public LoginAttempt(Long id, String name, boolean success, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.success = success;
        this.createdAt = createdAt;
    }
    public LoginAttempt(String name, boolean success, LocalDateTime createdAt) {
        this.id = null;
        this.name = name;
        this.success = success;
        this.createdAt = createdAt;
    }
}
