package com.example.demo.Services;
import org.springframework.stereotype.Service;
import com.example.demo.Models.Election;
import com.example.demo.Models.Patterns.ElectionCommand;
import com.example.demo.Models.Candidate;
import com.example.demo.Repositories.CandidateRepository;
import com.example.demo.Repositories.ElectionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ElectionService {
    private final ElectionRepository electionRepository;
    private final CandidateRepository candidateRepository;

    public ElectionService(ElectionRepository electionRepository, CandidateRepository candidateRepository) {
        this.electionRepository = electionRepository;
        this.candidateRepository = candidateRepository;
    }

    public void executeCommand(ElectionCommand command) {
        command.execute();
    }

    public void addCandidateToElection(String electionId, String candidateId) {
        Election election = electionRepository.findById(electionId).orElse(null);
        Candidate candidate = candidateRepository.findById(candidateId).orElse(null);
        if (election != null && candidate != null) {
            int candidateVotes = election.getCandidateVotes().getOrDefault(candidateId, -1);
            election.getCandidateVotes().put(candidateId, candidateVotes + 1);
            electionRepository.save(election);

            int electionVotes = candidate.getElectionVotes().getOrDefault(electionId, -1);
            candidate.getElectionVotes().put(electionId, electionVotes + 1);
            candidateRepository.save(candidate);
        }
    }

    public void standForElection(String candidateId, String electionId) {
        Election election = electionRepository.findById(electionId).orElse(null);
        Candidate candidate = candidateRepository.findById(candidateId).orElse(null);
        if (election != null) {
            if (candidate != null) {
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
        Candidate candidate = candidateRepository.findById(candidateId).orElse(null);
        if (candidate == null) {
            return true;
        }
        String candidateParty = candidate.getPartyAffiliation();
        for (String c : election.getCandidateVotes().keySet()) {
            Candidate ca = candidateRepository.findById(c).orElse(null);
            if (ca.getPartyAffiliation() != null && ca.getPartyAffiliation().equals(candidateParty)) {
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
    public Election getElectionById(String electionId) {
        Optional<Election> optionalElection = electionRepository.findById(electionId);
        return optionalElection.orElse(null);
    }
}
