package com.smartvillage.controller;

import com.smartvillage.dto.ApplicationDTO;
import com.smartvillage.dto.ComplaintDTO;
import com.smartvillage.entity.*;
import com.smartvillage.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/citizen")
public class CitizenController {
    
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private ComplaintService complaintService;
    @Autowired
    private CertificateService certificateService;
    @Autowired
    private SchemeService schemeService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private NotificationService notificationService;
    
    @PostMapping("/applications")
    public ResponseEntity<Application> createApplication(
            @RequestBody ApplicationDTO dto,
            @RequestParam(required = false) MultipartFile document,
            Authentication auth) throws Exception {
        Application application = applicationService.createApplication(dto, auth.getName(), document);
        return ResponseEntity.ok(application);
    }
    
    @GetMapping("/applications")
    public ResponseEntity<List<Application>> getMyApplications(Authentication auth) {
        List<Application> applications = applicationService.getUserApplications(auth.getName());
        return ResponseEntity.ok(applications);
    }
    
    @PostMapping("/complaints")
    public ResponseEntity<Complaint> createComplaint(
            @RequestBody ComplaintDTO dto,
            @RequestParam(required = false) MultipartFile image,
            Authentication auth) throws Exception {
        Complaint complaint = complaintService.createComplaint(dto, auth.getName(), image);
        return ResponseEntity.ok(complaint);
    }
    
    @GetMapping("/complaints")
    public ResponseEntity<List<Complaint>> getMyComplaints(Authentication auth) {
        List<Complaint> complaints = complaintService.getUserComplaints(auth.getName());
        return ResponseEntity.ok(complaints);
    }
    
    @GetMapping("/certificates")
    public ResponseEntity<List<Certificate>> getMyCertificates(Authentication auth) {
        List<Certificate> certificates = certificateService.getUserCertificates(auth.getName());
        return ResponseEntity.ok(certificates);
    }
    
    @GetMapping("/schemes")
    public ResponseEntity<List<Scheme>> getActiveSchemes() {
        List<Scheme> schemes = schemeService.getAllActiveSchemes();
        return ResponseEntity.ok(schemes);
    }
    
    @GetMapping("/schemes/recommended")
    public ResponseEntity<List<Scheme>> getRecommendedSchemes(Authentication auth) {
        List<Scheme> schemes = schemeService.getRecommendedSchemes(auth.getName());
        return ResponseEntity.ok(schemes);
    }
    
    @PostMapping("/payments")
    public ResponseEntity<Payment> createPayment(
            @RequestParam String paymentType,
            @RequestParam Double amount,
            Authentication auth) {
        Payment payment = paymentService.createPayment(auth.getName(), paymentType, amount);
        return ResponseEntity.ok(payment);
    }
    
    @GetMapping("/payments")
    public ResponseEntity<List<Payment>> getMyPayments(Authentication auth) {
        List<Payment> payments = paymentService.getUserPayments(auth.getName());
        return ResponseEntity.ok(payments);
    }
}