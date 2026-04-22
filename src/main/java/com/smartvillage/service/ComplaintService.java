package com.smartvillage.service;

import com.smartvillage.dto.ComplaintDTO;
import com.smartvillage.entity.Complaint;
import com.smartvillage.entity.User;
import com.smartvillage.repository.ComplaintRepository;
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
public class ComplaintService {
    
    @Autowired
    private ComplaintRepository complaintRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private NotificationService notificationService;
    
    @Transactional
    public Complaint createComplaint(ComplaintDTO dto, String username, MultipartFile image) throws IOException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Complaint complaint = new Complaint();
        complaint.setComplaintNumber("CMP" + System.currentTimeMillis());
        complaint.setUser(user);
        complaint.setCategory(dto.getCategory());
        complaint.setSubject(dto.getSubject());
        complaint.setDescription(dto.getDescription());
        complaint.setLocation(dto.getLocation());
        complaint.setLatitude(dto.getLatitude());
        complaint.setLongitude(dto.getLongitude());
        complaint.setPriority(dto.getPriority() != null ? dto.getPriority() : 5);
        
        if (image != null && !image.isEmpty()) {
            String fileName = saveImage(image);
            complaint.setImagePath(fileName);
        }
        
        Complaint saved = complaintRepository.save(complaint);
        
        notificationService.sendNotification(user, "Complaint Registered", 
            "Your complaint " + saved.getComplaintNumber() + " has been registered successfully.");
        
        return saved;
    }
    
    public List<Complaint> getUserComplaints(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return complaintRepository.findByUser(user);
    }
    
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }
    
    public List<Complaint> getOpenComplaints() {
        return complaintRepository.findByStatusOrderByPriorityAsc("OPEN");
    }
    
    @Transactional
    public Complaint updateComplaintStatus(Long id, String status, String resolution, String adminUsername) {
        Complaint complaint = complaintRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Complaint not found"));
        
        User admin = userRepository.findByUsername(adminUsername)
            .orElseThrow(() -> new RuntimeException("Admin not found"));
        
        complaint.setStatus(status);
        complaint.setResolution(resolution);
        
        if ("RESOLVED".equals(status) || "CLOSED".equals(status)) {
            complaint.setResolvedBy(admin);
            complaint.setResolvedDate(LocalDateTime.now());
        }
        
        Complaint updated = complaintRepository.save(complaint);
        
        notificationService.sendNotification(complaint.getUser(), 
            "Complaint Status Updated", 
            "Your complaint " + complaint.getComplaintNumber() + " status: " + status);
        
        return updated;
    }
    
    private String saveImage(MultipartFile file) throws IOException {
        String uploadDir = "uploads/complaints/";
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
