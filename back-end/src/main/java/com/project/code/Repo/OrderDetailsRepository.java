package com.project.code.Repo;

import com.project.code.Model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for OrderDetails entity.
 * Inherits standard CRUD operations from JpaRepository.
 */
@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
    // Basic CRUD functionality (save, findById, findAll, delete, etc.) 
    // is inherited from JpaRepository.
}