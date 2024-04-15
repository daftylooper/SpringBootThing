package com.example.demo.Models;
import org.springframework.data.annotation.Id;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Election {
    @Id
    private String uid;
    private String name;
    private Date openDate;
    private Date closeDate;
    private String status;
    private List<Candidate> candidates;
    private HashMap<Candidate, Integer> candidateVotes; //candidateId, Votes
    private Candidate winner;

    public Election(){}

    public Election(String uid, String name, Date openDate, Date closeDate, String status, List<Candidate>candidates) {
        this.uid = uid;
        this.name = name;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.status = status;
        this.candidates = candidates;
    }

    public Election(String name) {   
        this.name = name;
        this.status = "pending";
        this.candidates = new ArrayList<>();
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

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public Candidate getWinner() {
        return winner;
    }

    public void setWinner(Candidate winner) {
        this.winner = winner;
    }
}
