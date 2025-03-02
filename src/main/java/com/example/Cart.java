package com.example;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Cart {
    private final HashMap<String,Integer> items;
    private final UUID cartId;
    private final CustomerId customerid;
    private final Map<String, Double> catalog;
    private static final int LONGEST_CATALOG_ITEM_NAME = 10;

    Cart(String customerid) {
        this.cartId = generateCartId();
        this.customerid = new CustomerId(customerid);
        this.items = new HashMap<>();
        this.catalog = createCatalog();
    }

    private Map<String, Double> createCatalog() {
        HashMap<String, Double> possibleItems = new HashMap<>();
        possibleItems.put("Apple", 1.50);
        possibleItems.put("Banana", 1.00);
        possibleItems.put("Cookie", 5.00);
        possibleItems.put("Doughnuts", 7.00);
        possibleItems.put("Grapes", 50.);
        possibleItems.put("Honey", 10.00);
        possibleItems.put("Ice Cream", 3.00);
        return Collections.unmodifiableMap(possibleItems);
    }

    private UUID generateCartId() {
        return UUID.randomUUID();
    }

    public UUID getCartID() {
        // Return a copy of the UUID
        return new UUID(this.cartId.getMostSignificantBits(), this.cartId.getLeastSignificantBits());
    }

    public String getCustomerId() {
        return new String(this.customerid.getCustomerId());
    }

    public void addItem(String itemName, int quantity) {
        if (itemName == null || itemName.isEmpty() || this.catalog.get(itemName) == null || itemName.length() > LONGEST_CATALOG_ITEM_NAME) {
            throw new IllegalArgumentException("Item is not in the catalog");
        }
        Quantity q = new Quantity(quantity);
        this.items.put(itemName, q.getNumOfItems());
    }

    public void removeItem(String itemName) {
         if (itemName == null || itemName.isEmpty() || this.catalog.get(itemName) == null || itemName.length() > LONGEST_CATALOG_ITEM_NAME) {
            throw new IllegalArgumentException("Item is not in the catalog");
        }
       this.items.remove(itemName);
    }

    public void updateItem(String itemName, int quantity) {
        // Same logic for updating an item
        addItem(itemName, quantity);
    }

    public int getItemCount() {
        return items.size();
    }

    public Map<String, Integer> getItems() {
        return Collections.unmodifiableMap(new HashMap<>(this.items));
    }

    public double getTotal() {
        double total = 0;
        for (Map.Entry<String, Integer> entry : this.items.entrySet()) {
            total += this.catalog.get(entry.getKey()) * entry.getValue();
        }
        return total;
    }
}
