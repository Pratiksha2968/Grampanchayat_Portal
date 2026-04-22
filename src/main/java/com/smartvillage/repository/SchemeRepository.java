package com.smartvillage.repository;

import com.smartvillage.entity.Scheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SchemeRepository extends JpaRepository<Scheme, Long> {
    List<Scheme> findByActiveTrue();
    List<Scheme> findByCategory(String category);
    
    @Query("SELECT s FROM Scheme s WHERE s.active = true AND " +
           "(s.minIncome IS NULL OR s.minIncome <= ?1) AND " +
           "(s.maxIncome IS NULL OR s.maxIncome >= ?1) AND " +
           "(s.minAge IS NULL OR s.minAge <= ?2) AND " +
           "(s.maxAge IS NULL OR s.maxAge >= ?2)")
    List<Scheme> findEligibleSchemes(Double income, Integer age);
}
