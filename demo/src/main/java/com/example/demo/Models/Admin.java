package com.example.demo.Models;

import org.springframework.data.annotation.Id;
import java.util.UUID;

public class Admin implements User {
    @Id
    private String id;
    private String email;
    private String password;

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
