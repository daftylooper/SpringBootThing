package com.example.demo.Services;
import org.springframework.stereotype.Service;

import com.example.demo.Models.Admin;
import com.example.demo.Models.Candidate;
import com.example.demo.Models.User;
import com.example.demo.Models.Voter;
import com.example.demo.Repositories.AdminRepository;
import com.example.demo.Repositories.CandidateRepository;
import com.example.demo.Repositories.VoterRepository;

@Service
public class UserService {

    private final AdminRepository adminRepository;
    private final CandidateRepository candidateRepository;
    private final VoterRepository voterRepository;

    public UserService(AdminRepository adminRepository, CandidateRepository candidateRepository, VoterRepository voterRepository) {
        this.adminRepository = adminRepository;
        this.candidateRepository = candidateRepository;
        this.voterRepository = voterRepository;
    }
    public User createUser(String email, String password) {
        if (email.endsWith("@voter.com")) {
            return new Voter(email, password);
        } else if (email.endsWith("@admin.com")) {
            return new Admin(email, password);
        } else if (email.endsWith("@candidate.com")) {
            return new Candidate(email, password);
        } else {
            throw new IllegalArgumentException("Invalid email domain");
        }
    }
    public void saveUser(User user, String userType) {
        if (userType.equals("Voter")) {
            Voter vUser = (Voter) user;
            voterRepository.save(vUser);
        }
        else if (userType.equals("Candidate")) {
            Candidate cUser = (Candidate) user;
            candidateRepository.save(cUser);
        }
        else if (userType.equals("Admin")) {
            Admin aUser = (Admin) user;
            adminRepository.save(aUser);
        }
    }
    public User findByEmailAndPassword(String email, String password) {
        User user;
        if (email.endsWith("@voter.com")) {
            user = voterRepository.findByEmail(email);
        } else if (email.endsWith("@admin.com")) {
            user = adminRepository.findByEmail(email);
        } else if (email.endsWith("@candidate.com")) {
            user = candidateRepository.findByEmail(email);
        } else {
            throw new IllegalArgumentException("Invalid email domain");
        }
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
