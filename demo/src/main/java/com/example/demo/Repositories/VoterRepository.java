package com.example.demo.Repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.Models.Voter;

public interface VoterRepository extends MongoRepository<Voter, String> {
    @SuppressWarnings("null")
    Optional<Voter> findById(String id);
    Voter findByEmail(String email);
}
