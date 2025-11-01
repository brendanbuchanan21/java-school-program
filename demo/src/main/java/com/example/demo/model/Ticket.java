package com.example.demo.model;

import jakarta.persistence.*;

/**
 * Base Ticket class - parent class for all ticket types
 * Abstract class with JPA Entity annotations for database persistence
 * Uses single table inheritance strategy
 */
@Entity
@Table(name = "ticket")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Ticket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    
    @Column(nullable = false)
    protected String title;
    
    @Column(length = 1000)
    protected String description;
    
    @Column(length = 50)
    protected String status;
    
    @Column(name = "ticket_type", nullable = false)
    protected String ticketType;

    // Default constructor
    public Ticket() {
        this.status = "Open";
    }

    // Constructor with parameters
    public Ticket(String title, String description, String ticketType) {
        this.title = title;
        this.description = description;
        this.status = "Open";
        this.ticketType = ticketType;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    // Abstract method for displaying ticket info
    public abstract String getDetails();

    @Override
    public String toString() {
        return getDetails();
    }
}
