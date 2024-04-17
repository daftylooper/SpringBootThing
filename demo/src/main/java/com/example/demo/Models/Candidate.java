    package com.example.demo.Models;

    import java.util.HashMap;
    import java.util.UUID;

    import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import com.example.demo.Models.Patterns.UserFactory;

    @Component
    public class Candidate implements UserFactory {
        @Id
        private String id;
        private String email;
        private String name;
        private String password;
        private String partyAffiliation;
        private HashMap<String, Integer> electionVotes; //ElectionId, Votes

        public Candidate() {
        };
        public Candidate(String email, String password) {
            this.email = email;
            this.password = password;
            this.id = UUID.randomUUID().toString();
            this.electionVotes = new HashMap<>();
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

    public HashMap<String, Integer> getElectionVotes() {
        return electionVotes;
    }

    public void setElectionVotes(HashMap<String, Integer> electionVotes) {
        this.electionVotes = electionVotes;
    }
    
    }
