package com.example.demo.Models.Patterns;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.demo.Models.Candidate;
import com.example.demo.Repositories.CandidateRepository;

@Component
public class CandidateVotesAdapter {
    
    private final CandidateRepository candidateRepository;

    public CandidateVotesAdapter(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }
    
    public HashMap<String, HashMap<String, Integer>> adaptCandidateVotes(Map<String, Integer> originalCandidateVotes) {
        HashMap<String, HashMap<String, Integer>> adaptedCandidateVotes = new HashMap<>();
        
        // Iterate through the original candidateVotes map and populate the adapted map with information
        for (Map.Entry<String, Integer> entry : originalCandidateVotes.entrySet()) {
            Candidate candidate = candidateRepository.findById(entry.getKey()).orElse(null);
            if (candidate != null) {
                HashMap<String, Integer> candidateInfo = new HashMap<>();
                candidateInfo.put(candidate.getName(), entry.getValue());
                adaptedCandidateVotes.put(candidate.getId(), candidateInfo);
            }
        }
        
        return adaptedCandidateVotes;
    }
}
