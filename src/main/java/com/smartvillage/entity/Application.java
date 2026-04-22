package com.smartvillage.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
public class Application {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String applicationNumber;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private String applicationType; // BIRTH_CERTIFICATE, DEATH_CERTIFICATE, INCOME_CERTIFICATE
    
    @Column(length = 1000)
    private String details;
    
    @Column(nullable = false)
    private String status = "PENDING"; // PENDING, APPROVED, REJECTED
    
    private String remarks;
    
    private String documentPath;
    
    private Integer priority = 5; // 1-10, lower is higher priority
    
    @Column(nullable = false)
    private LocalDateTime appliedDate = LocalDateTime.now();
    
    private LocalDateTime processedDate;
    
    @ManyToOne
    @JoinColumn(name = "processed_by")
    private User processedBy;

    // Constructors
    public Application() {}

    public Application(Long id, String applicationNumber, User user, String applicationType, String details, String status, String remarks, String documentPath, Integer priority, LocalDateTime appliedDate, LocalDateTime processedDate, User processedBy) {
        this.id = id;
        this.applicationNumber = applicationNumber;
        this.user = user;
        this.applicationType = applicationType;
        this.details = details;
        this.status = status;
        this.remarks = remarks;
        this.documentPath = documentPath;
        this.priority = priority;
        this.appliedDate = appliedDate;
        this.processedDate = processedDate;
        this.processedBy = processedBy;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getApplicationNumber() { return applicationNumber; }
    public void setApplicationNumber(String applicationNumber) { this.applicationNumber = applicationNumber; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getApplicationType() { return applicationType; }
    public void setApplicationType(String applicationType) { this.applicationType = applicationType; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getDocumentPath() { return documentPath; }
    public void setDocumentPath(String documentPath) { this.documentPath = documentPath; }

    public Integer getPriority() { return priority; }
    public void setPriority(Integer priority) { this.priority = priority; }

    public LocalDateTime getAppliedDate() { return appliedDate; }
    public void setAppliedDate(LocalDateTime appliedDate) { this.appliedDate = appliedDate; }

    public LocalDateTime getProcessedDate() { return processedDate; }
    public void setProcessedDate(LocalDateTime processedDate) { this.processedDate = processedDate; }

    public User getProcessedBy() { return processedBy; }
    public void setProcessedBy(User processedBy) { this.processedBy = processedBy; }
}
