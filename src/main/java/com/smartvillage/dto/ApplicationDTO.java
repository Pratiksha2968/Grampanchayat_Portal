package com.smartvillage.dto;

import jakarta.validation.constraints.NotBlank;

public class ApplicationDTO {
    
    @NotBlank(message = "Application type is required")
    private String applicationType;
    
    private String details;
    private Integer priority;

    // Constructors
    public ApplicationDTO() {}

    public ApplicationDTO(String applicationType, String details, Integer priority) {
        this.applicationType = applicationType;
        this.details = details;
        this.priority = priority;
    }

    // Getters and Setters
    public String getApplicationType() { return applicationType; }
    public void setApplicationType(String applicationType) { this.applicationType = applicationType; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public Integer getPriority() { return priority; }
    public void setPriority(Integer priority) { this.priority = priority; }
}
