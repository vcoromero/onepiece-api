package com.onepiece.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {
    
    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to the One Piece API!");
        response.put("description", "API to manage the characters of the One Piece world");
        response.put("version", "1.0.0");
        response.put("timestamp", LocalDateTime.now());
        response.put("endpoints", Map.of(
            "characters", "/api/characters",
            "crews", "/api/crews",
            "devil-fruits", "/api/devil-fruits",
            "devil-fruit-types", "/api/devil-fruit-types",
            "health", "/health"
        ));
        return response;
    }
    
    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "One Piece API is running!");
        return response;
    }
}
