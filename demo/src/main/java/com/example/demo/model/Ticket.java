package com.example.demo.model;

/**
 * Base Ticket class - parent class for all ticket types
 * Contains properties that all tickets share
 */
public class Ticket {
    // Common properties for all tickets
    protected int id;
    protected String title;
    protected String description;
    protected String status;

    // Default constructor
    public Ticket() {
        this.status = "Open"; // default status
    }

    // Constructor with parameters
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

    // Method to convert ticket to string format for file storage
    public String toFileString() {
        return "Type: Ticket\n" +
               "ID: " + id + "\n" +
               "Title: " + title + "\n" +
               "Description: " + description + "\n" +
               "Status: " + status + "\n";
    }

    @Override
    public String toString() {
        return toFileString();
    }
}
