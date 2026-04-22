package com.smartvillage.controller;

import com.smartvillage.entity.TransparencyRecord;
import com.smartvillage.service.TransparencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transparency")
public class TransparencyController {
    
    @Autowired
    private TransparencyService transparencyService;
    
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = transparencyService.getTransparencyStats();
        stats.put("totalRecords", 0);
        stats.put("projectsCompleted", 0);
        stats.put("projectsOngoing", 0);
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/records")
    public ResponseEntity<List<TransparencyRecord>> getAllRecords() {
        List<TransparencyRecord> records = transparencyService.getAllRecords();
        return ResponseEntity.ok(records);
    }
    
    @PostMapping("/records")
    public ResponseEntity<TransparencyRecord> createRecord(@RequestBody TransparencyRecord record) {
        TransparencyRecord created = transparencyService.createRecord(record);
        return ResponseEntity.ok(created);
    }
}
