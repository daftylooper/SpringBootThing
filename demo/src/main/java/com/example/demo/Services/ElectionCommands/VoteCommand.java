package com.example.demo.Services.ElectionCommands;
import com.example.demo.Models.Election;
import com.example.demo.Models.Candidate;
import com.example.demo.Models.Voter;
import com.example.demo.Models.Patterns.ElectionCommand;
import com.example.demo.Repositories.CandidateRepository;
import com.example.demo.Repositories.ElectionRepository;
import com.example.demo.Repositories.VoterRepository;

public class VoteCommand implements ElectionCommand {

    private final ElectionRepository electionRepository;
    private final CandidateRepository candidateRepository;
    private final VoterRepository voterRepository;
    private final String electionId;
    private final String candidateId;
    private final String userId;

    public VoteCommand(ElectionRepository electionRepository, CandidateRepository candidateRepository,
                       VoterRepository voterRepository, String electionId, String candidateId, String userId) {
        this.electionRepository = electionRepository;
        this.candidateRepository = candidateRepository;
        this.voterRepository = voterRepository;
        this.electionId = electionId;
        this.candidateId = candidateId;
        this.userId = userId;
    }

    @Override
    public void execute() {
        Election election = electionRepository.findById(electionId).orElse(null);
        Candidate candidate = candidateRepository.findById(candidateId).orElse(null);
        Voter user = voterRepository.findById(userId).orElse(null);
        if (user != null && election != null && candidate != null) {
            if (user.getElections().contains(electionId)) {
                System.out.println("User has already voted in this election.");
            } else {
                user.getElections().add(electionId);
                System.out.println(user.getElections());
                voterRepository.save(user);

                int candidateVotes = election.getCandidateVotes().getOrDefault(candidateId, -1);
                election.getCandidateVotes().put(candidateId, candidateVotes + 1);
                System.out.println(election.getCandidateVotes());

                int electionVotes = candidate.getElectionVotes().getOrDefault(electionId, -1);
                candidate.getElectionVotes().put(electionId, electionVotes + 1);

                electionRepository.save(election);
                candidateRepository.save(candidate);
            }
        }
    }
}
