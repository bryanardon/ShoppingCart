package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class Cart {
    private final HashMap<String,Integer> items;
    private final UUID cartId;
    private final CustomerId customerid;
    private final Map<String, Double> catalog;
    private final int LONGEST_CATALOG_ITEM_NAME = 10;

    Cart(String customerid) {
        this.cartId = generateCartId();
        this.customerid = new CustomerId(customerid);
        this.items = new HashMap<String,Integer>();
        this.catalog = createCatalog();
    }

    private Map<String, Double> createCatalog() {
        HashMap<String, Double> catalog = new HashMap<>();
        catalog.put("Apple", 1.50);
        catalog.put("Banana", 1.00);
        catalog.put("Cookie", 5.00);
        catalog.put("Doughnuts", 7.00);
        catalog.put("Grapes", 50.);
        catalog.put("Honey", 10.00);
        catalog.put("Ice Cream", 3.00);
        return Collections.unmodifiableMap(catalog);
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
        for (Map.Entry<String, Integer> entry : this.items.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    public void removeItem(String itemName) {
         if (itemName == null || itemName.isEmpty() || this.catalog.get(itemName) == null || itemName.length() > LONGEST_CATALOG_ITEM_NAME) {
            throw new IllegalArgumentException("Item is not in the catalog");
        }
       this.items.remove(itemName);
    }

    public void updateItem(String itemName, int quantity) {
        if (itemName == null || itemName.isEmpty() || this.catalog.get(itemName) == null || itemName.length() > LONGEST_CATALOG_ITEM_NAME) {
            throw new IllegalArgumentException("Item is not in the catalog");
        }
        Quantity q = new Quantity(quantity);
        this.items.put(itemName, q.getNumOfItems());
    }

    public int getItemCount() {
        return items.size();
    }

    public Map<String, Integer> getItems() {
        return Collections.unmodifiableMap(new HashMap<>(this.items));
    }
}

//  class Item {
//     private final String name;
//     private final double price;

//     public Item(String name, double price) {
//         if (name == null || name.isEmpty() || price < 0) {
//             throw new IllegalArgumentException("Invalid item details");
//         }
//         this.name = name;
//         this.price = price;
//     }

//     public String getName() {
//         return name;
//     }

//     public double getPrice() {
//         return price;
//     }
// }
