package com.business.entity;

import com.business.exception.InsufficientStockException;
import com.business.exception.PaymentException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order extends BusinessEntity {
    private Customer customer;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private List<OrderItem> items;
    private double total;
    
    public Order() {
        super();
        this.orderDate = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
        this.items = new ArrayList<>();
        this.total = 0.0;
    }
    
    public Order(Customer customer, LocalDateTime orderDate) {
        this();
        this.customer = customer;
        this.orderDate = orderDate;
    }
    
    public void addItem(InventoryItem item, int quantity) {
        OrderItem orderItem = new OrderItem(this, item, quantity);
        items.add(orderItem);
        total += orderItem.getSubtotal();
    }
    
    public void removeItem(OrderItem item) {
        items.remove(item);
        total -= item.getSubtotal();
    }
    
    public double getTotal() {
        return total;
    }
    
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    
    public OrderStatus getStatus() {
        return status;
    }
    
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public List<OrderItem> getItems() {
        return items;
    }
    
    @Override
    public void displayDetails() {
        System.out.println("Order ID: " + getId());
        System.out.println("Customer: " + customer.getName());
        System.out.println("Date: " + orderDate);
        System.out.println("Status: " + status);
        System.out.println("Total: $" + total);
        System.out.println("Items:");
        for (OrderItem item : items) {
            System.out.println("  - " + item.getProduct().getName() + 
                             " x " + item.getQuantity() + 
                             " = $" + item.getSubtotal());
        }
    }
    
    // Order processing methods
    public void processPayment(double amount) throws PaymentException {
        double total = getTotal();
        if (amount < total) {
            throw new PaymentException("Insufficient payment. Amount due: $" + (total - amount));
        }
        this.status = OrderStatus.COMPLETED;
    }
} 