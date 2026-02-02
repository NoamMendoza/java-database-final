package com.project.code.Service;

import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceClass {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    // 1. validateInventory Method
    public boolean validateInventory(Inventory inventory) {
        Inventory existingInventory = inventoryRepository.findByProductIdAndStoreId(
                inventory.getProduct().getId(),
                inventory.getStore().getId()
        );
        // Returns false if inventory exists, otherwise true
        return existingInventory == null;
    }

    // 2. validateProduct Method
    public boolean validateProduct(Product product) {
        Product existingProduct = productRepository.findByName(product.getName());
        // Returns false if a product with the same name exists, otherwise true
        return existingProduct == null;
    }

    // 3. ValidateProductId Method
    public boolean ValidateProductId(long id) {
        boolean exists = productRepository.existsById(id);
        // Returns false if the product does not exist, otherwise true
        return exists;
    }

    // 4. getInventoryId Method
    public Inventory getInventoryId(Inventory inventory) {
        // Fetches the inventory record for a given product and store combination
        return inventoryRepository.findByProductIdAndStoreId(
                inventory.getProduct().getId(),
                inventory.getStore().getId()
        );
    }
}