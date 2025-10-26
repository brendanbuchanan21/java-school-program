package com.example.demo.model;

/**
 * Bug class - extends Ticket
 * Represents a bug ticket
 */
public class Bug extends Ticket {
    // Bug-specific properties
    private String severity;
    private String stepsToReproduce;

    // constructor
    public Bug() {
        super();
        this.severity = "Medium"; // default severity
    }

    // Constructor with parameters
    public Bug(int id, String title, String description, String severity, String stepsToReproduce) {
        super(id, title, description); // call parent constructor
        this.severity = severity;
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

    // Override toFileString to include bug-specific fields
    @Override
    public String toFileString() {
        return "Type: Bug\n" +
               "ID: " + id + "\n" +
               "Title: " + title + "\n" +
               "Description: " + description + "\n" +
               "Status: " + status + "\n" +
               "Severity: " + severity + "\n" +
               "Steps to Reproduce: " + stepsToReproduce + "\n";
    }
}
