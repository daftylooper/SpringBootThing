package com.example.demo.Repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.Models.Admin;

public interface AdminRepository extends MongoRepository<Admin, String> {
    Optional<Admin> findById(String id);

    Admin findByEmail(String email);
}
