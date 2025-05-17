package com.business.manager;

import com.business.entity.InventoryItem;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryManager {
    private List<InventoryItem> items;
    
    public InventoryManager() {
        this.items = new ArrayList<>();
    }
    
    public void addItem(InventoryItem item) {
        items.add(item);
    }
    
    public void removeItem(String itemId) {
        items.removeIf(item -> item.getId().equals(itemId));
    }
    
    public InventoryItem getItem(String itemId) {
        return items.stream()
                   .filter(item -> item.getId().equals(itemId))
                   .findFirst()
                   .orElse(null);
    }
    
    public List<InventoryItem> getItems() {
        return new ArrayList<>(items);
    }
    
    public List<InventoryItem> getItemsByCategory(String category) {
        return items.stream()
                   .filter(item -> item.getCategory().equals(category))
                   .collect(Collectors.toList());
    }
    
    public List<InventoryItem> getLowStockItems(int threshold) {
        return items.stream()
                   .filter(item -> item.getStock() < threshold)
                   .collect(Collectors.toList());
    }
    
    public double getTotalInventoryValue() {
        return items.stream()
                   .mapToDouble(item -> item.getPrice() * item.getStock())
                   .sum();
    }
    
    public void updateItemQuantity(String itemId, int newQuantity) {
        InventoryItem item = getItem(itemId);
        if (item != null) {
            item.setStock(newQuantity);
        }
    }
    
    public void updateItemPrice(String itemId, double newPrice) {
        InventoryItem item = getItem(itemId);
        if (item != null) {
            item.setPrice(newPrice);
        }
    }
} 