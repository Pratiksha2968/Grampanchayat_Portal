package com.smartvillage.dto;

import jakarta.validation.constraints.NotBlank;

public class ComplaintDTO {
    
    @NotBlank(message = "Category is required")
    private String category;
    
    @NotBlank(message = "Subject is required")
    private String subject;
    
    private String description;
    private String location;
    private Double latitude;
    private Double longitude;
    private Integer priority;

    // Constructors
    public ComplaintDTO() {}

    public ComplaintDTO(String category, String subject, String description, String location, Double latitude, Double longitude, Integer priority) {
        this.category = category;
        this.subject = subject;
        this.description = description;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.priority = priority;
    }

    // Getters and Setters
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Integer getPriority() { return priority; }
    public void setPriority(Integer priority) { this.priority = priority; }
}
