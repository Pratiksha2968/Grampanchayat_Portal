package com.smartvillage.service;

import com.smartvillage.entity.TransparencyRecord;
import com.smartvillage.repository.TransparencyRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransparencyService {
    
    @Autowired
    private TransparencyRecordRepository transparencyRepository;
    
    public List<TransparencyRecord> getAllRecords() {
        return transparencyRepository.findAll();
    }
    
    public List<TransparencyRecord> getRecordsByType(String recordType) {
        return transparencyRepository.findByRecordType(recordType);
    }
    
    public TransparencyRecord createRecord(TransparencyRecord record) {
        return transparencyRepository.save(record);
    }
    
    public Map<String, Object> getTransparencyStats() {
        Map<String, Object> stats = new HashMap<>();
        
        Double totalFunds = transparencyRepository.getTotalByRecordType("FUND_RECEIVED");
        Double totalExpenses = transparencyRepository.getTotalByRecordType("EXPENSE");
        
        stats.put("totalFunds", totalFunds != null ? totalFunds : 0.0);
        stats.put("totalExpenses", totalExpenses != null ? totalExpenses : 0.0);
        stats.put("remainingFunds", (totalFunds != null ? totalFunds : 0.0) - (totalExpenses != null ? totalExpenses : 0.0));
        
        List<Object[]> expensesByCategory = transparencyRepository.getExpensesByCategory();
        stats.put("expensesByCategory", expensesByCategory);
        
        return stats;
    }
}