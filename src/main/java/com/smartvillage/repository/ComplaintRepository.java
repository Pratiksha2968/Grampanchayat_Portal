package com.smartvillage.repository;

import com.smartvillage.entity.Complaint;
import com.smartvillage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByUser(User user);
    List<Complaint> findByStatus(String status);
    List<Complaint> findByCategory(String category);
    List<Complaint> findByStatusOrderByPriorityAsc(String status);
    
    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.status = ?1")
    Long countByStatus(String status);
    
    @Query("SELECT c.category, COUNT(c) FROM Complaint c GROUP BY c.category")
    List<Object[]> countByCategory();
}
