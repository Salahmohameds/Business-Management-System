package com.business.gui;

import com.business.entity.*;
import com.business.storage.DataStorage;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SalesPanel extends JPanel {
    private final DataStorage dataStorage;
    private JTable orderTable;
    private JButton newOrderButton;
    private JButton viewOrderButton;
    private JButton processPaymentButton;
    
    // Color scheme (matching MainFrame)
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);    // Blue
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);  // Light Blue
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241); // Light Gray
    private static final Color TEXT_COLOR = new Color(44, 62, 80);         // Dark Blue
    private static final Color ACCENT_COLOR = new Color(231, 76, 60);      // Red
    
    public SalesPanel(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        setupUI();
        refreshOrderList();
    }
    
    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        
        // Create toolbar
        JPanel toolbar = createToolbar();
        add(toolbar, BorderLayout.NORTH);
        
        // Create table
        orderTable = new JTable();
        orderTable.setFillsViewportHeight(true);
        orderTable.setRowHeight(30);
        orderTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        orderTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        orderTable.getTableHeader().setBackground(PRIMARY_COLOR);
        orderTable.getTableHeader().setForeground(Color.WHITE);
        
        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(orderTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel createToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolbar.setBackground(BACKGROUND_COLOR);
        toolbar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create buttons with custom styling
        newOrderButton = createStyledButton("New Order", "🛒");
        viewOrderButton = createStyledButton("View Order", "👁️");
        processPaymentButton = createStyledButton("Process Payment", "💳");
        
        toolbar.add(newOrderButton);
        toolbar.add(viewOrderButton);
        toolbar.add(processPaymentButton);
        
        // Add action listeners
        newOrderButton.addActionListener(e -> showNewOrderDialog());
        viewOrderButton.addActionListener(e -> showOrderDetails());
        processPaymentButton.addActionListener(e -> processPayment());
        
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
    
    public void refreshOrderList() {
        List<Order> orders = dataStorage.findAllOrders();
        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Customer", "Date", "Status", "Total"},
            0
        );
        
        for (Order order : orders) {
            model.addRow(new Object[]{
                order.getId(),
                order.getCustomer().getName(),
                order.getOrderDate(),
                order.getStatus(),
                String.format("$%.2f", order.calculateTotal())
            });
        }
        
        orderTable.setModel(model);
        
        // Adjust column widths
        orderTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        orderTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        orderTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        orderTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        orderTable.getColumnModel().getColumn(4).setPreferredWidth(100);
    }
    
    private void showNewOrderDialog() {
        // Implementation for new order dialog
        JOptionPane.showMessageDialog(this, "New Order functionality to be implemented");
    }
    
    private void showOrderDetails() {
        // Implementation for viewing order details
        JOptionPane.showMessageDialog(this, "View Order functionality to be implemented");
    }
    
    private void processPayment() {
        // Implementation for processing payment
        JOptionPane.showMessageDialog(this, "Process Payment functionality to be implemented");
    }
} 