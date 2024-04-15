package com.example.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Models.LoginSchema;
import com.example.demo.Models.User;
import com.example.demo.Services.UserService;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String signUp() {
        return "signup";
    }
    
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody LoginSchema user) {
        String email = user.email;
        String password = user.password;
        String userType = getUserTypeFromEmail(email);
        userService.saveUser(userService.createUser(email, password), userType);
        return ResponseEntity.ok("/login");
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/userlogin")
    public ResponseEntity<String> login(@RequestBody LoginSchema loginUser, Model model) {
        String email = loginUser.email;
        String password = loginUser.password;
        User user = userService.findByEmailAndPassword(email, password);
        if (user != null) {
            String userType = getUserTypeFromEmail(email);
            String userId = user.getId();
            String next;
            switch (userType) {
                case "Voter":
                    next="/dashboard?userId="+userId;
                    break;
                case "Admin":
                next="/adminDashboard?userId="+userId;
                    break;
                case "Candidate":
                next="/candidateDashboard?userId="+userId;
                    break;
                default:
                    next = "/login";
            }
            return ResponseEntity.ok(next);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    private String getUserTypeFromEmail(String email) {
        if (email.endsWith("@voter.com")) {
            return "Voter";
        } else if (email.endsWith("@admin.com")) {
            return "Admin";
        } else if (email.endsWith("@candidate.com")) {
            return "Candidate";
        }
        return null;
    }
}
