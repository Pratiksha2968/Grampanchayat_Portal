package com.smartvillage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SmartVillageApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SmartVillageApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SmartVillageApplication.class, args);
        System.out.println("==============================================");
        System.out.println("Smart Village Platform Started Successfully!");
        System.out.println("Access at: http://localhost:8080/gram-panchayat");
        System.out.println("==============================================");
    }
}