package com.example.demo.repository;

import com.example.demo.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Ticket entity
 * Provides automatic CRUD operations for database persistence
 * Added method to find multiple tickets by list of IDs
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    
    /**
     * Find all tickets matching the provided list of IDs
     * Spring Data JPA automatically implements this method based on the naming convention
     * @param ids List of ticket IDs to retrieve
     * @return List of Ticket objects matching the provided IDs
     */
    List<Ticket> findByIdIn(List<Integer> ids);
}
