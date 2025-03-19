package com.example.book_store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.book_store.model.User;
import com.example.book_store.service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticateUser(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        return ResponseEntity.ok(authService.registerUser(user));
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
