package com.business.util;

import com.business.entity.*;
import com.business.storage.DataStorage;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class DataInitializer {
    private final DataStorage dataStorage;
    
    public DataInitializer() {
        this.dataStorage = DataStorage.getInstance();
    }
    
    public void initializeData() {
        // Clear existing data
        dataStorage.clearAll();
        
        // Initialize employees
        initializeEmployees();
        
        // Initialize inventory
        initializeInventory();
        
        // Initialize customers
        initializeCustomers();
        
        // Initialize orders
        initializeOrders();
    }
    
    private void initializeEmployees() {
        // Salaried Employees
        SalariedEmployee ceo = new SalariedEmployee(
            "John Smith", "123 Executive Ave", "555-0101", "john.smith@company.com",
            "Executive", 150000.0, 50000.0, 20000.0
        );
        
        SalariedEmployee cto = new SalariedEmployee(
            "Sarah Johnson", "456 Tech Blvd", "555-0102", "sarah.j@company.com",
            "IT", 120000.0, 30000.0, 15000.0
        );
        
        SalariedEmployee hrManager = new SalariedEmployee(
            "Michael Brown", "789 HR Street", "555-0103", "michael.b@company.com",
            "HR", 90000.0, 15000.0, 10000.0
        );
        
        // Hourly Employees
        HourlyEmployee salesRep1 = new HourlyEmployee(
            "Emily Davis", "101 Sales Rd", "555-0104", "emily.d@company.com",
            "Sales", 25.0
        );
        salesRep1.addHours(160);
        
        HourlyEmployee salesRep2 = new HourlyEmployee(
            "David Wilson", "202 Sales Rd", "555-0105", "david.w@company.com",
            "Sales", 22.0
        );
        salesRep2.addHours(145);
        
        HourlyEmployee warehouseWorker = new HourlyEmployee(
            "Lisa Anderson", "303 Warehouse Ave", "555-0106", "lisa.a@company.com",
            "Warehouse", 18.0
        );
        warehouseWorker.addHours(175);
        
        // Save employees
        List<Employee> employees = Arrays.asList(ceo, cto, hrManager, salesRep1, salesRep2, warehouseWorker);
        employees.forEach(dataStorage::saveEmployee);
    }
    
    private void initializeInventory() {
        // Electronics
        InventoryItem laptop = new InventoryItem(
            "Dell XPS 15", "Electronics", 1299.99, 50, 10
        );
        
        InventoryItem smartphone = new InventoryItem(
            "iPhone 14 Pro", "Electronics", 999.99, 100, 20
        );
        
        InventoryItem monitor = new InventoryItem(
            "LG 27\" 4K Monitor", "Electronics", 399.99, 30, 5
        );
        
        // Office Supplies
        InventoryItem printer = new InventoryItem(
            "HP LaserJet Pro", "Office Supplies", 299.99, 25, 5
        );
        
        InventoryItem paper = new InventoryItem(
            "A4 Paper (500 sheets)", "Office Supplies", 4.99, 1000, 100
        );
        
        InventoryItem pens = new InventoryItem(
            "Ballpoint Pens (12 pack)", "Office Supplies", 9.99, 500, 50
        );
        
        // Furniture
        InventoryItem desk = new InventoryItem(
            "Executive Desk", "Furniture", 599.99, 20, 3
        );
        
        InventoryItem chair = new InventoryItem(
            "Ergonomic Office Chair", "Furniture", 299.99, 30, 5
        );
        
        // Save inventory items
        List<InventoryItem> items = Arrays.asList(
            laptop, smartphone, monitor, printer, paper, pens, desk, chair
        );
        items.forEach(dataStorage::saveInventoryItem);
    }
    
    private void initializeCustomers() {
        // Business Customers
        Customer business1 = new Customer(
            "Tech Solutions Inc.", "1000 Business Park", "555-0201", "contact@techsolutions.com"
        );
        
        Customer business2 = new Customer(
            "Global Enterprises", "2000 Corporate Plaza", "555-0202", "info@globalenterprises.com"
        );
        
        // Individual Customers
        Customer individual1 = new Customer(
            "Robert Johnson", "3000 Main St", "555-0203", "robert.j@email.com"
        );
        
        Customer individual2 = new Customer(
            "Maria Garcia", "4000 Oak Ave", "555-0204", "maria.g@email.com"
        );
        
        // Save customers
        List<Customer> customers = Arrays.asList(business1, business2, individual1, individual2);
        customers.forEach(dataStorage::saveCustomer);
    }
    
    private void initializeOrders() {
        // Get some sample data
        Customer business1 = dataStorage.findAllCustomers().get(0);
        Customer individual1 = dataStorage.findAllCustomers().get(2);
        
        InventoryItem laptop = dataStorage.findAllInventoryItems().get(0);
        InventoryItem monitor = dataStorage.findAllInventoryItems().get(2);
        InventoryItem desk = dataStorage.findAllInventoryItems().get(6);
        InventoryItem chair = dataStorage.findAllInventoryItems().get(7);
        
        // Create orders
        Order order1 = new Order(business1, LocalDateTime.now().minusDays(5));
        order1.addItem(laptop, 5);
        order1.addItem(monitor, 5);
        order1.setStatus(OrderStatus.COMPLETED);
        
        Order order2 = new Order(individual1, LocalDateTime.now().minusDays(2));
        order2.addItem(desk, 1);
        order2.addItem(chair, 1);
        order2.setStatus(OrderStatus.PENDING);
        
        // Save orders
        List<Order> orders = Arrays.asList(order1, order2);
        orders.forEach(dataStorage::saveOrder);
        
        // Update customer order history
        business1.addOrder(order1);
        individual1.addOrder(order2);
        
        // Update inventory stock
        dataStorage.updateInventoryStock(laptop.getId(), laptop.getStock() - 5);
        dataStorage.updateInventoryStock(monitor.getId(), monitor.getStock() - 5);
        dataStorage.updateInventoryStock(desk.getId(), desk.getStock() - 1);
        dataStorage.updateInventoryStock(chair.getId(), chair.getStock() - 1);
    }
} 