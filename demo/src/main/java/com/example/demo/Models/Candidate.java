    package com.example.demo.Models;

    import java.util.HashMap;
    import java.util.UUID;

import org.springframework.data.annotation.Id;

    public class Candidate implements User {
        @Id
        private String id;
        private String email;
        private String name;
        private String password;
        private String partyAffiliation;
        private HashMap<Election, Integer> electionVotes; //ElectionId, Votes

    public Candidate(String email, String password) {
        this.email = email;
        this.password = password;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartyAffiliation() {
        return partyAffiliation;
    }

    public void setPartyAffiliation(String partyAffiliation) {
        this.partyAffiliation = partyAffiliation;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    }
