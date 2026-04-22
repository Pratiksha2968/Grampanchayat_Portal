package com.smartvillage.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.smartvillage.entity.Application;
import com.smartvillage.entity.Certificate;
import com.smartvillage.entity.User;
import com.smartvillage.repository.ApplicationRepository;
import com.smartvillage.repository.CertificateRepository;
import com.smartvillage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class CertificateService {
    
    @Autowired
    private CertificateRepository certificateRepository;
    
    @Autowired
    private ApplicationRepository applicationRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Transactional
    public Certificate generateCertificate(Long applicationId, String adminUsername) throws Exception {
        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new RuntimeException("Application not found"));
        
        if (!"APPROVED".equals(application.getStatus())) {
            throw new RuntimeException("Application must be approved first");
        }
        
        User admin = userRepository.findByUsername(adminUsername)
            .orElseThrow(() -> new RuntimeException("Admin not found"));
        
        Certificate certificate = new Certificate();
        certificate.setCertificateNumber("CERT" + System.currentTimeMillis());
        certificate.setApplication(application);
        certificate.setUser(application.getUser());
        certificate.setCertificateType(application.getApplicationType());
        certificate.setIssuedBy(admin);
        
        String qrPath = generateQRCode(certificate.getCertificateNumber());
        certificate.setQrCodePath(qrPath);
        
        String pdfPath = generatePDF(certificate);
        certificate.setPdfPath(pdfPath);
        
        return certificateRepository.save(certificate);
    }
    
    private String generateQRCode(String certificateNumber) throws Exception {
        String uploadDir = "uploads/qrcodes/";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(certificateNumber, BarcodeFormat.QR_CODE, 200, 200);
        
        String fileName = UUID.randomUUID().toString() + ".png";
        Path filePath = uploadPath.resolve(fileName);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", filePath);
        
        return fileName;
    }
    
    private String generatePDF(Certificate certificate) throws Exception {
        String uploadDir = "uploads/certificates/";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        String fileName = UUID.randomUUID().toString() + ".pdf";
        Path filePath = uploadPath.resolve(fileName);
        
        PdfWriter writer = new PdfWriter(filePath.toString());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        
        document.add(new Paragraph("GRAM PANCHAYAT CERTIFICATE").setBold().setFontSize(20));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Certificate Number: " + certificate.getCertificateNumber()));
        document.add(new Paragraph("Certificate Type: " + certificate.getCertificateType()));
        document.add(new Paragraph("Issued To: " + certificate.getUser().getFullName()));
        document.add(new Paragraph("Issued Date: " + certificate.getIssuedDate()
            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
        document.add(new Paragraph("Issued By: " + certificate.getIssuedBy().getFullName()));
        
        document.close();
        
        return fileName;
    }
    
    public List<Certificate> getUserCertificates(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return certificateRepository.findByUser(user);
    }
    
    public Certificate verifyCertificate(String certificateNumber) {
        return certificateRepository.findByCertificateNumber(certificateNumber)
            .orElseThrow(() -> new RuntimeException("Certificate not found"));
    }
}
