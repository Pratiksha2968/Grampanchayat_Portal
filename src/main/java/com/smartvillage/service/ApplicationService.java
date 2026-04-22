package com.smartvillage.service;

import com.smartvillage.dto.ApplicationDTO;
import com.smartvillage.entity.Application;
import com.smartvillage.entity.User;
import com.smartvillage.repository.ApplicationRepository;
import com.smartvillage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ApplicationService {
    
    @Autowired
    private ApplicationRepository applicationRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private CertificateService certificateService;
    
    @Transactional
    public Application createApplication(ApplicationDTO dto, String username, MultipartFile document) throws IOException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Application application = new Application();
        application.setApplicationNumber("APP" + System.currentTimeMillis());
        application.setUser(user);
        application.setApplicationType(dto.getApplicationType());
        application.setDetails(dto.getDetails());
        application.setPriority(dto.getPriority() != null ? dto.getPriority() : 5);
        
        if (document != null && !document.isEmpty()) {
            String fileName = saveDocument(document);
            application.setDocumentPath(fileName);
        }
        
        Application saved = applicationRepository.save(application);
        
        notificationService.sendNotification(user, "Application Submitted", 
            "Your application " + saved.getApplicationNumber() + " has been submitted successfully.");
        
        return saved;
    }
    
    public List<Application> getUserApplications(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return applicationRepository.findByUser(user);
    }
    
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }
    
    public List<Application> getPendingApplications() {
        return applicationRepository.findByStatusOrderByPriorityAsc("PENDING");
    }
    
    @Transactional
    public Application processApplication(Long id, String status, String remarks, String adminUsername) {
        Application application = applicationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Application not found"));
        
        User admin = userRepository.findByUsername(adminUsername)
            .orElseThrow(() -> new RuntimeException("Admin not found"));
        
        application.setStatus(status);
        application.setRemarks(remarks);
        application.setProcessedBy(admin);
        application.setProcessedDate(LocalDateTime.now());
        
        Application updated = applicationRepository.save(application);
        
        notificationService.sendNotification(application.getUser(), 
            "Application " + status, 
            "Your application " + application.getApplicationNumber() + " has been " + status.toLowerCase() + ".");
        
        // Auto-generate certificate if approved
        if ("APPROVED".equals(status)) {
            try {
                certificateService.generateCertificate(id, adminUsername);
            } catch (Exception e) {
                System.err.println("Failed to generate certificate: " + e.getMessage());
            }
        }
        
        return updated;
    }
    
    private String saveDocument(MultipartFile file) throws IOException {
        String uploadDir = "uploads/documents/";
        Path uploadPath = Paths.get(uploadDir);
        
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);
        
        return fileName;
    }
}
