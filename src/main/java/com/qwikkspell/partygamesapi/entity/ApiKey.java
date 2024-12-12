package com.qwikkspell.partygamesapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "api_keys")
@Data
public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "api_key", nullable = false, unique = true)
    private String apiKey;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "isActive", nullable = false)
    private Boolean isActive;

    public ApiKey(String apiKey, String role) {
        this.apiKey = apiKey;
        this.role = role;
        this.createdAt = LocalDateTime.now();
        this.isActive = true;
    }
    public ApiKey() {

    }
}
