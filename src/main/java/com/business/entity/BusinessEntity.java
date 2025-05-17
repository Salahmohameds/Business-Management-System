package com.business.entity;

import java.util.Date;

public abstract class BusinessEntity {
    protected Long id;
    protected String name;
    protected Date createdDate;
    protected boolean isActive;
    
    // Constructors (overloaded)
    public BusinessEntity() {
        this.createdDate = new Date();
        this.isActive = true;
        this.id = null;
    }
    
    public BusinessEntity(String name) {
        this();
        this.name = name;
    }
    
    // Abstract methods
    public abstract void displayDetails();
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Date getCreatedDate() { return createdDate; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
} 