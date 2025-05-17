package com.business.gui;

import com.business.entity.*;
import com.business.storage.DataStorage;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReportingPanel extends JPanel {
    private DataStorage dataStorage;
    private JTabbedPane tabbedPane;
    
    // Color scheme (matching MainFrame)
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);    // Blue
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);  // Light Blue
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241); // Light Gray
    private static final Color TEXT_COLOR = new Color(44, 62, 80);         // Dark Blue
    private static final Color ACCENT_COLOR = new Color(231, 76, 60);      // Red
    
    public ReportingPanel() {
        this.dataStorage = DataStorage.getInstance();
        setupUI();
    }
    
    private void setupUI() {
        setLayout(new BorderLayout());
        
        tabbedPane = new JTabbedPane();
        
        // Sales Reports
        JPanel salesPanel = new JPanel(new BorderLayout());
        JPanel salesControlPanel = new JPanel(new FlowLayout());
        JButton refreshSalesButton = new JButton("Refresh");
        refreshSalesButton.addActionListener(e -> refreshSalesReport());
        salesControlPanel.add(refreshSalesButton);
        
        JTable salesTable = new JTable(new SalesReportTableModel());
        salesPanel.add(new JScrollPane(salesTable), BorderLayout.CENTER);
        salesPanel.add(salesControlPanel, BorderLayout.SOUTH);
        
        // Inventory Reports
        JPanel inventoryPanel = new JPanel(new BorderLayout());
        JPanel inventoryControlPanel = new JPanel(new FlowLayout());
        JButton refreshInventoryButton = new JButton("Refresh");
        refreshInventoryButton.addActionListener(e -> refreshInventoryReport());
        inventoryControlPanel.add(refreshInventoryButton);
        
        JTable inventoryTable = new JTable(new InventoryReportTableModel());
        inventoryPanel.add(new JScrollPane(inventoryTable), BorderLayout.CENTER);
        inventoryPanel.add(inventoryControlPanel, BorderLayout.SOUTH);
        
        // Employee Reports
        JPanel employeePanel = new JPanel(new BorderLayout());
        JPanel employeeControlPanel = new JPanel(new FlowLayout());
        JButton refreshEmployeeButton = new JButton("Refresh");
        refreshEmployeeButton.addActionListener(e -> refreshEmployeeReport());
        employeeControlPanel.add(refreshEmployeeButton);
        
        JTable employeeTable = new JTable(new EmployeeReportTableModel());
        employeePanel.add(new JScrollPane(employeeTable), BorderLayout.CENTER);
        employeePanel.add(employeeControlPanel, BorderLayout.SOUTH);
        
        // Add tabs
        tabbedPane.addTab("Sales Report", salesPanel);
        tabbedPane.addTab("Inventory Report", inventoryPanel);
        tabbedPane.addTab("Employee Report", employeePanel);
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Initial refresh
        refreshSalesReport();
        refreshInventoryReport();
        refreshEmployeeReport();
    }
    
    private void refreshSalesReport() {
        JTable table = (JTable) ((JScrollPane) ((JPanel) tabbedPane.getComponentAt(0)).getComponent(0)).getViewport().getView();
        SalesReportTableModel model = (SalesReportTableModel) table.getModel();
        
        List<Order> orders = dataStorage.findAllOrders();
        Map<String, Double> salesByDate = new HashMap<>();
        double totalSales = 0;
        int totalOrders = 0;
        
        for (Order order : orders) {
            if (order.getStatus() == OrderStatus.COMPLETED) {
                String date = order.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
                salesByDate.merge(date, order.getTotal(), Double::sum);
                totalSales += order.getTotal();
                totalOrders++;
            }
        }
        
        model.setData(salesByDate, totalSales, totalOrders);
    }
    
    private void refreshInventoryReport() {
        JTable table = (JTable) ((JScrollPane) ((JPanel) tabbedPane.getComponentAt(1)).getComponent(0)).getViewport().getView();
        InventoryReportTableModel model = (InventoryReportTableModel) table.getModel();
        
        List<InventoryItem> items = dataStorage.findAllInventoryItems();
        model.setData(items);
    }
    
    private void refreshEmployeeReport() {
        JTable table = (JTable) ((JScrollPane) ((JPanel) tabbedPane.getComponentAt(2)).getComponent(0)).getViewport().getView();
        EmployeeReportTableModel model = (EmployeeReportTableModel) table.getModel();
        
        List<Employee> employees = dataStorage.findAllEmployees();
        model.setData(employees);
    }
    
    // Table model for sales report
    private class SalesReportTableModel extends AbstractTableModel {
        private final String[] COLUMN_NAMES = {"Date", "Sales Amount"};
        private List<Map.Entry<String, Double>> salesData;
        private double totalSales;
        private int totalOrders;
        
        public SalesReportTableModel() {
            this.salesData = new ArrayList<>();
        }
        
        public void setData(Map<String, Double> salesByDate, double totalSales, int totalOrders) {
            this.salesData = new ArrayList<>(salesByDate.entrySet());
            this.totalSales = totalSales;
            this.totalOrders = totalOrders;
            fireTableDataChanged();
        }
        
        @Override
        public int getRowCount() {
            return salesData.size() + 2; // +2 for total and average rows
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
            if (rowIndex < salesData.size()) {
                Map.Entry<String, Double> entry = salesData.get(rowIndex);
                return columnIndex == 0 ? entry.getKey() : String.format("$%.2f", entry.getValue());
            } else if (rowIndex == salesData.size()) {
                return columnIndex == 0 ? "Total Sales" : String.format("$%.2f", totalSales);
            } else {
                return columnIndex == 0 ? "Average Order Value" : 
                       String.format("$%.2f", totalOrders > 0 ? totalSales / totalOrders : 0);
            }
        }
    }
    
    // Table model for inventory report
    private class InventoryReportTableModel extends AbstractTableModel {
        private final String[] COLUMN_NAMES = {"Item", "Current Stock", "Unit Price", "Total Value"};
        private List<InventoryItem> items;
        
        public InventoryReportTableModel() {
            this.items = new ArrayList<>();
        }
        
        public void setData(List<InventoryItem> items) {
            this.items = items;
            fireTableDataChanged();
        }
        
        @Override
        public int getRowCount() {
            return items.size() + 1; // +1 for total row
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
            if (rowIndex < items.size()) {
                InventoryItem item = items.get(rowIndex);
                switch (columnIndex) {
                    case 0: return item.getName();
                    case 1: return item.getStock();
                    case 2: return String.format("$%.2f", item.getPrice());
                    case 3: return String.format("$%.2f", item.getStock() * item.getPrice());
                    default: return null;
                }
            } else {
                if (columnIndex == 0) {
                    return "Total Inventory Value";
                } else if (columnIndex == 3) {
                    double totalValue = items.stream()
                        .mapToDouble(item -> item.getStock() * item.getPrice())
                        .sum();
                    return String.format("$%.2f", totalValue);
                }
                return "";
            }
        }
    }
    
    // Table model for employee report
    private class EmployeeReportTableModel extends AbstractTableModel {
        private final String[] COLUMN_NAMES = {"Name", "Department", "Type", "Salary/Rate", "Status"};
        private List<Employee> employees;
        
        public EmployeeReportTableModel() {
            this.employees = new ArrayList<>();
        }
        
        public void setData(List<Employee> employees) {
            this.employees = employees;
            fireTableDataChanged();
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
                case 0: return employee.getName();
                case 1: return employee.getDepartment();
                case 2: return employee instanceof SalariedEmployee ? "Salaried" : "Hourly";
                case 3: return employee instanceof SalariedEmployee ? 
                       String.format("$%.2f", employee.getSalary()) :
                       String.format("$%.2f/hr", ((HourlyEmployee) employee).getHourlyRate());
                case 4: return employee.isActive() ? "Active" : "Inactive";
                default: return null;
            }
        }
    }
} 