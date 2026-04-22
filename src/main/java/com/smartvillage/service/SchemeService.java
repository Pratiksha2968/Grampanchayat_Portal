package com.smartvillage.service;

import com.smartvillage.entity.Scheme;
import com.smartvillage.entity.User;
import com.smartvillage.repository.SchemeRepository;
import com.smartvillage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SchemeService {
    
    @Autowired
    private SchemeRepository schemeRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public List<Scheme> getAllActiveSchemes() {
        return schemeRepository.findByActiveTrue();
    }
    
    public List<Scheme> getRecommendedSchemes(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Double income = user.getIncome() != null ? user.getIncome() : 0.0;
        Integer age = user.getAge() != null ? user.getAge() : 0;
        
        return schemeRepository.findEligibleSchemes(income, age);
    }
    
    public Scheme createScheme(Scheme scheme) {
        return schemeRepository.save(scheme);
    }
    
    public Scheme updateScheme(Long id, Scheme scheme) {
        Scheme existing = schemeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Scheme not found"));
        
        existing.setName(scheme.getName());
        existing.setDescription(scheme.getDescription());
        existing.setCategory(scheme.getCategory());
        existing.setMinIncome(scheme.getMinIncome());
        existing.setMaxIncome(scheme.getMaxIncome());
        existing.setMinAge(scheme.getMinAge());
        existing.setMaxAge(scheme.getMaxAge());
        existing.setEligibilityCriteria(scheme.getEligibilityCriteria());
        existing.setStartDate(scheme.getStartDate());
        existing.setEndDate(scheme.getEndDate());
        existing.setActive(scheme.getActive());
        
        return schemeRepository.save(existing);
    }
    
    public void deleteScheme(Long id) {
        schemeRepository.deleteById(id);
    }
}
