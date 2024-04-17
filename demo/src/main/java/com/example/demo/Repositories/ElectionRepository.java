package com.example.demo.Repositories;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.demo.Models.Election;
import java.util.List;
import java.util.Optional;

public interface ElectionRepository extends MongoRepository<Election, String> {
    List<Election> findByStatus(String status);
    @SuppressWarnings("null")
    Optional<Election> findById(String id);
}