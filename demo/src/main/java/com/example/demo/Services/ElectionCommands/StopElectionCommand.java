package com.example.demo.Services.ElectionCommands;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.example.demo.Models.Election;
import com.example.demo.Models.Patterns.ElectionCommand;
import com.example.demo.Repositories.ElectionRepository;

@Component
public class StopElectionCommand implements ElectionCommand {

    private final ElectionRepository electionRepository;
    private final Election election;

    public StopElectionCommand(ElectionRepository electionRepository, Election election) {
        this.electionRepository = electionRepository;
        this.election = election;
    }

    @Override
    public void execute() {
        election.setStatus("completed");
        election.setCloseDate(new Date());
        electionRepository.save(election);
    }
}