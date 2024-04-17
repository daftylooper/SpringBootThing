package com.example.demo.Services.ElectionCommands;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.demo.Models.Candidate;
import com.example.demo.Models.Election;
import com.example.demo.Models.Patterns.ElectionCommand;
import com.example.demo.Repositories.CandidateRepository;
import com.example.demo.Repositories.ElectionRepository;

@Component
public class DeclareWinnerCommand implements ElectionCommand {

    private final ElectionRepository electionRepository;
    private final CandidateRepository candidateRepository;
    private final Election election;

    public DeclareWinnerCommand(ElectionRepository electionRepository, Election election,
            CandidateRepository candidateRepository
    ) {
        this.electionRepository = electionRepository;
        this.candidateRepository = candidateRepository;
        this.election = election;
    }

    @Override
    public void execute() {
        Candidate winner = null;
        int maxVotes = Integer.MIN_VALUE;
        for (Map.Entry<String, Integer> entry : election.getCandidateVotes().entrySet()) {
            if (entry.getValue() > maxVotes) {
                maxVotes = entry.getValue();
                winner = candidateRepository.findById(entry.getKey()).orElse(null);
                election.setWinner(winner);
                electionRepository.save(election);
            }
        }
    }
}