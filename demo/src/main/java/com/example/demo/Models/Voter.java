package com.example.demo.Models;

import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;

public class Voter implements User {
    
    @Id
    private String id;
    private String email;
    private String password;
    private List<Election> elections; //Elections voted for

    public Voter(String email, String password) {
        this.email = email;
        this.password = password;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setUid(String uid) {
        this.id = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password; // You may want to hash the password before setting it
    }
}
