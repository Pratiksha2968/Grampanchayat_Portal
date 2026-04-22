package com.smartvillage.controller;

import com.smartvillage.entity.*;
import com.smartvillage.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
    @Autowired
    private ApplicationService applicationService;
    
    @Autowired
    private ComplaintService complaintService;
    
    @Autowired
    private CertificateService certificateService;
    
    @Autowired
    private SchemeService schemeService;
    
    @Autowired
    private TransparencyService transparencyService;
    
    @Autowired
    private AnalyticsService analyticsService;
    
    @Autowired
    private PaymentService paymentService;
    
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboard() {
        Map<String, Object> stats = analyticsService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = analyticsService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/applications")
    public ResponseEntity<List<Application>> getAllApplications() {
        List<Application> applications = applicationService.getAllApplications();
        return ResponseEntity.ok(applications);
    }
    
    @GetMapping("/applications/pending")
    public ResponseEntity<List<Application>> getPendingApplications() {
        List<Application> applications = applicationService.getPendingApplications();
        return ResponseEntity.ok(applications);
    }
    
    @PutMapping("/applications/{id}/process")
    public ResponseEntity<Application> processApplication(
            @PathVariable Long id,
            @RequestBody Map<String, String> request,
            Authentication auth) {
        String status = request.get("status");
        String remarks = request.get("remarks");
        Application application = applicationService.processApplication(id, status, remarks, auth.getName());
        return ResponseEntity.ok(application);
    }
    
    @PostMapping("/applications/{id}/certificate")
    public ResponseEntity<Certificate> generateCertificate(
            @PathVariable Long id,
            Authentication auth) throws Exception {
        Certificate certificate = certificateService.generateCertificate(id, auth.getName());
        return ResponseEntity.ok(certificate);
    }
    
    @GetMapping("/complaints")
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        List<Complaint> complaints = complaintService.getAllComplaints();
        return ResponseEntity.ok(complaints);
    }
    
    @GetMapping("/complaints/open")
    public ResponseEntity<List<Complaint>> getOpenComplaints() {
        List<Complaint> complaints = complaintService.getOpenComplaints();
        return ResponseEntity.ok(complaints);
    }
    
    @PutMapping("/complaints/{id}/status")
    public ResponseEntity<Complaint> updateComplaintStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam String resolution,
            Authentication auth) {
        Complaint complaint = complaintService.updateComplaintStatus(id, status, resolution, auth.getName());
        return ResponseEntity.ok(complaint);
    }
    
    @PostMapping("/schemes")
    public ResponseEntity<Scheme> createScheme(@RequestBody Scheme scheme) {
        Scheme created = schemeService.createScheme(scheme);
        return ResponseEntity.ok(created);
    }
    
    @PutMapping("/schemes/{id}")
    public ResponseEntity<Scheme> updateScheme(@PathVariable Long id, @RequestBody Scheme scheme) {
        Scheme updated = schemeService.updateScheme(id, scheme);
        return ResponseEntity.ok(updated);
    }
    
    @PostMapping("/transparency")
    public ResponseEntity<TransparencyRecord> createTransparencyRecord(@RequestBody TransparencyRecord record) {
        TransparencyRecord created = transparencyService.createRecord(record);
        return ResponseEntity.ok(created);
    }
    
    @GetMapping("/analytics/applications")
    public ResponseEntity<Map<String, Long>> getApplicationAnalytics() {
        Map<String, Long> data = analyticsService.getApplicationsByType();
        return ResponseEntity.ok(data);
    }
    
    @GetMapping("/analytics/complaints")
    public ResponseEntity<List<Object[]>> getComplaintAnalytics() {
        List<Object[]> data = analyticsService.getComplaintsByCategory();
        return ResponseEntity.ok(data);
    }
    
    @GetMapping("/analytics/payments")
    public ResponseEntity<List<Object[]>> getPaymentAnalytics() {
        List<Object[]> data = analyticsService.getPaymentsByType();
        return ResponseEntity.ok(data);
    }
    
    @GetMapping("/payments")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }
}