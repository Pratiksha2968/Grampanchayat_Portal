package com.smartvillage.controller;

import com.smartvillage.dto.JwtResponse;
import com.smartvillage.dto.LoginRequest;
import com.smartvillage.dto.MessageResponse;
import com.smartvillage.dto.SignupRequest;
import com.smartvillage.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> signup(@Valid @RequestBody SignupRequest signupRequest) {
        MessageResponse response = authService.signup(signupRequest);
        return ResponseEntity.ok(response);
    }
}