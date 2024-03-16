package com.auth0.example.web;

import com.auth0.example.model.Message;
import com.auth0.example.service.MessageService;
import com.auth0.example.web.dto.JsonResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Handles requests to "/api" endpoints.
 * @see com.auth0.example.security.SecurityConfig to see how these endpoints are protected.
 */
@RestController
@RequestMapping(path = "/message", produces = MediaType.APPLICATION_JSON_VALUE)
// For simplicity of this sample, allow all origins. Real applications should configure CORS for their use case.
@CrossOrigin(origins = "*")
public class MessageController {


    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<JsonResponse<Message>> getMessageById(@PathVariable UUID id) {
        var response = new JsonResponse<Message>();
        try {
            Message message = service.getMessageById(id);
            response.setData(message);
            return ResponseEntity.ok(response);
        }
        catch (NoSuchElementException e) {
            response.addError(e.getMessage());
            return new ResponseEntity<>(response, NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<JsonResponse<Message>> storeMessage(@RequestBody Message message) {
        var response = new JsonResponse<Message>();
        Message storedMessage = service.saveMessage(message);
        response.setData(storedMessage);
        return ResponseEntity.ok(response);
    }

}
