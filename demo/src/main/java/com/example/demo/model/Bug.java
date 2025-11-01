package com.example.demo.model;

import jakarta.persistence.*;

/**
 * Bug class - extends Ticket
 * Represents a bug ticket with JPA annotations for database persistence
 */
@Entity
@Table(name = "bug")
public class Bug extends Ticket {
    
    @Column(length = 50)
    private String severity;
    
    @Column(name = "steps_to_reproduce", length = 1000)
    private String stepsToReproduce;

    // Default constructor (required by JPA)
    public Bug() {
        super();
        this.severity = "Medium";
    }

    // Constructor with parameters
    public Bug(String title, String description, String severity, String stepsToReproduce) {
        super(title, description, "Bug");
        this.severity = severity != null ? severity : "Medium";
        this.stepsToReproduce = stepsToReproduce;
    }

    // Getters and Setters
    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getStepsToReproduce() {
        return stepsToReproduce;
    }

    public void setStepsToReproduce(String stepsToReproduce) {
        this.stepsToReproduce = stepsToReproduce;
    }

    @Override
    public String getDetails() {
        return "Type: Bug\n" +
               "ID: " + id + "\n" +
               "Title: " + title + "\n" +
               "Description: " + description + "\n" +
               "Status: " + status + "\n" +
               "Severity: " + severity + "\n" +
               "Steps to Reproduce: " + stepsToReproduce + "\n";
    }
}
