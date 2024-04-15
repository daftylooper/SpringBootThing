package com.example.demo.Services;
import org.springframework.stereotype.Service;
import com.example.demo.Models.Election;
import com.example.demo.Models.Candidate;
import com.example.demo.Repositories.ElectionRepository;

import java.util.List;
import java.util.Date;
import java.util.Optional;

@Service
public class ElectionService {
    private final ElectionRepository electionRepository;
    private final CandidateService candidateService;

    public ElectionService(ElectionRepository electionRepository, CandidateService candidateService) {
        this.electionRepository = electionRepository;
        this.candidateService = candidateService;
    }

    public void addCandidateToElection(String electionId, String candidateId) {
        Election election = electionRepository.findById(electionId).orElse(null);
        if (election != null) {
            List<Candidate> candidates = election.getCandidates();
            Candidate candidate = candidateService.findCandidateById(candidateId);
            candidates.add(candidate);
            election.setCandidates(candidates);
            electionRepository.save(election);   //CHECK IF THIS CREATES DUPLICATES
        }
    }

    public void standForElection(String candidateId, String electionId) {
        Election election = electionRepository.findById(electionId).orElse(null);
        if (election != null) {
            if (candidateId != null) {
                if (!isCandidateFromSamePartyInElection(election, candidateId)) {
                    addCandidateToElection(electionId, candidateId);
                    return;
                } else {
                    System.out.println("Candidate from the same party already in the election.");
                    return;
                }
            }
            System.out.println("Candidate does not exist.");
        }
    }
    
    private boolean isCandidateFromSamePartyInElection(Election election, String candidateId) {
        Candidate candidate = candidateService.findCandidateById(candidateId);
        if (candidate == null) {
            return true;
        }
        String candidateParty = candidate.getPartyAffiliation();
        for (Candidate c : election.getCandidates()) {
            if (c.getPartyAffiliation() != null && c.getPartyAffiliation().equals(candidateParty)) {
                return true;
            }
        }
        return false;
    }
    
    
    public List<Election> getPendingElections() {
        return electionRepository.findByStatus("pending");
    }
    public List<Election> getOngoingElections() {
        return electionRepository.findByStatus("ongoing");
    }
    public List<Election> getCompletedElections() {
        return electionRepository.findByStatus("completed");
    }

    public void addElection(Election election) {
        electionRepository.save(election);
    }

    public void startElection(Election election) {
        election.setStatus("ongoing");
        election.setOpenDate(new Date());
        electionRepository.save(election);
    }

    public void closeElection(Election election) {
        election.setStatus("completed");
        election.setCloseDate(new Date());
        electionRepository.save(election);
    }

    public Election getElectionById(String electionId) {
        Optional<Election> optionalElection = electionRepository.findById(electionId);
        return optionalElection.orElse(null);
    }

}
