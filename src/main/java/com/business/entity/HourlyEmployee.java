package com.business.entity;

public class HourlyEmployee extends Employee {
    private double hourlyRate;
    private int hoursWorked;
    
    // Constructors
    public HourlyEmployee() { 
        super(); 
    }
    
    public HourlyEmployee(String name, String address, String phone, String email,
                         String department, double hourlyRate) {
        super(name, address, phone, email, department, 0);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = 0;
    }
    
    // Overridden methods
    public double calculateBonus() {
        return hoursWorked > 160 ? (hoursWorked - 160) * hourlyRate * 0.5 : 0;
    }
    
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Type: Hourly Employee");
        System.out.println("Hourly Rate: $" + hourlyRate);
        System.out.println("Hours Worked: " + hoursWorked);
        System.out.println("Current Pay: $" + calculatePay());
    }
    
    protected String generateID() {
        return "HRL-" + java.util.UUID.randomUUID().toString().substring(0, 8);
    }
    
    // Additional methods
    public void addHours(int hours) {
        this.hoursWorked += hours;
    }
    
    public double calculatePay() {
        return hoursWorked * hourlyRate + calculateBonus();
    }
    
    // Getters and setters
    public double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(double hourlyRate) { this.hourlyRate = hourlyRate; }
    public int getHoursWorked() { return hoursWorked; }
    public void setHoursWorked(int hoursWorked) { this.hoursWorked = hoursWorked; }
} 