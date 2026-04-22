package com.smartvillage.service;

import com.smartvillage.dto.JwtResponse;
import com.smartvillage.dto.LoginRequest;
import com.smartvillage.dto.MessageResponse;
import com.smartvillage.dto.SignupRequest;
import com.smartvillage.entity.Role;
import com.smartvillage.entity.User;
import com.smartvillage.repository.RoleRepository;
import com.smartvillage.repository.UserRepository;
import com.smartvillage.security.JwtUtils;
import com.smartvillage.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .findFirst()
            .orElse("")
            .replace("ROLE_", "");
        
        return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), 
                              userDetails.getEmail(), role);
    }
    
    @Transactional
    public MessageResponse signup(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return new MessageResponse("Error: Username already exists!");
        }
        
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return new MessageResponse("Error: Email already exists!");
        }
        
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setFullName(signupRequest.getFullName());
        user.setPhoneNumber(signupRequest.getPhoneNumber());
        user.setAddress(signupRequest.getAddress());
        user.setAadharNumber(signupRequest.getAadharNumber());
        user.setAge(signupRequest.getAge());
        user.setOccupation(signupRequest.getOccupation());
        user.setIncome(signupRequest.getIncome());
        
        Role citizenRole = roleRepository.findByName("CITIZEN")
            .orElseThrow(() -> new RuntimeException("Error: Role not found."));
        user.setRole(citizenRole);
        
        userRepository.save(user);
        return new MessageResponse("User registered successfully!");
    }
}
