package com.business.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Customer extends Person {
    private List<Order> orderHistory;
    private double totalSpent;
    private String customerType; // Regular, Premium, etc.
    
    // Constructors
    public Customer() {
        super();
        this.orderHistory = new ArrayList<>();
    }
    
    public Customer(String name, String address, String phone, String email) {
        super(name, address, phone, email);
        this.orderHistory = new ArrayList<>();
        this.customerType = "Regular";
    }
    
    // Overridden methods
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Customer Type: " + customerType);
        System.out.println("Total Orders: " + orderHistory.size());
        System.out.println("Lifetime Value: $" + totalSpent);
    }
    
    protected String generateID() {
        return "CUST-" + UUID.randomUUID().toString().substring(0, 8);
    }
    
    // Customer-specific methods
    public void addOrder(Order order) {
        orderHistory.add(order);
        totalSpent += order.getTotal();
        updateCustomerType();
    }
    
    private void updateCustomerType() {
        if (totalSpent > 10000) {
            customerType = "Premium";
        } else if (totalSpent > 5000) {
            customerType = "Gold";
        } else {
            customerType = "Regular";
        }
    }
    
    // Getters and setters
    public List<Order> getOrderHistory() { return orderHistory; }
    public void setOrderHistory(List<Order> orderHistory) { this.orderHistory = orderHistory; }
    public double getTotalSpent() { return totalSpent; }
    public void setTotalSpent(double totalSpent) { this.totalSpent = totalSpent; }
    public String getCustomerType() { return customerType; }
    public void setCustomerType(String customerType) { this.customerType = customerType; }
} 