package com.example.demo.Services;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.example.demo.Models.Candidate;
import com.example.demo.Repositories.CandidateRepository;

@Service
public class CandidateService {
    private final CandidateRepository candidateRepository;

    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public Candidate findCandidateById(String id) {
        Optional<Candidate> optionalCandidate = candidateRepository.findById(id);
        return optionalCandidate.orElse(null);
    }
    public void joinParty(String id, String party, String name) {
        Candidate candidate = findCandidateById(id);
        candidate.setPartyAffiliation(party);
        candidate.setName(name);
        candidateRepository.save(candidate);
    }
}