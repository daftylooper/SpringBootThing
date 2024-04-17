package com.example.demo.Models;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import com.example.demo.Models.Patterns.UserFactory;

import java.util.UUID;

@Component
public class Admin implements UserFactory {
    @Id
    private String id;
    private String email;
    private String password;

    public Admin() {
    };

    public Admin(String email, String password) {
        this.email = email;
        this.password = password;
        this.id = UUID.randomUUID().toString();

    }
    public String getId() {
        return id;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
