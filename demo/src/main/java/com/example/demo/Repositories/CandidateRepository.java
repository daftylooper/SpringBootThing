package com.example.demo.Repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.Models.Candidate;

public interface CandidateRepository extends MongoRepository<Candidate, String> {
    @SuppressWarnings("null")
    Optional<Candidate> findById(String id);

    Candidate findByEmail(String email);
}
