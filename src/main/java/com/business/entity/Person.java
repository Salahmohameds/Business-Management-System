package com.business.entity;

public abstract class Person {
    protected Long id;
    protected String name;
    protected String address;
    protected String phone;
    protected String email;
    
    public Person() {
        this.id = null;
    }
    
    public Person(String name, String address, String phone, String email) {
        this.id = null;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }
    
    public void displayDetails() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
} 