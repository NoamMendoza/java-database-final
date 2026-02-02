package com.project.code.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;

/**
 * Entity representing a Customer in the system.
 */
@Entity
public class Customer {

    // 1. Add 'id' field: 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 2. Add 'name' field:
    @NotNull(message = "Name cannot be null")
    private String name;

    // 3. Add 'email' field:
    @NotNull(message = "Email cannot be null")
    private String email;

    // 4. Add 'phone' field:
    @NotNull(message = "Phone cannot be null")
    private String phone;

    // 5. Add one-to-many relationship with orders:
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<OrderDetails> orders;

    // 6. Getters and Setters:
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<OrderDetails> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDetails> orders) {
        this.orders = orders;
    }
}