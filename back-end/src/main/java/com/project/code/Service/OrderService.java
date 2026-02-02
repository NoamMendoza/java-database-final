package com.project.code.Service;

import com.project.code.Model.*;
import com.project.code.Repo.*;
import com.project.code.DTO.PlaceOrderRequestDTO;
import com.project.code.DTO.OrderItemRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void saveOrder(PlaceOrderRequestDTO placeOrderRequest) {
        // 2. Retrieve or Create the Customer
        Customer customer = customerRepository.findByEmail(placeOrderRequest.getEmail());
        if (customer == null) {
            customer = new Customer();
            customer.setName(placeOrderRequest.getCustomerName());
            customer.setEmail(placeOrderRequest.getEmail());
            customer.setPhone(placeOrderRequest.getPhone());
            customer = customerRepository.save(customer);
        }

        // 3. Retrieve the Store
        Store store = storeRepository.findById(placeOrderRequest.getStoreId()).orElse(null);
        if (store == null) {
            throw new RuntimeException("Store not found with ID: " + placeOrderRequest.getStoreId());
        }

        // 4. Create OrderDetails
        OrderDetails orderDetails = new OrderDetails(
            customer, 
            store, 
            placeOrderRequest.getTotalPrice(), 
            LocalDateTime.now()
        );
        orderDetails = orderDetailsRepository.save(orderDetails);

        // 5. Create and Save OrderItems
        for (OrderItemRequestDTO itemRequest : placeOrderRequest.getOrderItems()) {
            // Find Product
            Product product = productRepository.findById(itemRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found: " + itemRequest.getProductId()));

            // Update Inventory
            Inventory inventory = inventoryRepository.findByProductIdAndStoreId(
                product.getId(), 
                store.getId()
            );

            if (inventory == null || inventory.getStockLevel() < itemRequest.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            // Deduct stock and save
            inventory.setStockLevel(inventory.getStockLevel() - itemRequest.getQuantity());
            inventoryRepository.save(inventory);

            // Create and save OrderItem
            OrderItem orderItem = new OrderItem(
                orderDetails, 
                product, 
                itemRequest.getQuantity(), 
                itemRequest.getPrice()
            );
            orderItemRepository.save(orderItem);
        }
    }
}