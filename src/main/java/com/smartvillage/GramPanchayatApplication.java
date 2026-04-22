package com.smartvillage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GramPanchayatApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(GramPanchayatApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(GramPanchayatApplication.class, args);
        System.out.println("==============================================");
        System.out.println("Smart Village Platform Started Successfully!");
        System.out.println("Access at: http://localhost:8080/gram-panchayat");
        System.out.println("==============================================");
    }
}
