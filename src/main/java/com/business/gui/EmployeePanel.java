package com.business.gui;

import com.business.entity.*;
import com.business.storage.DataStorage;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class EmployeePanel extends JPanel {
    private final DataStorage dataStorage;
    private JTable employeeTable;
    private EmployeeTableModel tableModel;
    
    // Color scheme (matching MainFrame)
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);    // Blue
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);  // Light Blue
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241); // Light Gray
    private static final Color TEXT_COLOR = new Color(44, 62, 80);         // Dark Blue
    private static final Color ACCENT_COLOR = new Color(231, 76, 60);      // Red
    
    public EmployeePanel(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        setupUI();
        refreshEmployeeList();
    }
    
    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        
        // Create toolbar
        JPanel toolbar = createToolbar();
        add(toolbar, BorderLayout.NORTH);
        
        // Create table
        employeeTable = new JTable();
        employeeTable.setFillsViewportHeight(true);
        employeeTable.setRowHeight(30);
        employeeTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        employeeTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        employeeTable.getTableHeader().setBackground(PRIMARY_COLOR);
        employeeTable.getTableHeader().setForeground(Color.WHITE);
        
        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel createToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolbar.setBackground(BACKGROUND_COLOR);
        toolbar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create buttons with custom styling
        JButton addButton = createStyledButton("Add Employee", "➕");
        JButton editButton = createStyledButton("Edit", "✏️");
        JButton deleteButton = createStyledButton("Delete", "🗑️");
        
        toolbar.add(addButton);
        toolbar.add(editButton);
        toolbar.add(deleteButton);
        
        // Add action listeners
        addButton.addActionListener(e -> showAddEmployeeDialog());
        editButton.addActionListener(e -> showEditEmployeeDialog());
        deleteButton.addActionListener(e -> deleteSelectedEmployee());
        
        return toolbar;
    }
    
    private JButton createStyledButton(String text, String icon) {
        JButton button = new JButton(icon + " " + text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(SECONDARY_COLOR);
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });
        
        return button;
    }
    
    private void refreshEmployeeList() {
        List<Employee> employees = dataStorage.findAllEmployees();
        tableModel = new EmployeeTableModel(employees);
        employeeTable.setModel(tableModel);
        
        // Adjust column widths
        employeeTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        employeeTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        employeeTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        employeeTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        employeeTable.getColumnModel().getColumn(4).setPreferredWidth(100);
    }
    
    private void showAddEmployeeDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add New Employee", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Form fields
        JTextField nameField = new JTextField(20);
        JTextField addressField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField departmentField = new JTextField(20);
        JTextField salaryField = new JTextField(20);
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Salaried", "Hourly"});
        
        // Add components
        int gridy = 0;
        addFormField(dialog, gbc, "Name:", nameField, gridy++);
        addFormField(dialog, gbc, "Address:", addressField, gridy++);
        addFormField(dialog, gbc, "Phone:", phoneField, gridy++);
        addFormField(dialog, gbc, "Email:", emailField, gridy++);
        addFormField(dialog, gbc, "Department:", departmentField, gridy++);
        addFormField(dialog, gbc, "Salary/Rate:", salaryField, gridy++);
        addFormField(dialog, gbc, "Type:", typeCombo, gridy++);
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String address = addressField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();
                String department = departmentField.getText();
                double salary = Double.parseDouble(salaryField.getText());
                
                Employee employee;
                if (typeCombo.getSelectedItem().equals("Salaried")) {
                    employee = new SalariedEmployee(name, address, phone, email, department, salary, 0, 0);
                } else {
                    employee = new HourlyEmployee(name, address, phone, email, department, salary);
                }
                
                dataStorage.saveEmployee(employee);
                refreshEmployeeList();
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid numbers for salary/rate.",
                                            "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void addFormField(JDialog dialog, GridBagConstraints gbc, String label, JComponent field, int gridy) {
        gbc.gridx = 0;
        gbc.gridy = gridy;
        dialog.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        dialog.add(field, gbc);
    }
    
    private void showEditEmployeeDialog() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow >= 0) {
            Employee employee = tableModel.getEmployeeAt(selectedRow);
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Employee", true);
            dialog.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            
            // Form fields
            JTextField nameField = new JTextField(employee.getName(), 20);
            JTextField addressField = new JTextField(employee.getAddress(), 20);
            JTextField phoneField = new JTextField(employee.getPhone(), 20);
            JTextField emailField = new JTextField(employee.getEmail(), 20);
            JTextField departmentField = new JTextField(employee.getDepartment(), 20);
            JTextField salaryField = new JTextField(String.valueOf(employee.getSalary()), 20);
            JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Salaried", "Hourly"});
            typeCombo.setSelectedItem(employee instanceof SalariedEmployee ? "Salaried" : "Hourly");
            
            // Add components
            int gridy = 0;
            addFormField(dialog, gbc, "Name:", nameField, gridy++);
            addFormField(dialog, gbc, "Address:", addressField, gridy++);
            addFormField(dialog, gbc, "Phone:", phoneField, gridy++);
            addFormField(dialog, gbc, "Email:", emailField, gridy++);
            addFormField(dialog, gbc, "Department:", departmentField, gridy++);
            addFormField(dialog, gbc, "Salary/Rate:", salaryField, gridy++);
            addFormField(dialog, gbc, "Type:", typeCombo, gridy++);
            
            // Buttons
            JPanel buttonPanel = new JPanel();
            JButton saveButton = new JButton("Save");
            JButton cancelButton = new JButton("Cancel");
            
            saveButton.addActionListener(e -> {
                try {
                    employee.setName(nameField.getText());
                    employee.setAddress(addressField.getText());
                    employee.setPhone(phoneField.getText());
                    employee.setEmail(emailField.getText());
                    employee.setDepartment(departmentField.getText());
                    employee.setSalary(Double.parseDouble(salaryField.getText()));
                    
                    dataStorage.saveEmployee(employee);
                    refreshEmployeeList();
                    dialog.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Please enter valid numbers for salary/rate.",
                                                "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            
            cancelButton.addActionListener(e -> dialog.dispose());
            
            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton);
            
            gbc.gridx = 0;
            gbc.gridy = gridy;
            gbc.gridwidth = 2;
            dialog.add(buttonPanel, gbc);
            
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please select an employee to edit",
                                        "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void deleteSelectedEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this employee?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                Employee employee = tableModel.getEmployeeAt(selectedRow);
                dataStorage.deleteEmployee(employee.getId());
                refreshEmployeeList();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an employee to delete",
                                        "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    // Table model for employees
    private class EmployeeTableModel extends AbstractTableModel {
        private final String[] COLUMN_NAMES = {"ID", "Name", "Department", "Type", "Salary/Rate", "Status"};
        private List<Employee> employees;
        
        public EmployeeTableModel(List<Employee> employees) {
            this.employees = employees;
        }
        
        public void setEmployees(List<Employee> employees) {
            this.employees = employees;
            fireTableDataChanged();
        }
        
        public Employee getEmployeeAt(int row) {
            return employees.get(row);
        }
        
        @Override
        public int getRowCount() {
            return employees.size();
        }
        
        @Override
        public int getColumnCount() {
            return COLUMN_NAMES.length;
        }
        
        @Override
        public String getColumnName(int column) {
            return COLUMN_NAMES[column];
        }
        
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Employee employee = employees.get(rowIndex);
            switch (columnIndex) {
                case 0: return employee.getId();
                case 1: return employee.getName();
                case 2: return employee.getDepartment();
                case 3: return employee instanceof SalariedEmployee ? "Salaried" : "Hourly";
                case 4: return employee instanceof SalariedEmployee ? 
                       String.format("$%.2f", employee.getSalary()) :
                       String.format("$%.2f/hr", ((HourlyEmployee) employee).getHourlyRate());
                case 5: return employee.isActive() ? "Active" : "Inactive";
                default: return null;
            }
        }
    }
} 