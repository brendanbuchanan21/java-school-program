package com.example.demo.repository;

import com.example.demo.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Ticket entity
 * Provides automatic CRUD operations for database persistence
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
