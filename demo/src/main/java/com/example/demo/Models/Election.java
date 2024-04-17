package com.example.demo.Models;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class Election {
    @Id
    private String uid;
    private String name;
    private Date openDate;
    private Date closeDate;
    private String status;
    private HashMap<String, Integer> candidateVotes; //candidateId, Votes
    private Candidate winner;

    public Election(){}

    public Election(String uid, String name, Date openDate, Date closeDate, String status) {
        this.uid = uid;
        this.name = name;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.status = status;
    }

    public Election(String name) {   
        this.name = name;
        this.status = "pending";
        this.candidateVotes = new HashMap<>();
    }

    public String getId() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Candidate getWinner() {
        return winner;
    }

    public void setWinner(Candidate winner) {
        this.winner = winner;
    }

    public HashMap<String, Integer> getCandidateVotes() {
        return candidateVotes;
    }

    public void setCandidateVotes(HashMap<String, Integer> candidateVotes) {
        this.candidateVotes = candidateVotes;
    }
}
