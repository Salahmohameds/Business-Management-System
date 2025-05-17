package com.business.entity;

public class OrderItem extends BusinessEntity {
    private Order order;
    private InventoryItem product;
    private int quantity;
    private double price;
    
    public OrderItem() {
        super();
    }
    
    public OrderItem(Order order, InventoryItem product, int quantity) {
        super();
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = product.getPrice();
    }
    
    public double getSubtotal() {
        return price * quantity;
    }
    
    public Order getOrder() {
        return order;
    }
    
    public InventoryItem getProduct() {
        return product;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    @Override
    public void displayDetails() {
        System.out.println("Order Item ID: " + getId());
        System.out.println("Product: " + product.getName());
        System.out.println("Quantity: " + quantity);
        System.out.println("Price: $" + price);
        System.out.println("Subtotal: $" + getSubtotal());
    }
} 