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

public class InventoryPanel extends JPanel {
    private DataStorage dataStorage;
    private JTable inventoryTable;
    private InventoryTableModel tableModel;
    
    // Color scheme (matching MainFrame)
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);    // Blue
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);  // Light Blue
    private static final Color BACKGROUND_COLOR = new Color
    
    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        
        // Create toolbar
        JPanel toolbar = createToolbar();
        add(toolbar, BorderLayout.NORTH);
        
        // Create table
        inventoryTable = new JTable();
        inventoryTable.setFillsViewportHeight(true);
        inventoryTable.setRowHeight(30);
        inventoryTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        inventoryTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        inventoryTable.getTableHeader().setBackground(PRIMARY_COLOR);
        inventoryTable.getTableHeader().setForeground(Color.WHITE);
        
        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel createToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolbar.setBackground(BACKGROUND_COLOR);
        toolbar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create buttons with custom styling
        JButton addButton = createStyledButton("Add Item", "📦");
        JButton editButton = createStyledButton("Edit Item", "✏️");
        JButton deleteButton = createStyledButton("Delete", "🗑️");
        JButton refreshButton = createStyledButton("Refresh", "🔄");
        
        toolbar.add(addButton);
        toolbar.add(editButton);
        toolbar.add(deleteButton);
        toolbar.add(refreshButton);
        
        // Add action listeners
        addButton.addActionListener(e -> showAddItemDialog());
        editButton.addActionListener(e -> showEditItemDialog());
        deleteButton.addActionListener(e -> deleteSelectedItem());
        refreshButton.addActionListener(e -> refreshInventoryList());
        
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
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(SECONDARY_COLOR);
            }
            
            public void mouseExited(MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });
        
        return button;
    }
    
    private void refreshInventoryList() {
        List<InventoryItem> items = dataStorage.findAllInventoryItems();
        tableModel.setItems(items);
    }
    
    private void showAddItemDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add New Item", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Form fields
        JTextField nameField = new JTextField(20);
        JTextField categoryField = new JTextField(20);
        JTextField priceField = new JTextField(20);
        JTextField stockField = new JTextField(20);
        JTextField reorderPointField = new JTextField(20);
        
        // Add components
        int gridy = 0;
        addFormField(dialog, gbc, "Name:", nameField, gridy++);
        addFormField(dialog, gbc, "Category:", categoryField, gridy++);
        addFormField(dialog, gbc, "Price:", priceField, gridy++);
        addFormField(dialog, gbc, "Stock:", stockField, gridy++);
        addFormField(dialog, gbc, "Reorder Point:", reorderPointField, gridy++);
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String category = categoryField.getText();
                double price = Double.parseDouble(priceField.getText());
                int stock = Integer.parseInt(stockField.getText());
                int reorderPoint = Integer.parseInt(reorderPointField.getText());
                
                InventoryItem item = new InventoryItem(name, category, price, stock, reorderPoint);
                dataStorage.saveInventoryItem(item);
                refreshInventoryList();
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid numbers for price, stock, and reorder point.",
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
    
    private void showEditItemDialog() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow >= 0) {
            InventoryItem item = tableModel.getItemAt(selectedRow);
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Item", true);
            dialog.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            
            // Form fields
            JTextField nameField = new JTextField(item.getName(), 20);
            JTextField categoryField = new JTextField(item.getCategory(), 20);
            JTextField priceField = new JTextField(String.valueOf(item.getPrice()), 20);
            JTextField stockField = new JTextField(String.valueOf(item.getStock()), 20);
            JTextField reorderPointField = new JTextField(String.valueOf(item.getReorderPoint()), 20);
            
            // Add components
            int gridy = 0;
            addFormField(dialog, gbc, "Name:", nameField, gridy++);
            addFormField(dialog, gbc, "Category:", categoryField, gridy++);
            addFormField(dialog, gbc, "Price:", priceField, gridy++);
            addFormField(dialog, gbc, "Stock:", stockField, gridy++);
            addFormField(dialog, gbc, "Reorder Point:", reorderPointField, gridy++);
            
            // Buttons
            JPanel buttonPanel = new JPanel();
            JButton saveButton = new JButton("Save");
            JButton cancelButton = new JButton("Cancel");
            
            saveButton.addActionListener(e -> {
                try {
                    item.setName(nameField.getText());
                    item.setCategory(categoryField.getText());
                    item.setPrice(Double.parseDouble(priceField.getText()));
                    item.setStock(Integer.parseInt(stockField.getText()));
                    item.setReorderPoint(Integer.parseInt(reorderPointField.getText()));
                    
                    dataStorage.saveInventoryItem(item);
                    refreshInventoryList();
                    dialog.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Please enter valid numbers for price, stock, and reorder point.",
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
            JOptionPane.showMessageDialog(this, "Please select an item to edit",
                                        "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void deleteSelectedItem() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this item?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                InventoryItem item = tableModel.getItemAt(selectedRow);
                dataStorage.deleteInventoryItem(item.getId());
                refreshInventoryList();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to delete",
                                        "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    // Table model for inventory items
    private class InventoryTableModel extends AbstractTableModel {
        private final String[] COLUMN_NAMES = {"ID", "Name", "Category", "Price", "Stock", "Reorder Point", "Status"};
        private List<InventoryItem> items;
        
        public InventoryTableModel(List<InventoryItem> items) {
            this.items = items;
        }
        
        public void setItems(List<InventoryItem> items) {
            this.items = items;
            fireTableDataChanged();
        }
        
        public InventoryItem getItemAt(int row) {
            return items.get(row);
        }
        
        @Override
        public int getRowCount() {
            return items.size();
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
            InventoryItem item = items.get(rowIndex);
            switch (columnIndex) {
                case 0: return item.getId();
                case 1: return item.getName();
                case 2: return item.getCategory();
                case 3: return String.format("$%.2f", item.getPrice());
                case 4: return item.getStock();
                case 5: return item.getReorderPoint();
                case 6: return item.getStock() <= item.getReorderPoint() ? "Low Stock" : "In Stock";
                default: return null;
            }
        }
    }
} 