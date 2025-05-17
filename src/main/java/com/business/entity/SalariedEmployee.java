package com.business.entity;

public class SalariedEmployee extends Employee {
    private double bonus;
    private double deductions;
    
    // Constructors
    public SalariedEmployee() { 
        super(); 
    }
    
    public SalariedEmployee(String name, String address, String phone, String email,
                          String department, double salary, double bonus, double deductions) {
        super(name, address, phone, email, department, salary);
        this.bonus = bonus;
        this.deductions = deductions;
    }
    
    // Overridden methods
    public double calculateBonus() {
        return bonus;
    }
    
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Type: Salaried Employee");
        System.out.println("Net Salary: $" + getNetSalary());
    }
    
    protected String generateID() {
        return "SAL-" + java.util.UUID.randomUUID().toString().substring(0, 8);
    }
    
    // Additional method
    public double getNetSalary() {
        return salary + bonus - deductions;
    }
    
    // Getters and setters
    public double getBonus() { return bonus; }
    public void setBonus(double bonus) { this.bonus = bonus; }
    public double getDeductions() { return deductions; }
    public void setDeductions(double deductions) { this.deductions = deductions; }
} 