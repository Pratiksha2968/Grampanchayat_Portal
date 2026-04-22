package com.smartvillage.repository;

import com.smartvillage.entity.Application;
import com.smartvillage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByUser(User user);
    List<Application> findByStatus(String status);
    List<Application> findByApplicationType(String applicationType);
    List<Application> findByStatusOrderByPriorityAsc(String status);
    
    @Query("SELECT COUNT(a) FROM Application a WHERE a.status = ?1")
    Long countByStatus(String status);
    
    @Query("SELECT COUNT(a) FROM Application a WHERE a.applicationType = ?1 AND a.appliedDate BETWEEN ?2 AND ?3")
    Long countByTypeAndDateRange(String type, LocalDateTime start, LocalDateTime end);
}
