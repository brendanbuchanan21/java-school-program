package com.example.demo.model;

import jakarta.persistence.*;

/**
 * Task class - extends Ticket
 * Represents a task ticket with JPA annotations for database persistence
 */
@Entity
@Table(name = "task")
public class Task extends Ticket {
    
    @Column(name = "due_date")
    private String dueDate;
    
    @Column(length = 50)
    private String priority;

    // Default constructor (required by JPA)
    public Task() {
        super();
        this.priority = "Medium";
    }

    // Constructor with parameters
    public Task(String title, String description, String dueDate, String priority) {
        super(title, description, "Task");
        this.dueDate = dueDate;
        this.priority = priority != null ? priority : "Medium";
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

    @Override
    public String getDetails() {
        return "Type: Task\n" +
               "ID: " + id + "\n" +
               "Title: " + title + "\n" +
               "Description: " + description + "\n" +
               "Status: " + status + "\n" +
               "Due Date: " + dueDate + "\n" +
               "Priority: " + priority + "\n";
    }
}
