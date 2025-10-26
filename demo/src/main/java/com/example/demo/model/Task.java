package com.example.demo.model;

/**
 * Task class - extends Ticket
 * Represents a task ticket 
 */
public class Task extends Ticket {
    // Task-specific properties
    private String dueDate;
    private String priority;

    //constructor
    public Task() {
        super();
        this.priority = "Medium"; // default priority
    }

    // Constructor with params
    public Task(int id, String title, String description, String dueDate, String priority) {
        super(id, title, description); // call parent constructor
        this.dueDate = dueDate;
        this.priority = priority;
    }

    // Getters and Setters
    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    // Override toFileString to include task-specific fields
    @Override
    public String toFileString() {
        return "Type: Task\n" +
               "ID: " + id + "\n" +
               "Title: " + title + "\n" +
               "Description: " + description + "\n" +
               "Status: " + status + "\n" +
               "Due Date: " + dueDate + "\n" +
               "Priority: " + priority + "\n";
    }
}
