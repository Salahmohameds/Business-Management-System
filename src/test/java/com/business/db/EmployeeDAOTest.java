package com.business.db;

import com.business.entity.Employee;
import com.business.entity.HourlyEmployee;
import com.business.entity.SalariedEmployee;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeeDAOTest {
    private EmployeeDAO employeeDAO;
    
    @BeforeAll
    public void setup() {
        employeeDAO = new EmployeeDAOImpl();
    }
    
    @AfterAll
    public void cleanup() {
        TestDatabaseConfig.shutdown();
    }
    
    @Test
    public void testCreateAndFindEmployee() {
        // Create test employee
        Employee employee = new SalariedEmployee();
        employee.setName("John Doe");
        employee.setDepartment("IT");
        employee.setSalary(50000.0);
        
        // Save employee
        employeeDAO.save(employee);
        
        // Find employee
        Employee found = employeeDAO.findById(employee.getId());
        assertNotNull(found);
        assertEquals("John Doe", found.getName());
        assertEquals("IT", found.getDepartment());
        assertEquals(50000.0, found.getSalary());
    }
    
    @Test
    public void testUpdateEmployee() {
        // Create test employee
        Employee employee = new HourlyEmployee();
        employee.setName("Jane Smith");
        employee.setDepartment("HR");
        employee.setSalary(25.0);
        ((HourlyEmployee) employee).setHoursWorked(40);
        
        // Save employee
        employeeDAO.save(employee);
        
        // Update employee
        employee.setName("Jane Doe");
        employeeDAO.update(employee);
        
        // Find updated employee
        Employee found = employeeDAO.findById(employee.getId());
        assertNotNull(found);
        assertEquals("Jane Doe", found.getName());
    }
    
    @Test
    public void testDeleteEmployee() {
        // Create test employee
        Employee employee = new SalariedEmployee();
        employee.setName("Bob Johnson");
        employee.setDepartment("Sales");
        employee.setSalary(60000.0);
        
        // Save employee
        employeeDAO.save(employee);
        
        // Delete employee
        employeeDAO.delete(employee.getId());
        
        // Try to find deleted employee
        Employee found = employeeDAO.findById(employee.getId());
        assertNull(found);
    }
    
    @Test
    public void testFindAllEmployees() {
        // Create test employees
        Employee emp1 = new SalariedEmployee();
        emp1.setName("Alice Brown");
        emp1.setDepartment("IT");
        emp1.setSalary(55000.0);
        
        Employee emp2 = new HourlyEmployee();
        emp2.setName("Charlie Wilson");
        emp2.setDepartment("HR");
        emp2.setSalary(30.0);
        ((HourlyEmployee) emp2).setHoursWorked(35);
        
        // Save employees
        employeeDAO.save(emp1);
        employeeDAO.save(emp2);
        
        // Find all employees
        List<Employee> employees = employeeDAO.findAll();
        assertTrue(employees.size() >= 2);
        assertTrue(employees.stream().anyMatch(e -> e.getName().equals("Alice Brown")));
        assertTrue(employees.stream().anyMatch(e -> e.getName().equals("Charlie Wilson")));
    }
    
    @Test
    public void testFindByDepartment() {
        // Create test employees
        Employee emp1 = new SalariedEmployee();
        emp1.setName("David Lee");
        emp1.setDepartment("IT");
        emp1.setSalary(65000.0);
        
        Employee emp2 = new SalariedEmployee();
        emp2.setName("Eve Davis");
        emp2.setDepartment("IT");
        emp2.setSalary(70000.0);
        
        // Save employees
        employeeDAO.save(emp1);
        employeeDAO.save(emp2);
        
        // Find employees by department
        List<Employee> itEmployees = employeeDAO.findByDepartment("IT");
        assertEquals(2, itEmployees.size());
        assertTrue(itEmployees.stream().allMatch(e -> e.getDepartment().equals("IT")));
    }
} 