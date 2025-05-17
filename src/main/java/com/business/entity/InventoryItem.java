package com.business.entity;

public class InventoryItem extends BusinessEntity {
    private double price;
    private int stock;
    private int reorderPoint;
    private String category;
    
    // Constructors
    public InventoryItem() {
        super();
    }
    
    public InventoryItem(String name, String category, double price, int stock, int reorderPoint) {
        super(name);
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.reorderPoint = reorderPoint;
    }
    
    // Overridden methods
    @Override
    public void displayDetails() {
        System.out.println("Inventory Item ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Category: " + category);
        System.out.println("Price: $" + price);
        System.out.println("Stock: " + stock);
        System.out.println("Reorder Point: " + reorderPoint);
        System.out.println("Status: " + (stock <= reorderPoint ? "Low Stock" : "In Stock"));
    }
    
    // Inventory-specific methods
    public void updateStock(int amount) {
        this.stock += amount;
    }
    
    public boolean isLowStock() {
        return stock <= reorderPoint;
    }
    
    // Getters and setters
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public int getReorderPoint() { return reorderPoint; }
    public void setReorderPoint(int reorderPoint) { this.reorderPoint = reorderPoint; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
} 