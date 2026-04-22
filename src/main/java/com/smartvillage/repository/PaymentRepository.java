package com.smartvillage.repository;

import com.smartvillage.entity.Payment;
import com.smartvillage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUser(User user);
    List<Payment> findByStatus(String status);
    
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = 'SUCCESS' AND p.paymentDate BETWEEN ?1 AND ?2")
    Double getTotalCollectionBetweenDates(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT p.paymentType, SUM(p.amount) FROM Payment p WHERE p.status = 'SUCCESS' GROUP BY p.paymentType")
    List<Object[]> getCollectionByType();
}
