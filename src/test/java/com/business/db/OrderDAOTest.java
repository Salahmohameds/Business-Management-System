package com.business.db;

import com.business.entity.*;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderDAOTest {
    private OrderDAO orderDAO;
    private EmployeeDAO employeeDAO;
    private CustomerDAO customerDAO;
    private InventoryDAO inventoryDAO;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    @BeforeAll
    public void setup() {
        orderDAO = new OrderDAOImpl();
        employeeDAO = new EmployeeDAOImpl();
        customerDAO = new CustomerDAOImpl();
        inventoryDAO = new InventoryDAOImpl();
    }
    
    @AfterAll
    public void cleanup() {
        TestDatabaseConfig.shutdown();
    }
    
    @Test
    public void testCreateAndFindOrder() {
        // Create test data
        Employee salesPerson = new SalariedEmployee();
        salesPerson.setName("John Sales");
        salesPerson.setDepartment("Sales");
        salesPerson.setSalary(50000.0);
        employeeDAO.save(salesPerson);
        
        Customer customer = new Customer();
        customer.setName("Test Customer");
        customer.setEmail("test@example.com");
        customerDAO.save(customer);
        
        InventoryItem item = new InventoryItem();
        item.setName("Test Item");
        item.setPrice(100.0);
        item.setQuantity(10);
        inventoryDAO.save(item);
        
        // Create order
        Order order = new Order();
        order.setOrderDate(LocalDate.now().format(formatter));
        order.setStatus(OrderStatus.PENDING);
        order.setSalesPerson(salesPerson);
        order.setCustomer(customer);
        
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setQuantity(2);
        orderItem.setPrice(item.getPrice());
        order.addItem(orderItem);
        
        // Save order
        orderDAO.save(order);
        
        // Find order
        Order found = orderDAO.findById(order.getId());
        assertNotNull(found);
        assertEquals(OrderStatus.PENDING, found.getStatus());
        assertEquals(1, found.getItems().size());
        assertEquals(200.0, found.getTotalAmount());
    }
    
    @Test
    public void testUpdateOrderStatus() {
        // Create test data
        Employee salesPerson = new SalariedEmployee();
        salesPerson.setName("Jane Sales");
        salesPerson.setDepartment("Sales");
        salesPerson.setSalary(55000.0);
        employeeDAO.save(salesPerson);
        
        Customer customer = new Customer();
        customer.setName("Another Customer");
        customer.setEmail("another@example.com");
        customerDAO.save(customer);
        
        // Create order
        Order order = new Order();
        order.setOrderDate(LocalDate.now().format(formatter));
        order.setStatus(OrderStatus.PENDING);
        order.setSalesPerson(salesPerson);
        order.setCustomer(customer);
        
        // Save order
        orderDAO.save(order);
        
        // Update status
        order.setStatus(OrderStatus.COMPLETED);
        orderDAO.update(order);
        
        // Find updated order
        Order found = orderDAO.findById(order.getId());
        assertNotNull(found);
        assertEquals(OrderStatus.COMPLETED, found.getStatus());
    }
    
    @Test
    public void testFindOrdersByCustomer() {
        // Create test data
        Employee salesPerson = new SalariedEmployee();
        salesPerson.setName("Bob Sales");
        salesPerson.setDepartment("Sales");
        salesPerson.setSalary(60000.0);
        employeeDAO.save(salesPerson);
        
        Customer customer = new Customer();
        customer.setName("Regular Customer");
        customer.setEmail("regular@example.com");
        customerDAO.save(customer);
        
        // Create orders
        Order order1 = new Order();
        order1.setOrderDate(LocalDate.now().format(formatter));
        order1.setStatus(OrderStatus.COMPLETED);
        order1.setSalesPerson(salesPerson);
        order1.setCustomer(customer);
        
        Order order2 = new Order();
        order2.setOrderDate(LocalDate.now().format(formatter));
        order2.setStatus(OrderStatus.PENDING);
        order2.setSalesPerson(salesPerson);
        order2.setCustomer(customer);
        
        // Save orders
        orderDAO.save(order1);
        orderDAO.save(order2);
        
        // Find orders by customer
        List<Order> customerOrders = orderDAO.findByCustomer(customer.getId());
        assertEquals(2, customerOrders.size());
        assertTrue(customerOrders.stream().allMatch(o -> o.getCustomer().getId().equals(customer.getId())));
    }
    
    @Test
    public void testFindOrdersByDateRange() {
        // Create test data
        Employee salesPerson = new SalariedEmployee();
        salesPerson.setName("Alice Sales");
        salesPerson.setDepartment("Sales");
        salesPerson.setSalary(65000.0);
        employeeDAO.save(salesPerson);
        
        Customer customer = new Customer();
        customer.setName("Date Customer");
        customer.setEmail("date@example.com");
        customerDAO.save(customer);
        
        // Create orders with different dates
        Order order1 = new Order();
        order1.setOrderDate(LocalDate.now().minusDays(2).format(formatter));
        order1.setStatus(OrderStatus.COMPLETED);
        order1.setSalesPerson(salesPerson);
        order1.setCustomer(customer);
        
        Order order2 = new Order();
        order2.setOrderDate(LocalDate.now().format(formatter));
        order2.setStatus(OrderStatus.PENDING);
        order2.setSalesPerson(salesPerson);
        order2.setCustomer(customer);
        
        // Save orders
        orderDAO.save(order1);
        orderDAO.save(order2);
        
        // Find orders by date range
        String startDate = LocalDate.now().minusDays(3).format(formatter);
        String endDate = LocalDate.now().minusDays(1).format(formatter);
        List<Order> dateRangeOrders = orderDAO.findByDateRange(startDate, endDate);
        assertEquals(1, dateRangeOrders.size());
        assertEquals(order1.getId(), dateRangeOrders.get(0).getId());
    }
} 