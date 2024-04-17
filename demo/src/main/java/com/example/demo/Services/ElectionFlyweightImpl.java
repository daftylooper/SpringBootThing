package com.example.demo.Services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.demo.Models.Election;
import com.example.demo.Models.Patterns.ElectionFlyweight;

@Service
public class ElectionFlyweightImpl implements ElectionFlyweight {
    private List<Election> pendingElections;
    private List<Election> ongoingElections;
    private List<Election> completedElections;

    public ElectionFlyweightImpl(List<Election> pendingElections, List<Election> ongoingElections, List<Election> completedElections) {
        this.pendingElections = pendingElections;
        this.ongoingElections = ongoingElections;
        this.completedElections = completedElections;
    }

    @Override
    public void showDashboard(Model model, String userId) {
        model.addAttribute("userId", userId);
        model.addAttribute("pendingElections", pendingElections);
        model.addAttribute("ongoingElections", ongoingElections);
        model.addAttribute("completedElections", completedElections);
    }
}