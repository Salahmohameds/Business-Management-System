package com.business.entity;

import java.util.Date;

public abstract class Employee extends Person {
    protected String department;
    protected double salary;
    protected Date hireDate;
    
    // Constructors
    public Employee() { 
        super(); 
    }
    
    public Employee(String name, String address, String phone, String email, 
                   String department, double salary) {
        super(name, address, phone, email);
        this.department = department;
        this.salary = salary;
        this.hireDate = new Date();
    }
    
    // Overridden method
    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Department: " + department);
        System.out.println("Base Salary: $" + salary);
    }
    
    // Abstract method
    public abstract double calculateSalary();
    
    // Getters and setters
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    public Date getHireDate() { return hireDate; }
    public void setHireDate(Date hireDate) { this.hireDate = hireDate; }
} 