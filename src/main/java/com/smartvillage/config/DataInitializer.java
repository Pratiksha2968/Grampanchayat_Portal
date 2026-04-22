package com.smartvillage.config;

import com.smartvillage.entity.Role;
import com.smartvillage.entity.Scheme;
import com.smartvillage.entity.User;
import com.smartvillage.repository.RoleRepository;
import com.smartvillage.repository.SchemeRepository;
import com.smartvillage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private SchemeRepository schemeRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
        initializeAdminUser();
        initializeSchemes();
    }
    
    private void initializeRoles() {
        if (roleRepository.count() == 0) {
            Role citizenRole = new Role();
            citizenRole.setName("CITIZEN");
            citizenRole.setDescription("Regular citizen user");
            roleRepository.save(citizenRole);
            
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            adminRole.setDescription("Gram Sevak administrator");
            roleRepository.save(adminRole);
            
            Role superAdminRole = new Role();
            superAdminRole.setName("SUPER_ADMIN");
            superAdminRole.setDescription("Super administrator");
            roleRepository.save(superAdminRole);
            
            System.out.println("Roles initialized successfully!");
        }
    }
    
    private void initializeAdminUser() {
        if (!userRepository.existsByUsername("admin")) {
            Role adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new RuntimeException("Admin role not found"));
            
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@grampanchayat.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFullName("Gram Sevak Administrator");
            admin.setPhoneNumber("9876543210");
            admin.setAddress("Gram Panchayat Office");
            admin.setRole(adminRole);
            
            userRepository.save(admin);
            System.out.println("Admin user created: username=admin, password=admin123");
        }
    }
    
    private void initializeSchemes() {
        if (schemeRepository.count() == 0) {
            // PM-KISAN Scheme
            Scheme pmKisan = new Scheme();
            pmKisan.setName("PM-KISAN");
            pmKisan.setDescription("Direct income support to farmers");
            pmKisan.setCategory("AGRICULTURE");
            pmKisan.setMaxIncome(200000.0);
            pmKisan.setEligibilityCriteria("Small and marginal farmers");
            pmKisan.setStartDate(LocalDate.of(2024, 1, 1));
            pmKisan.setEndDate(LocalDate.of(2024, 12, 31));
            schemeRepository.save(pmKisan);
            
            // Ayushman Bharat
            Scheme ayushman = new Scheme();
            ayushman.setName("Ayushman Bharat");
            ayushman.setDescription("Health insurance for poor families");
            ayushman.setCategory("HEALTH");
            ayushman.setMaxIncome(100000.0);
            ayushman.setEligibilityCriteria("BPL families");
            ayushman.setStartDate(LocalDate.of(2024, 1, 1));
            ayushman.setEndDate(LocalDate.of(2024, 12, 31));
            schemeRepository.save(ayushman);
            
            // MGNREGA
            Scheme mgnrega = new Scheme();
            mgnrega.setName("MGNREGA");
            mgnrega.setDescription("Employment guarantee scheme");
            mgnrega.setCategory("EMPLOYMENT");
            mgnrega.setMinAge(18);
            mgnrega.setMaxAge(65);
            mgnrega.setEligibilityCriteria("Rural households");
            mgnrega.setStartDate(LocalDate.of(2024, 1, 1));
            mgnrega.setEndDate(LocalDate.of(2024, 12, 31));
            schemeRepository.save(mgnrega);
            
            // Beti Bachao Beti Padhao
            Scheme betiScheme = new Scheme();
            betiScheme.setName("Beti Bachao Beti Padhao");
            betiScheme.setDescription("Girl child education and empowerment");
            betiScheme.setCategory("EDUCATION");
            betiScheme.setMinAge(0);
            betiScheme.setMaxAge(18);
            betiScheme.setEligibilityCriteria("Girl children");
            betiScheme.setStartDate(LocalDate.of(2024, 1, 1));
            betiScheme.setEndDate(LocalDate.of(2024, 12, 31));
            schemeRepository.save(betiScheme);
            
            System.out.println("Sample schemes initialized successfully!");
        }
    }
}