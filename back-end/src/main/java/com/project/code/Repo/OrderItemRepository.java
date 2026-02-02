package com.project.code.Repo;

import com.project.code.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for OrderItem entity.
 * Inherits standard CRUD operations from JpaRepository.
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // Default CRUD operations (save, delete, update, findById, etc.) 
    // are available out of the box by extending JpaRepository.
}