package com.smartvillage.service;

import com.smartvillage.entity.Payment;
import com.smartvillage.entity.User;
import com.smartvillage.repository.PaymentRepository;
import com.smartvillage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private NotificationService notificationService;
    
    @Transactional
    public Payment createPayment(String username, String paymentType, Double amount) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Payment payment = new Payment();
        payment.setTransactionId("TXN" + System.currentTimeMillis());
        payment.setUser(user);
        payment.setPaymentType(paymentType);
        payment.setAmount(amount);
        payment.setStatus("COMPLETED");
        payment.setPaymentMethod("ONLINE");
        
        Payment saved = paymentRepository.save(payment);
        
        notificationService.sendNotification(user, 
            "Payment Successful", 
            "Your payment of Rs. " + amount + " was successful. Transaction ID: " + saved.getTransactionId());
        
        return saved;
    }
    
    @Transactional
    public Payment processPayment(Long paymentId, String razorpayOrderId, String razorpayPaymentId) {
        Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> new RuntimeException("Payment not found"));
        
        payment.setRazorpayOrderId(razorpayOrderId);
        payment.setRazorpayPaymentId(razorpayPaymentId);
        payment.setStatus("SUCCESS");
        payment.setPaymentMethod("RAZORPAY");
        
        Payment updated = paymentRepository.save(payment);
        
        notificationService.sendNotification(payment.getUser(), 
            "Payment Successful", 
            "Your payment of Rs. " + payment.getAmount() + " was successful. Transaction ID: " + payment.getTransactionId());
        
        return updated;
    }
    
    public List<Payment> getUserPayments(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return paymentRepository.findByUser(user);
    }
    
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
