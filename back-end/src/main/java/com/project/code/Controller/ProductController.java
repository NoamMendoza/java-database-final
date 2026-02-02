package com.project.code.Controller;

import com.project.code.Model.Product;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import com.project.code.Service.ServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ServiceClass serviceClass;

    @Autowired
    private InventoryRepository inventoryRepository;

    // 3. addProduct Method
    @PostMapping
    public Map<String, String> addProduct(@RequestBody Product product) {
        Map<String, String> response = new HashMap<>();
        try {
            if (serviceClass.validateProduct(product)) {
                productRepository.save(product);
                response.put("message", "Product added successfully");
            } else {
                response.put("message", "Product already exists");
            }
        } catch (DataIntegrityViolationException e) {
            response.put("message", "Error: SKU must be unique");
        }
        return response;
    }

    // 4. getProductbyId Method
    @GetMapping("/product/{id}")
    public Map<String, Object> getProductbyId(@PathVariable long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<Product> product = productRepository.findById(id);
        response.put("products", product.orElse(null));
        return response;
    }

    // 5. updateProduct Method
    @PutMapping
    public Map<String, String> updateProduct(@RequestBody Product product) {
        Map<String, String> response = new HashMap<>();
        productRepository.save(product);
        response.put("message", "Product updated successfully");
        return response;
    }

    // 6. filterbyCategoryProduct Method
    @GetMapping("/category/{name}/{category}")
    public Map<String, Object> filterbyCategoryProduct(@PathVariable String name, @PathVariable String category) {
        Map<String, Object> response = new HashMap<>();
        List<Product> products;

        if (name.equals("null") && !category.equals("null")) {
            products = productRepository.findByCategory(category);
        } else if (!name.equals("null") && category.equals("null")) {
            products = List.of(productRepository.findByName(name));
        } else {
            products = productRepository.findAll();
        }

        response.put("products", products);
        return response;
    }

    // 7. listProduct Method
    @GetMapping
    public Map<String, Object> listProduct() {
        Map<String, Object> response = new HashMap<>();
        response.put("products", productRepository.findAll());
        return response;
    }

    // 8. getProductbyCategoryAndStoreId Method
    @GetMapping("filter/{category}/{storeid}")
    public Map<String, Object> getProductbyCategoryAndStoreId(@PathVariable String category, @PathVariable Long storeid) {
        Map<String, Object> response = new HashMap<>();
        // Assuming custom implementation in repository to filter by category
        List<Product> products = productRepository.findByCategory(category);
        response.put("product", products);
        return response;
    }

    // 9. deleteProduct Method
    @DeleteMapping("/{id}")
    public Map<String, String> deleteProduct(@PathVariable long id) {
        Map<String, String> response = new HashMap<>();
        if (serviceClass.ValidateProductId(id)) {
            inventoryRepository.deleteByProductId(id);
            productRepository.deleteById(id);
            response.put("message", "Product deleted successfully");
        } else {
            response.put("message", "Product not found");
        }
        return response;
    }

    // 10. searchProduct Method
    @GetMapping("/searchProduct/{name}")
    public Map<String, Object> searchProduct(@PathVariable String name) {
        Map<String, Object> response = new HashMap<>();
        // Reusing findByName logic or custom substring search if available in Repo
        Product product = productRepository.findByName(name);
        response.put("products", product);
        return response;
    }
}