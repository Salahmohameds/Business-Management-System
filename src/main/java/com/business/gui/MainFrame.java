package com.business.gui;

import com.business.storage.DataStorage;
import com.business.util.DataInitializer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.Graphics2D;

public class MainFrame extends JFrame {
    private final DataStorage dataStorage;
    private JTabbedPane tabbedPane;
    
    // Color scheme
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);    // Blue
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);  // Light Blue
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241); // Light Gray
    private static final Color TEXT_COLOR = new Color(44, 62, 80);         // Dark Blue
    private static final Color ACCENT_COLOR = new Color(231, 76, 60);      // Red
    
    public MainFrame() {
        this.dataStorage = DataStorage.getInstance();
        setupUI();
        initializeData();
    }
    
    private void setupUI() {
        setTitle("Business Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        // Set the look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Customize the tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(BACKGROUND_COLOR);
        tabbedPane.setForeground(TEXT_COLOR);
        
        // Add panels with custom styling
        tabbedPane.addTab("Employees", createStyledIcon("👥"), new EmployeePanel(dataStorage));
        tabbedPane.addTab("Sales", createStyledIcon("💰"), new SalesPanel(dataStorage));
        tabbedPane.addTab("Inventory", createStyledIcon("📦"), new InventoryPanel(dataStorage));
        tabbedPane.addTab("Reports", createStyledIcon("📊"), new ReportingPanel(dataStorage));
        
        // Style the tabbed pane
        UIManager.put("TabbedPane.selected", PRIMARY_COLOR);
        UIManager.put("TabbedPane.background", BACKGROUND_COLOR);
        UIManager.put("TabbedPane.foreground", TEXT_COLOR);
        
        // Create main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Add header
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Add tabbed pane
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Add status bar
        JPanel statusBar = createStatusBar();
        mainPanel.add(statusBar, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Add window listener for cleanup
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Perform any cleanup if needed
            }
        });
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 60));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("Business Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        return headerPanel;
    }
    
    private JPanel createStatusBar() {
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(SECONDARY_COLOR);
        statusBar.setPreferredSize(new Dimension(getWidth(), 25));
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        JLabel statusLabel = new JLabel("Ready");
        statusLabel.setForeground(Color.WHITE);
        statusBar.add(statusLabel, BorderLayout.WEST);
        
        return statusBar;
    }
    
    private ImageIcon createStyledIcon(String emoji) {
        JLabel label = new JLabel(emoji);
        label.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        label.setPreferredSize(new Dimension(20, 20));
        
        // Create a buffered image
        BufferedImage image = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        label.paint(g2d);
        g2d.dispose();
        
        return new ImageIcon(image);
    }
    
    private void initializeData() {
        DataInitializer initializer = new DataInitializer();
        initializer.initializeData();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
} 