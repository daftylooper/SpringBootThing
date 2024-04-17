package com.example.demo.Models;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import com.example.demo.Models.Patterns.UserFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class Voter implements UserFactory {
    
    @Id
    private String id;
    private String email;
    private String password;
    private List<String> elections; //Elections voted for

    public Voter(){}

    public Voter(String email, String password) {
        this.email = email;
        this.password = password;
        this.id = UUID.randomUUID().toString();
        this.elections = new ArrayList<>(); 
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

    public List<String> getElections() {
        return elections;
    }
}
