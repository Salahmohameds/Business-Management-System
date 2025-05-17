package com.business.db;

import com.business.entity.InventoryItem;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InventoryDAOTest {
    private InventoryDAO inventoryDAO;
    
    @BeforeAll
    public void setup() {
        inventoryDAO = new InventoryDAOImpl();
    }
    
    @AfterAll
    public void cleanup() {
        TestDatabaseConfig.shutdown();
    }
    
    @Test
    public void testCreateAndFindItem() {
        // Create test item
        InventoryItem item = new InventoryItem();
        item.setName("Test Item");
        item.setDescription("Test Description");
        item.setPrice(100.0);
        item.setQuantity(10);
        item.setCategory("Test Category");
        item.setReorderPoint(5);
        
        // Save item
        inventoryDAO.save(item);
        
        // Find item
        InventoryItem found = inventoryDAO.findById(item.getId());
        assertNotNull(found);
        assertEquals("Test Item", found.getName());
        assertEquals("Test Description", found.getDescription());
        assertEquals(100.0, found.getPrice());
        assertEquals(10, found.getQuantity());
        assertEquals("Test Category", found.getCategory());
        assertEquals(5, found.getReorderPoint());
    }
    
    @Test
    public void testUpdateItem() {
        // Create test item
        InventoryItem item = new InventoryItem();
        item.setName("Update Test");
        item.setPrice(50.0);
        item.setQuantity(20);
        item.setCategory("Test");
        item.setReorderPoint(10);
        
        // Save item
        inventoryDAO.save(item);
        
        // Update item
        item.setPrice(75.0);
        item.setQuantity(15);
        inventoryDAO.update(item);
        
        // Find updated item
        InventoryItem found = inventoryDAO.findById(item.getId());
        assertNotNull(found);
        assertEquals(75.0, found.getPrice());
        assertEquals(15, found.getQuantity());
    }
    
    @Test
    public void testDeleteItem() {
        // Create test item
        InventoryItem item = new InventoryItem();
        item.setName("Delete Test");
        item.setPrice(25.0);
        item.setQuantity(5);
        item.setCategory("Test");
        item.setReorderPoint(2);
        
        // Save item
        inventoryDAO.save(item);
        
        // Delete item
        inventoryDAO.delete(item.getId());
        
        // Try to find deleted item
        InventoryItem found = inventoryDAO.findById(item.getId());
        assertNull(found);
    }
    
    @Test
    public void testFindLowStockItems() {
        // Create test items
        InventoryItem item1 = new InventoryItem();
        item1.setName("Low Stock 1");
        item1.setPrice(30.0);
        item1.setQuantity(3);
        item1.setCategory("Test");
        item1.setReorderPoint(5);
        
        InventoryItem item2 = new InventoryItem();
        item2.setName("Low Stock 2");
        item2.setPrice(40.0);
        item2.setQuantity(4);
        item2.setCategory("Test");
        item2.setReorderPoint(5);
        
        InventoryItem item3 = new InventoryItem();
        item3.setName("Normal Stock");
        item3.setPrice(50.0);
        item3.setQuantity(10);
        item3.setCategory("Test");
        item3.setReorderPoint(5);
        
        // Save items
        inventoryDAO.save(item1);
        inventoryDAO.save(item2);
        inventoryDAO.save(item3);
        
        // Find low stock items
        List<InventoryItem> lowStockItems = inventoryDAO.findLowStockItems();
        assertEquals(2, lowStockItems.size());
        assertTrue(lowStockItems.stream().allMatch(i -> i.getQuantity() < i.getReorderPoint()));
    }
    
    @Test
    public void testFindByCategory() {
        // Create test items
        InventoryItem item1 = new InventoryItem();
        item1.setName("Category 1");
        item1.setPrice(60.0);
        item1.setQuantity(8);
        item1.setCategory("Category A");
        item1.setReorderPoint(3);
        
        InventoryItem item2 = new InventoryItem();
        item2.setName("Category 2");
        item2.setPrice(70.0);
        item2.setQuantity(12);
        item2.setCategory("Category A");
        item2.setReorderPoint(4);
        
        InventoryItem item3 = new InventoryItem();
        item3.setName("Other Category");
        item3.setPrice(80.0);
        item3.setQuantity(15);
        item3.setCategory("Category B");
        item3.setReorderPoint(5);
        
        // Save items
        inventoryDAO.save(item1);
        inventoryDAO.save(item2);
        inventoryDAO.save(item3);
        
        // Find items by category
        List<InventoryItem> categoryAItems = inventoryDAO.findByCategory("Category A");
        assertEquals(2, categoryAItems.size());
        assertTrue(categoryAItems.stream().allMatch(i -> i.getCategory().equals("Category A")));
    }
    
    @Test
    public void testUpdateStock() {
        // Create test item
        InventoryItem item = new InventoryItem();
        item.setName("Stock Update");
        item.setPrice(90.0);
        item.setQuantity(20);
        item.setCategory("Test");
        item.setReorderPoint(5);
        
        // Save item
        inventoryDAO.save(item);
        
        // Update stock
        inventoryDAO.updateStock(item.getId(), 15);
        
        // Find updated item
        InventoryItem found = inventoryDAO.findById(item.getId());
        assertNotNull(found);
        assertEquals(15, found.getQuantity());
    }
} 