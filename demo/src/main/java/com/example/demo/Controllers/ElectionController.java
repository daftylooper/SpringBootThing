package com.example.demo.Controllers;

import com.example.demo.Models.Election;
import com.example.demo.Models.Patterns.CandidateVotesAdapter;
import com.example.demo.Models.Patterns.ElectionFlyweight;
import com.example.demo.Repositories.CandidateRepository;
import com.example.demo.Repositories.ElectionRepository;
import com.example.demo.Repositories.VoterRepository;
import com.example.demo.Services.ElectionFlyweightFactory;
import com.example.demo.Services.ElectionService;
import com.example.demo.Services.ElectionCommands.DeclareWinnerCommand;
import com.example.demo.Services.ElectionCommands.StartElectionCommand;
import com.example.demo.Services.ElectionCommands.StopElectionCommand;
import com.example.demo.Services.ElectionCommands.VoteCommand;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ElectionController {

    private final ElectionService electionService;
    private final ElectionRepository electionRepository;
    private final CandidateRepository candidateRepository;
    private final VoterRepository voterRepository;

    public ElectionController(ElectionService electionService, CandidateRepository candidateRepository,
            ElectionRepository electionRepository, VoterRepository voterRepository) {
        this.electionService = electionService;
        this.candidateRepository = candidateRepository;
        this.electionRepository = electionRepository;
        this.voterRepository = voterRepository;
    }

    //DISPLAYS
    @GetMapping("/dashboard")
    public String showDashboard(Model model, @RequestParam("userId") String userId) {
        List<Election> pendingElections = electionService.getPendingElections();
        List<Election> ongoingElections = electionService.getOngoingElections();
        List<Election> completedElections = electionService.getCompletedElections();

        ElectionFlyweightFactory factory = new ElectionFlyweightFactory();
        ElectionFlyweight flyweight = factory.getElectionFlyweight(pendingElections, ongoingElections, completedElections);
        flyweight.showDashboard(model, userId);
        return "dashboard";
    }

    @GetMapping("/adminDashboard")
    public String showAdminDashboard(Model model) {
        List<Election> pendingElections = electionService.getPendingElections();
        List<Election> ongoingElections = electionService.getOngoingElections();
        List<Election> completedElections = electionService.getCompletedElections();
        String userId = "admin";
        ElectionFlyweightFactory factory = new ElectionFlyweightFactory();
        ElectionFlyweight flyweight = factory.getElectionFlyweight(pendingElections, ongoingElections, completedElections);
        flyweight.showDashboard(model, userId);
        return "admin-dashboard";
    }

    @GetMapping("/election/candidateDashboard")
    public String candidateDashboard(@RequestParam("userId") String userId, Model model) {
        List<Election> pendingElections = electionService.getPendingElections();
        List<Election> ongoingElections = electionService.getOngoingElections();
        List<Election> completedElections = electionService.getCompletedElections();
        
        ElectionFlyweightFactory factory = new ElectionFlyweightFactory();
        ElectionFlyweight flyweight = factory.getElectionFlyweight(pendingElections, ongoingElections, completedElections);
        flyweight.showDashboard(model, userId);
        return "candidate-dashboard";
    }
    
    @GetMapping("/dashboard/electionDetails")
    public String viewElectionDetailsVoter(@RequestParam("electionId") String electionId,
            @RequestParam("userId") String userId, Model model) {
        CandidateVotesAdapter candidateVotesAdapter = new CandidateVotesAdapter(candidateRepository);
        Election election = electionService.getElectionById(electionId);
        model.addAttribute("userId", userId);
        model.addAttribute("election", election);
        Map<String, Integer> originalCandidateVotes = election.getCandidateVotes();
        HashMap<String, HashMap<String, Integer>> adaptedCandidateVotes = candidateVotesAdapter
                .adaptCandidateVotes(originalCandidateVotes);
        model.addAttribute("candidatesVotes", adaptedCandidateVotes);
        return "election-details-voter";
    }

    @GetMapping("/adminDashboard/electionDetails")
    public String viewElectionDetailsAdmin(@RequestParam("electionId") String electionId, Model model) {
        Election election = electionService.getElectionById(electionId);
        model.addAttribute("election", election);
        CandidateVotesAdapter candidateVotesAdapter = new CandidateVotesAdapter(candidateRepository);
        Map<String, Integer> originalCandidateVotes = election.getCandidateVotes();
        HashMap<String, HashMap<String, Integer>> adaptedCandidateVotes = candidateVotesAdapter
                .adaptCandidateVotes(originalCandidateVotes);
        model.addAttribute("candidatesVotes", adaptedCandidateVotes);
        return "election-details-admin";
    }

    @GetMapping("/candidateDashboard/electionDetails")
    public String viewElectionDetailsCandidate(@RequestParam("userId") String userId, @RequestParam("electionId") String electionId, Model model) {
        Election election = electionService.getElectionById(electionId);
        model.addAttribute("election", election);
        model.addAttribute("userId", userId);
        CandidateVotesAdapter candidateVotesAdapter = new CandidateVotesAdapter(candidateRepository);
        Map<String, Integer> originalCandidateVotes = election.getCandidateVotes();
        HashMap<String, HashMap<String, Integer>> adaptedCandidateVotes = candidateVotesAdapter.adaptCandidateVotes(originalCandidateVotes);
        model.addAttribute("candidatesVotes", adaptedCandidateVotes);
        return "election-details-candidate";
    }

    // COMMANDS
    
    @GetMapping("/dashboard/electionDetails/vote")
    public String makeVote(@RequestParam("electionId") String electionId,
            @RequestParam("userId") String userId, @RequestParam("candidateId") String candidateId) {
        VoteCommand voteCommand = new VoteCommand(electionRepository, candidateRepository, voterRepository, electionId,
                candidateId, userId);
        electionService.executeCommand(voteCommand);
        return "redirect:/dashboard/electionDetails?userId=" + userId + "&electionId=" + electionId;
    }
    
    @PostMapping("/adminDashboard/startElection")
    public String startElection(@RequestParam("electionId") String electionId) {
        Election election = electionService.getElectionById(electionId);
        if (election != null) {
            StartElectionCommand startElectionCommand = new StartElectionCommand(electionRepository, election);
            electionService.executeCommand(startElectionCommand);
        }
        return "redirect:/adminDashboard";
    }

    @PostMapping("/adminDashboard/closeElection")
    public String closeElection(@RequestParam("electionId") String electionId) {
        Election election = electionService.getElectionById(electionId);
        if (election != null) {
            StopElectionCommand stopElectionCommand = new StopElectionCommand(electionRepository, election);
            DeclareWinnerCommand declareWinnerCommand = new DeclareWinnerCommand(electionRepository, election,
                    candidateRepository);
            electionService.executeCommand(stopElectionCommand);
            electionService.executeCommand(declareWinnerCommand);
        }
        return "redirect:/adminDashboard";
    }

    @PostMapping("/adminDashboard/addElection")
    public String addElection(@RequestParam("name") String name) {
        Election election = new Election(name);
        electionService.addElection(election);
        return "redirect:/adminDashboard";
    }

    @PostMapping("/candidateDashboard/standForElection")
    public String standForElection(@RequestParam("userId") String userId, @RequestParam("electionId") String electionId,
    Model model) {
        model.addAttribute("userId", userId);
        electionService.standForElection(userId, electionId);
        return "redirect:/candidateDashboard?userId=" + userId;
    }
}
