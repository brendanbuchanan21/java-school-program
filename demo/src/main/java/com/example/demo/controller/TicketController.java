package com.example.demo.controller;

import com.example.demo.model.Ticket;
import com.example.demo.model.Task;
import com.example.demo.model.Bug;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Controller class for handling ticket-related endpoints
 * Provides endpoints for creating and retrieving tickets
 */
@RestController
public class TicketController {
    
    private int ticketIdCounter = 1; // Counter for generating unique ticket IDs
    private static final String TICKET_FOLDER = "demo/tickets/"; // Folder to store ticket files

    /**
     * Endpoint to create a new ticket
     * URL: /createTicket?type=task&title=...&description=...&dueDate=...&priority=...
     * or
     * URL: /createTicket?type=bug&title=...&description=...&severity=...&steps=...
     */
    @RequestMapping("/createTicket")
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
            int id = ticketIdCounter++;

            // Create the appropriate ticket type based on the 'type' parameter
            if (type.equalsIgnoreCase("task")) {
                ticket = new Task(id, title, description, dueDate, priority != null ? priority : "Medium");
            } else if (type.equalsIgnoreCase("bug")) {
                ticket = new Bug(id, title, description, severity != null ? severity : "Medium", steps);
            } else {
                return "Error: Invalid ticket type. Use 'task' or 'bug'.";
            }

            // Save the ticket to a file
            saveTicketToFile(ticket);

            return "Ticket created successfully!\n" + ticket.toString();

        } catch (Exception e) {
            return "Error creating ticket: " + e.getMessage();
        }
    }

    /**
     * Endpoint to retrieve a ticket by ID
     * URL: /getTicket?id=1
     */
    @RequestMapping("/getTicket")
    public String getTicket(@RequestParam int id) {
        try {
            String filename = TICKET_FOLDER + "ticket_" + id + ".txt";
            
            // Check if file exists
            if (!Files.exists(Paths.get(filename))) {
                return "Error: Ticket with ID " + id + " not found.";
            }

            // Read the file and return its contents
            String content = new String(Files.readAllBytes(Paths.get(filename)));
            return content;

        } catch (Exception e) {
            return "Error retrieving ticket: " + e.getMessage();
        }
    }

    /**
     * Helper method to save a ticket to a file
     */
    private void saveTicketToFile(Ticket ticket) throws IOException {
        // Create the tickets folder if it doesn't exist
        File folder = new File(TICKET_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Create the filename based on ticket ID
        String filename = TICKET_FOLDER + "ticket_" + ticket.getId() + ".txt";

        // write the ticket data to the file
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(ticket.toFileString());
        }
    }
}
