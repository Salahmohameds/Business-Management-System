# Class Relationships Documentation

## Core Entity Classes

### Person (Abstract)
- **Access Level**: Protected
- **Attributes**: 
  - `id` (protected Long)
  - `name` (protected String)
  - `address` (protected String)
  - `phone` (protected String)
  - `email` (protected String)
- **Methods**:
  - `displayDetails()` (public)
  - Getters and setters for all attributes (public)
- **Inheritance**: None
- **Dependencies**: None

### Employee (Abstract)
- **Access Level**: Protected
- **Inherits From**: Person
- **Attributes**:
  - `department` (protected String)
  - `salary` (protected double)
- **Methods**:
  - `calculateSalary()` (abstract public)
  - Getters and setters for department and salary (public)
- **Dependencies**: Person

### BusinessEntity (Abstract)
- **Access Level**: Protected
- **Attributes**:
  - `id` (protected Long)
- **Methods**:
  - `getId()` and `setId()` (public)
- **Inheritance**: None
- **Dependencies**: None

## Concrete Entity Classes

### SalariedEmployee
- **Inherits From**: Employee
- **Attributes**:
  - `bonus` (private double)
  - `deductions` (private double)
- **Methods**:
  - `calculateSalary()` (implements abstract method)
  - `getNetSalary()`
  - Getters and setters for bonus and deductions

### HourlyEmployee
- **Inherits From**: Employee
- **Attributes**:
  - `hourlyRate` (private double)
  - `hoursWorked` (private int)
- **Methods**:
  - `calculateSalary()` (implements abstract method)
  - `addHours(int)`
  - Getters and setters for hourlyRate and hoursWorked

### Customer
- **Inherits From**: Person
- **Attributes**:
  - `orderHistory` (private List<Order>)
  - `totalSpent` (private double)
  - `customerType` (private String)
- **Methods**:
  - `addOrder(Order)`
  - `updateCustomerType()`
  - Getters and setters for all attributes

### Order
- **Inherits From**: BusinessEntity
- **Attributes**:
  - `customer` (private Customer)
  - `orderDate` (private LocalDateTime)
  - `status` (private OrderStatus)
  - `items` (private List<OrderItem>)
- **Methods**:
  - `addItem(InventoryItem, int)`
  - `removeItem(OrderItem)`
  - `calculateTotal()`
  - Getters and setters for all attributes

### OrderItem
- **Inherits From**: BusinessEntity
- **Attributes**:
  - `order` (private Order)
  - `product` (private InventoryItem)
  - `quantity` (private int)
  - `price` (private double)
- **Methods**:
  - `calculateSubtotal()`
  - Getters and setters for all attributes

### InventoryItem
- **Inherits From**: BusinessEntity
- **Attributes**:
  - `name` (private String)
  - `category` (private String)
  - `price` (private double)
  - `stock` (private int)
  - `reorderPoint` (private int)
- **Methods**:
  - `updateStock(int)`
  - `checkLowStock()`
  - Getters and setters for all attributes

## Storage Layer

### DataStorage (Singleton)
- **Access Level**: Public
- **Attributes**:
  - `employees` (private Map<Long, Employee>)
  - `orders` (private Map<Long, Order>)
  - `inventoryItems` (private Map<Long, InventoryItem>)
  - `customers` (private Map<Long, Customer>)
- **Methods**:
  - `getInstance()`
  - `saveEmployee(Employee)`
  - `saveOrder(Order)`
  - `saveInventoryItem(InventoryItem)`
  - `saveCustomer(Customer)`
  - `findAllEmployees()`
  - `findAllOrders()`
  - `findAllInventoryItems()`
  - `findAllCustomers()`

## GUI Components

### MainFrame
- **Attributes**:
  - `dataStorage` (private DataStorage)
  - `tabbedPane` (private JTabbedPane)
- **Methods**:
  - `setupUI()`
  - `initializeData()`

### EmployeePanel
- **Attributes**:
  - `dataStorage` (private DataStorage)
  - `employeeTable` (private JTable)
- **Methods**:
  - `refreshEmployeeList()`
  - `showAddEmployeeDialog()`
  - `showEditEmployeeDialog()`

### SalesPanel
- **Attributes**:
  - `dataStorage` (private DataStorage)
  - `orderTable` (private JTable)
- **Methods**:
  - `refreshOrderList()`
  - `showNewOrderDialog()`
  - `processPayment()`

### InventoryPanel
- **Attributes**:
  - `dataStorage` (private DataStorage)
  - `inventoryTable` (private JTable)
- **Methods**:
  - `refreshInventoryList()`
  - `showAddItemDialog()`
  - `showEditItemDialog()`

### ReportingPanel
- **Attributes**:
  - `dataStorage` (private DataStorage)
  - `reportTable` (private JTable)
- **Methods**:
  - `refreshSalesReport()`
  - `refreshInventoryReport()`
  - `refreshEmployeeReport()`

## Key Relationships

1. **Inheritance Hierarchy**:
   - Person ← Employee ← SalariedEmployee
   - Person ← Employee ← HourlyEmployee
   - Person ← Customer
   - BusinessEntity ← Order
   - BusinessEntity ← OrderItem
   - BusinessEntity ← InventoryItem

2. **Composition Relationships**:
   - Order contains multiple OrderItems
   - Customer has multiple Orders
   - MainFrame contains all Panel components

3. **Dependencies**:
   - All GUI Panels depend on DataStorage
   - Order depends on Customer and InventoryItem
   - OrderItem depends on Order and InventoryItem

4. **Data Flow**:
   - GUI Panels → DataStorage → Entity Objects
   - Entity Objects → Business Logic → DataStorage 