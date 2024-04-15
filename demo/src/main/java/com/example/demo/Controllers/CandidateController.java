package com.example.demo.Controllers;

import org.springframework.ui.Model;
import com.example.demo.Services.CandidateService;
import com.example.demo.Models.Candidate;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping("/selectParty")
    public String selectParty(Model model, @RequestParam("userId") String userId) {
        List<String> partyOptions = Arrays.asList("Party A", "Party B", "Party C");
        model.addAttribute("partyOptions", partyOptions);
        model.addAttribute("userId", userId);
        return "select-party";
    }

    @PostMapping("/joinParty")
    public String joinParty(@RequestParam("userId") String userId, @RequestParam("party") String party, @RequestParam("name") String name, Model model) {
        candidateService.joinParty(userId, party, name);
        return "redirect:/candidateDashboard?userId="+userId;
    }

    @GetMapping("/candidateDashboard")
    public String candidateDashboard(@RequestParam("userId") String userId, Model model) {    
        Candidate candidate = candidateService.findCandidateById(userId);
            if (candidate==null || candidate.getPartyAffiliation() == null || candidate.getPartyAffiliation().isEmpty()) {
                return "redirect:/selectParty?userId=" + userId;
            } else {
                System.out.println(userId);
                return "redirect:/election/candidateDashboard?userId=" + userId;
            }
        }
        
    }