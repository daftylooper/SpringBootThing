package com.example.demo.Services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.Models.Election;
import com.example.demo.Models.Patterns.ElectionFlyweight;

public class ElectionFlyweightFactory {
    private Map<String, ElectionFlyweight> flyweights = new HashMap<>();

    public ElectionFlyweight getElectionFlyweight(List<Election> pendingElections, List<Election> ongoingElections, List<Election> completedElections) {
        String key = generateKey(pendingElections, ongoingElections, completedElections);
        if (!flyweights.containsKey(key)) {
            flyweights.put(key, new ElectionFlyweightImpl(pendingElections, ongoingElections, completedElections));
        }
        return flyweights.get(key);
    }

    private String generateKey(List<Election> pendingElections, List<Election> ongoingElections, List<Election> completedElections) {
        int pendingCount = pendingElections.size();
        int ongoingCount = ongoingElections.size();
        int completedCount = completedElections.size();
    
        // Concatenate the counts to form a unique key
        return pendingCount + "_" + ongoingCount + "_" + completedCount;
    }
}