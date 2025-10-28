package com.example.demo.model;

/**
 * Base Ticket class - parent class for all ticket types
 * Changed to abstract class 
 */
public abstract class Ticket {
    // Common properties for all tickets
    protected int id;
    protected String title;
    protected String description;
    protected String status;

    //constructor
    public Ticket() {
        this.status = "Open"; // default status
    }

    // Constructor with paramss
    public Ticket(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = "Open";
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Abstract method - must be implemented by child classes
    // This enforces that each ticket type defines how to convert to file format
    public abstract String toFileString();

    @Override
    public String toString() {
        return toFileString();
    }
}
