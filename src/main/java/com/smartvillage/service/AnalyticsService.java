package com.smartvillage.service;

import com.smartvillage.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService {
    
    @Autowired
    private ApplicationRepository applicationRepository;
    
    @Autowired
    private ComplaintRepository complaintRepository;
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private CertificateRepository certificateRepository;
    
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalApplications", applicationRepository.count());
        stats.put("pendingApplications", applicationRepository.countByStatus("PENDING"));
        stats.put("approvedApplications", applicationRepository.countByStatus("APPROVED"));
        
        stats.put("totalComplaints", complaintRepository.count());
        stats.put("openComplaints", complaintRepository.countByStatus("OPEN"));
        stats.put("resolvedComplaints", complaintRepository.countByStatus("RESOLVED"));
        
        stats.put("totalCertificates", certificateRepository.count());
        stats.put("totalPayments", paymentRepository.count());
        
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0);
        LocalDateTime endOfMonth = LocalDateTime.now();
        Double monthlyCollection = paymentRepository.getTotalCollectionBetweenDates(startOfMonth, endOfMonth);
        stats.put("monthlyCollection", monthlyCollection != null ? monthlyCollection : 0.0);
        
        return stats;
    }
    
    public Map<String, Long> getApplicationsByType() {
        Map<String, Long> data = new HashMap<>();
        data.put("BIRTH_CERTIFICATE", applicationRepository.countByStatus("BIRTH_CERTIFICATE"));
        data.put("DEATH_CERTIFICATE", applicationRepository.countByStatus("DEATH_CERTIFICATE"));
        data.put("INCOME_CERTIFICATE", applicationRepository.countByStatus("INCOME_CERTIFICATE"));
        return data;
    }
    
    public List<Object[]> getComplaintsByCategory() {
        return complaintRepository.countByCategory();
    }
    
    public List<Object[]> getPaymentsByType() {
        return paymentRepository.getCollectionByType();
    }
}
