package com.project.code.Repo;

import com.project.code.Model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // 2. Add custom query methods:

    // findByProductIdandStoreId: find an inventory record by its product ID and store ID.
    Inventory findByProductIdAndStoreId(Long productId, Long storeId);

    // findByStore_Id: find a list of inventory records for a specific store.
    List<Inventory> findByStore_Id(Long storeId);

    // deleteByProductId: delete all inventory records related to a specific product ID.
    @Modifying
    @Transactional
    void deleteByProductId(Long productId);
}