package com.example.demo.Models.Patterns;

import org.springframework.ui.Model;

public interface ElectionFlyweight {
    void showDashboard(Model model, String userId);
}