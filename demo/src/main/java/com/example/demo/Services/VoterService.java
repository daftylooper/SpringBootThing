package com.example.demo.Services;

import java.util.Optional;

import com.example.demo.Models.Voter;
import com.example.demo.Repositories.VoterRepository;

public class VoterService {
    private final VoterRepository voterRepository;

    public VoterService(VoterRepository voterRepository) {
        this.voterRepository = voterRepository;
    }

    public Voter findVoterById(String id) {
        Optional<Voter> optionalCandidate = voterRepository.findById(id);
        return optionalCandidate.orElse(null);
    }
}
