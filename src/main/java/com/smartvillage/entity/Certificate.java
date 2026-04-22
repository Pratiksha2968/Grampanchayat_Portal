package com.smartvillage.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "certificates")
public class Certificate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String certificateNumber;
    
    @OneToOne
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private String certificateType;
    
    private String pdfPath;
    
    private String qrCodePath;
    
    @Column(nullable = false)
    private LocalDateTime issuedDate = LocalDateTime.now();
    
    @ManyToOne
    @JoinColumn(name = "issued_by")
    private User issuedBy;

    // Default constructor
    public Certificate() {}

    // Constructor with all fields
    public Certificate(Long id, String certificateNumber, Application application, User user, 
                      String certificateType, String pdfPath, String qrCodePath, 
                      LocalDateTime issuedDate, User issuedBy) {
        this.id = id;
        this.certificateNumber = certificateNumber;
        this.application = application;
        this.user = user;
        this.certificateType = certificateType;
        this.pdfPath = pdfPath;
        this.qrCodePath = qrCodePath;
        this.issuedDate = issuedDate;
        this.issuedBy = issuedBy;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public String getQrCodePath() {
        return qrCodePath;
    }

    public void setQrCodePath(String qrCodePath) {
        this.qrCodePath = qrCodePath;
    }

    public LocalDateTime getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(LocalDateTime issuedDate) {
        this.issuedDate = issuedDate;
    }

    public User getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(User issuedBy) {
        this.issuedBy = issuedBy;
    }
}
