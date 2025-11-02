package com.example.demo.controller;

import com.example.demo.model.Ticket;
import com.example.demo.model.Task;
import com.example.demo.model.Bug;
import com.example.demo.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller class for handling ticket-related endpoints
 * Updated to use Caffeine caching for improved performance
 */
@RestController
public class TicketController {
    
    @Autowired
    private TicketRepository ticketRepository;

    /**
     * Endpoint to create a new ticket
     * Saves ticket to database using JPA Repository
     */
    @PostMapping("/createTicket")
    @CacheEvict(value = "tickets", allEntries = true)
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
     * Uses @Cacheable to cache individual ticket responses
     * Subsequent requests for the same ID will be served from cache
     */
    @GetMapping("/getTicket")
    @Cacheable(value = "tickets", key = "#id")
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
     * Uses @Cacheable to cache the entire ticket list
     * Cache is shared across all requests until it expires or is evicted :')
     */
    @GetMapping("/getAllTickets")
    @Cacheable(value = "tickets", key = "'allTickets'")
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
     * Endpoint to mark multiple tickets as complete
     * Uses @CacheEvict to clear cache when tickets are updated
     * Ensures subsequent requests get fresh data from database
     */
    @PostMapping("/completeTickets")
    @CacheEvict(value = "tickets", allEntries = true)
    public String completeTickets(@RequestParam List<Integer> ids) {
        try {
            // Retrieve all tickets with the provided IDs using Repository method
            List<Ticket> tickets = ticketRepository.findByIdIn(ids);
            
            if (tickets.isEmpty()) {
                return "Error: No tickets found with the provided IDs.";
            }
            
            // Use lambda function with forEach to update each ticket's status
            tickets.forEach(ticket -> ticket.setStatus("Complete"));
            
            // Save all updated tickets back to the database
            ticketRepository.saveAll(tickets);
            
            // Build response message showing which tickets were completed
            StringBuilder result = new StringBuilder("Successfully marked " + tickets.size() + " ticket(s) as complete:\n\n");
            tickets.forEach(ticket -> result.append("ID: ").append(ticket.getId())
                                            .append(" - ").append(ticket.getTitle())
                                            .append(" - Status: ").append(ticket.getStatus())
                                            .append("\n"));
            
            return result.toString();

        } catch (Exception e) {
            return "Error completing tickets: " + e.getMessage();
        }
    }

    /**
     * Endpoint to delete a ticket by ID
     * Uses @CacheEvict to clear cache when a ticket is deleted
     */
    @DeleteMapping("/deleteTicket")
    @CacheEvict(value = "tickets", allEntries = true)
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

    /**
     * Endpoint to manually refresh/clear all caches
     * Useful for forcing fresh data retrieval from database
     */
    @PostMapping("/refreshCache")
    @CacheEvict(value = "tickets", allEntries = true)
    public String refreshCache() {
        return "Cache has been successfully refreshed. All cached ticket data has been cleared.";
    }
}