package com.auth0.example.service;

import com.auth0.example.model.Message;
import com.auth0.example.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MessageService {

    private final MessageRepository repository;

    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    public Message saveMessage(Message message) {
        return repository.save(message);
    }

    public Message getMessageById(UUID id) {
        return repository.findById(id).orElseThrow();
    }
}
