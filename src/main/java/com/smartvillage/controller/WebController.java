package com.smartvillage.controller;

import com.smartvillage.service.TransparencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Map;

@Controller
public class WebController {
    
    @Autowired
    private TransparencyService transparencyService;
    
    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
    
    @GetMapping("/signup")
    public String signup() {
        return "auth/signup";
    }
    
    @GetMapping("/citizen/dashboard")
    public String citizenDashboard() {
        return "citizen/dashboard";
    }
    
    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
    }
    
    @GetMapping("/transparency")
    public String transparency(Model model) {
        Map<String, Object> stats = transparencyService.getTransparencyStats();
        model.addAttribute("stats", stats);
        return "transparency/dashboard";
    }
    
    @GetMapping("/certificate/verify")
    public String certificateVerify() {
        return "certificate/verify";
    }
}