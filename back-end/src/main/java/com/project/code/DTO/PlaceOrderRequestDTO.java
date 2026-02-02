package com.project.code.DTO;

import java.util.List;

public class PlaceOrderRequestDTO {
    private String customerName;
    private String email;
    private String phone;
    private Long storeId;
    private Double totalPrice;
    private List<OrderItemRequestDTO> orderItems;

    // Getters y Setters
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Long getStoreId() { return storeId; }
    public void setStoreId(Long storeId) { this.storeId = storeId; }
    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
    public List<OrderItemRequestDTO> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItemRequestDTO> orderItems) { this.orderItems = orderItems; }
}