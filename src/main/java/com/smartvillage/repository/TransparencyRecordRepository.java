package com.smartvillage.repository;

import com.smartvillage.entity.TransparencyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransparencyRecordRepository extends JpaRepository<TransparencyRecord, Long> {
    List<TransparencyRecord> findByRecordType(String recordType);
    List<TransparencyRecord> findByRecordDateBetween(LocalDate start, LocalDate end);
    
    @Query("SELECT SUM(t.amount) FROM TransparencyRecord t WHERE t.recordType = ?1")
    Double getTotalByRecordType(String recordType);
    
    @Query("SELECT t.category, SUM(t.amount) FROM TransparencyRecord t WHERE t.recordType = 'EXPENSE' GROUP BY t.category")
    List<Object[]> getExpensesByCategory();
}
