# Business Management System

A Java-based business management system that provides functionality for managing employees, sales, inventory, and generating reports. The system uses in-memory storage for data persistence during runtime.

## Project Structure

```
src/main/java/com/business/
├── entity/           # Core business entities
├── gui/             # User interface components
├── storage/         # Data storage management
├── manager/         # Business logic managers
├── exception/       # Custom exceptions
└── util/            # Utility classes
```

## Class Hierarchy and Relationships

### Entity Classes

#### Employee Hierarchy
```
Employee (Abstract)
├── SalariedEmployee
└── HourlyEmployee
```

- `Employee`: Abstract base class for all employees
  - Properties: id, name, address, phone, email, department
  - Abstract method: calculateSalary()

- `SalariedEmployee`: Extends Employee
  - Additional properties: annualSalary, bonus, deductions
  - Overrides calculateSalary() for salaried calculation

- `HourlyEmployee`: Extends Employee
  - Additional properties: hourlyRate, hoursWorked
  - Overrides calculateSalary() for hourly calculation

#### Order System
```
Order
└── OrderItem
```

- `Order`: Represents a sales order
  - Properties: id, customer, date, status, items
  - Methods: addItem(), removeItem(), calculateTotal()

- `OrderItem`: Represents an item in an order
  - Properties: id, order, product, quantity, price
  - Methods: calculateSubtotal()

#### Inventory
```
InventoryItem
```

- `InventoryItem`: Represents a product in inventory
  - Properties: id, name, category, price, stock, reorderPoint
  - Methods: updateStock(), checkLowStock()

#### Customer
```
Customer
```

- `Customer`: Represents a business customer
  - Properties: id, name, address, phone, email
  - Methods: placeOrder()

### GUI Components

#### Main Frame
```
MainFrame
```

- Main application window
- Contains tabbed interface for different modules
- Initializes DataStorage and loads sample data

#### Panels
```
JPanel
├── EmployeePanel
├── SalesPanel
├── InventoryPanel
└── ReportingPanel
```

- `EmployeePanel`: Manages employee records
  - Features: Add, edit, delete employees
  - Displays employee list in table format
  - Handles salary calculations

- `SalesPanel`: Manages sales and orders
  - Features: Create orders, add items, process payments
  - Displays order history
  - Manages customer interactions

- `InventoryPanel`: Manages inventory items
  - Features: Add, edit, delete inventory items
  - Stock level monitoring
  - Low stock alerts

- `ReportingPanel`: Generates business reports
  - Sales reports
  - Inventory reports
  - Employee reports

### Data Storage

```
DataStorage (Singleton)
```

- Singleton class for in-memory data management
- Uses ConcurrentHashMap for thread-safe operations
- Manages all entity types:
  - Employees
  - Orders
  - Inventory Items
  - Customers
- Provides CRUD operations for all entities
- Handles ID generation using AtomicLong

## Features

### Employee Management
- Add, edit, and delete employees
- Support for both salaried and hourly employees
- Automatic salary calculations
- Department organization

### Sales Management
- Create and manage orders
- Add items to orders
- Process payments
- Track order status
- Customer management

### Inventory Management
- Track product inventory
- Monitor stock levels
- Set reorder points
- Low stock alerts
- Category organization

### Reporting
- Sales reports with totals and trends
- Inventory status reports
- Employee performance reports

## Technical Details

### Data Storage
- In-memory storage using ConcurrentHashMap
- Thread-safe operations
- Automatic ID generation
- Data persistence during runtime

### User Interface
- Built with Java Swing
- Tabbed interface for easy navigation
- Input validation
- Error handling

## Getting Started

### Prerequisites
- Java 17 or higher

### Building the Project
```bash
javac -d target/classes src/main/java/com/business/gui/*.java src/main/java/com/business/entity/*.java src/main/java/com/business/storage/*.java src/main/java/com/business/manager/*.java src/main/java/com/business/exception/*.java src/main/java/com/business/util/*.java
```

### Running the Application
```bash
java -cp target/classes com.business.gui.MainFrame
```

## Sample Data

The system comes pre-populated with sample data:

### Employees
- 3 Salaried Employees:
  - CEO (John Smith)
  - CTO (Sarah Johnson)
  - HR Manager (Michael Brown)
- 3 Hourly Employees:
  - Sales Rep (Emily Davis)
  - Sales Rep (David Wilson)
  - Warehouse Worker (Lisa Anderson)

### Inventory Items
- Electronics:
  - Dell XPS 15 Laptop
  - iPhone 14 Pro
  - LG 27" 4K Monitor
- Office Supplies:
  - HP LaserJet Pro Printer
  - A4 Paper
  - Ballpoint Pens
- Furniture:
  - Executive Desk
  - Ergonomic Office Chair

### Customers
- Business Customers:
  - Tech Solutions Inc.
  - Global Enterprises
- Individual Customers:
  - Robert Johnson
  - Maria Garcia

### Sample Orders
- Business Order:
  - 5 Dell XPS 15 Laptops
  - 5 LG 27" 4K Monitors
- Individual Order:
  - 1 Executive Desk
  - 1 Ergonomic Office Chair

## License
This project is licensed under the MIT License - see the LICENSE file for details. 