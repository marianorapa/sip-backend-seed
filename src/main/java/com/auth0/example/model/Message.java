package com.auth0.example.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Simple domain object for our API to return a message.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Message {

    @Id
    private UUID id;

    private String message;

    @PrePersist
    void prePersist() {
        this.id = UUID.randomUUID();
    }

    public Message(String message) {
        this.message = message;
    }
}
