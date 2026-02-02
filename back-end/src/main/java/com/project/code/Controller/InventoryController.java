package com.project.code.Controller;

import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import com.project.code.Service.ServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ServiceClass serviceClass;

    // 3. updateInventory Method
    @PutMapping("/update")
    public Map<String, String> updateInventory(@RequestBody CombinedRequest combinedRequest) {
        Map<String, String> response = new HashMap<>();
        Product product = combinedRequest.getProduct();
        Inventory inventory = combinedRequest.getInventory();

        if (serviceClass.ValidateProductId(product.getId())) {
            Inventory existingInventory = serviceClass.getInventoryId(inventory);
            if (existingInventory != null) {
                existingInventory.setStockLevel(inventory.getStockLevel());
                inventoryRepository.save(existingInventory);
                response.put("message", "Inventory updated successfully");
            } else {
                response.put("message", "No data available for this inventory");
            }
        } else {
            response.put("message", "Invalid Product ID");
        }
        return response;
    }

    // 4. saveInventory Method
    @PostMapping("/save")
    public Map<String, String> saveInventory(@RequestBody Inventory inventory) {
        Map<String, String> response = new HashMap<>();
        // serviceClass.validateInventory returns false if it exists
        if (!serviceClass.validateInventory(inventory)) {
            response.put("message", "Inventory already exists");
        } else {
            inventoryRepository.save(inventory);
            response.put("message", "Inventory saved successfully");
        }
        return response;
    }

    // 5. getAllProducts Method
    @GetMapping("/products/{storeId}")
    public Map<String, List<Inventory>> getAllProducts(@PathVariable Long storeId) {
        Map<String, List<Inventory>> response = new HashMap<>();
        List<Inventory> inventoryList = inventoryRepository.findByStore_Id(storeId);
        response.put("products", inventoryList);
        return response;
    }

    // 6. getProductName Method
    @GetMapping("/filter")
    public Map<String, List<Product>> getProductName(@RequestParam String category, @RequestParam String name) {
        Map<String, List<Product>> response = new HashMap<>();
        List<Product> products;
        
        if (category.equals("null") && !name.equals("null")) {
            products = List.of(productRepository.findByName(name));
        } else if (!category.equals("null") && name.equals("null")) {
            products = productRepository.findByCategory(category);
        } else {
            // Assume logic for both or handle as needed
            products = productRepository.findAll(); 
        }
        
        response.put("product", products);
        return response;
    }

    // 7. searchProduct Method
    @GetMapping("/search")
    public Map<String, List<Product>> searchProduct(@RequestParam String name, @RequestParam Long storeId) {
        Map<String, List<Product>> response = new HashMap<>();
        List<Product> products = productRepository.findByNameLike(storeId, name);
        response.put("product", products);
        return response;
    }

    // 8. removeProduct Method
    @DeleteMapping("/remove/{id}")
    public Map<String, String> removeProduct(@PathVariable long id) {
        Map<String, String> response = new HashMap<>();
        if (serviceClass.ValidateProductId(id)) {
            inventoryRepository.deleteByProductId(id);
            productRepository.deleteById(id);
            response.put("message", "Product and related inventory deleted successfully");
        } else {
            response.put("message", "Product not found");
        }
        return response;
    }

    // 9. validateQuantity Method
    @GetMapping("/validate-stock")
    public boolean validateQuantity(@RequestParam Long productId, @RequestParam Long storeId, @RequestParam Integer quantity) {
        Inventory inventory = inventoryRepository.findByProductIdAndStoreId(productId, storeId);
        if (inventory != null && inventory.getStockLevel() >= quantity) {
            return true;
        }
        return false;
    }
}

// Inner helper class for CombinedRequest
class CombinedRequest {
    private Product product;
    private Inventory inventory;

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Inventory getInventory() { return inventory; }
    public void setInventory(Inventory inventory) { this.inventory = inventory; }
}