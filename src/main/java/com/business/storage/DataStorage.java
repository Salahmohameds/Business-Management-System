package com.business.storage;

import com.business.entity.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.time.LocalDateTime;

public class DataStorage {
    private static DataStorage instance;
    
    // Storage maps
    private final Map<Long, Employee> employees;
    private final Map<Long, Order> orders;
    private final Map<Long, InventoryItem> inventoryItems;
    private final Map<Long, Customer> customers;
    
    // ID generators
    private final AtomicLong employeeIdGenerator;
    private final AtomicLong orderIdGenerator;
    private final AtomicLong inventoryIdGenerator;
    private final AtomicLong customerIdGenerator;
    
    private DataStorage() {
        // Using ConcurrentHashMap for thread safety
        employees = new ConcurrentHashMap<>();
        orders = new ConcurrentHashMap<>();
        inventoryItems = new ConcurrentHashMap<>();
        customers = new ConcurrentHashMap<>();
        
        // Initialize ID generators
        employeeIdGenerator = new AtomicLong(1);
        orderIdGenerator = new AtomicLong(1);
        inventoryIdGenerator = new AtomicLong(1);
        customerIdGenerator = new AtomicLong(1);
    }
    
    public static synchronized DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }
    
    // Employee operations
    public Employee saveEmployee(Employee employee) {
        if (employee.getId() == null) {
            employee.setId(employeeIdGenerator.getAndIncrement());
        }
        employees.put(employee.getId(), employee);
        return employee;
    }
    
    public Employee findEmployeeById(Long id) {
        return employees.get(id);
    }
    
    public List<Employee> findAllEmployees() {
        return new ArrayList<>(employees.values());
    }
    
    public List<Employee> findEmployeesByDepartment(String department) {
        return employees.values().stream()
            .filter(e -> e.getDepartment().equals(department))
            .toList();
    }
    
    public void deleteEmployee(Long id) {
        employees.remove(id);
    }
    
    // Order operations
    public Order saveOrder(Order order) {
        if (order.getId() == null) {
            order.setId(orderIdGenerator.getAndIncrement());
        }
        orders.put(order.getId(), order);
        return order;
    }
    
    public Order findOrderById(Long id) {
        return orders.get(id);
    }
    
    public List<Order> findAllOrders() {
        return new ArrayList<>(orders.values());
    }
    
    public List<Order> findOrdersByCustomer(Long customerId) {
        return orders.values().stream()
            .filter(o -> o.getCustomer().getId().equals(customerId))
            .toList();
    }
    
    public List<Order> findOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orders.values().stream()
            .filter(o -> o.getOrderDate().compareTo(startDate) >= 0 && o.getOrderDate().compareTo(endDate) <= 0)
            .toList();
    }
    
    public void deleteOrder(Long id) {
        orders.remove(id);
    }
    
    // Inventory operations
    public InventoryItem saveInventoryItem(InventoryItem item) {
        if (item.getId() == null) {
            item.setId(inventoryIdGenerator.getAndIncrement());
        }
        inventoryItems.put(item.getId(), item);
        return item;
    }
    
    public InventoryItem findInventoryItemById(Long id) {
        return inventoryItems.get(id);
    }
    
    public List<InventoryItem> findAllInventoryItems() {
        return new ArrayList<>(inventoryItems.values());
    }
    
    public List<InventoryItem> findLowStockItems() {
        return inventoryItems.values().stream()
            .filter(i -> i.getStock() < i.getReorderPoint())
            .toList();
    }
    
    public List<InventoryItem> findInventoryItemsByCategory(String category) {
        return inventoryItems.values().stream()
            .filter(i -> i.getCategory().equals(category))
            .toList();
    }
    
    public void updateInventoryStock(Long itemId, int quantity) {
        InventoryItem item = inventoryItems.get(itemId);
        if (item != null) {
            item.setStock(quantity);
            inventoryItems.put(itemId, item);
        }
    }
    
    public void deleteInventoryItem(Long id) {
        inventoryItems.remove(id);
    }
    
    // Customer operations
    public Customer saveCustomer(Customer customer) {
        if (customer.getId() == null) {
            customer.setId(customerIdGenerator.getAndIncrement());
        }
        customers.put(customer.getId(), customer);
        return customer;
    }
    
    public Customer findCustomerById(Long id) {
        return customers.get(id);
    }
    
    public List<Customer> findAllCustomers() {
        return new ArrayList<>(customers.values());
    }
    
    public void deleteCustomer(Long id) {
        customers.remove(id);
    }
    
    // Clear all data (useful for testing)
    public void clearAll() {
        employees.clear();
        orders.clear();
        inventoryItems.clear();
        customers.clear();
        
        employeeIdGenerator.set(1);
        orderIdGenerator.set(1);
        inventoryIdGenerator.set(1);
        customerIdGenerator.set(1);
    }
} 