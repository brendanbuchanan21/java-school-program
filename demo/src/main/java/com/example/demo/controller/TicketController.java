package com.example.demo.controller;

import com.example.demo.model.Ticket;
import com.example.demo.model.Task;
import com.example.demo.model.Bug;
import com.example.demo.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller class for handling ticket-related endpoints
 * Updated to use JPA Repository for database persistence instead of file I/O
 */
@RestController
public class TicketController {
    
    @Autowired
    private TicketRepository ticketRepository;

    /**
     * Endpoint to create a new ticket
     * Uses @PostMapping for RESTful style - POST creates new data
     * Saves ticket to database using JPA Repository
     */
    @PostMapping("/createTicket")
    public String createTicket(
            @RequestParam String type,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam(required = false) String dueDate,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String severity,
            @RequestParam(required = false) String steps) {
        
        try {
            Ticket ticket;

            // Create the appropriate ticket type based on the 'type' parameter
            if (type.equalsIgnoreCase("task")) {
                ticket = new Task(title, description, dueDate, priority != null ? priority : "Medium");
            } else if (type.equalsIgnoreCase("bug")) {
                ticket = new Bug(title, description, severity != null ? severity : "Medium", steps);
            } else {
                return "Error: Invalid ticket type. Use 'task' or 'bug'.";
            }

            // Save the ticket to database using Repository
            ticketRepository.save(ticket);

            return "Ticket created successfully!\n" + ticket.toString();

        } catch (Exception e) {
            return "Error creating ticket: " + e.getMessage();
        }
    }

    /**
     * Endpoint to retrieve a ticket by ID
     * Uses @GetMapping for RESTful style - GET retrieves data
     * Reads from database using JPA Repository
     */
    @GetMapping("/getTicket")
    public String getTicket(@RequestParam Integer id) {
        try {
            Optional<Ticket> ticketOpt = ticketRepository.findById(id);
            
            if (ticketOpt.isPresent()) {
                return ticketOpt.get().toString();
            } else {
                return "Error: Ticket with ID " + id + " not found.";
            }

        } catch (Exception e) {
            return "Error retrieving ticket: " + e.getMessage();
        }
    }

    /**
     * Endpoint to retrieve all tickets
     * Uses @GetMapping for RESTful style
     * Returns all tickets from database
     */
    @GetMapping("/getAllTickets")
    public String getAllTickets() {
        try {
            List<Ticket> tickets = ticketRepository.findAll();
            
            if (tickets.isEmpty()) {
                return "No tickets found.";
            }
            
            StringBuilder result = new StringBuilder("All Tickets:\n\n");
            for (Ticket ticket : tickets) {
                result.append(ticket.toString()).append("\n---\n\n");
            }
            
            return result.toString();

        } catch (Exception e) {
            return "Error retrieving tickets: " + e.getMessage();
        }
    }

    /**
     * Endpoint to delete a ticket by ID
     * Uses @DeleteMapping for RESTful style - DELETE removes data
     * Deletes from database using JPA Repository
     */
    @DeleteMapping("/deleteTicket")
    public String deleteTicket(@RequestParam Integer id) {
        try {
            if (ticketRepository.existsById(id)) {
                ticketRepository.deleteById(id);
                return "Ticket with ID " + id + " has been successfully deleted.";
            } else {
                return "Error: Ticket with ID " + id + " not found. Cannot delete.";
            }

        } catch (Exception e) {
            return "Error deleting ticket: " + e.getMessage();
        }
    }
}
