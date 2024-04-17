package com.example.demo.Models.Patterns;

import org.springframework.stereotype.Component;

@Component
public interface UserFactory {
    String getId();
    String getEmail();
    String getPassword();
}
