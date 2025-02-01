package com.example;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CartTest {
    private Cart cart;
    private final String validCustomerId = "ABC89321BC-Q";

    @Test
    void testInvalidCartCreationWrongFormat() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Cart("123456789"); 
        });

        assertEquals("Customer ID does not follow correct format", exception.getMessage());
    }

    @Test
    void testInvalidCartCreationCartIDIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Cart(null); 
        });

        assertEquals("Invalid customer ID", exception.getMessage());
    }

    @Test
    void testInvalidCartCreationIDIsEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Cart(""); 
        });

        assertEquals("Invalid customer ID", exception.getMessage());
    }

    @Test
    void testCreateNewCart() {
        cart = new Cart(validCustomerId);

        assertNotNull(cart);
        assertEquals(cart.getItems(), new HashMap<>());
    }

    @Test
    void testQueryCartID() {
        cart = new Cart(validCustomerId);
        UUID cartID = cart.getCartID();

        assertNotNull(cartID);
        assertEquals(4, cartID.version());
    }

    @Test 
    void testGetCartId() {
        cart = new Cart(validCustomerId);

        assertEquals(validCustomerId, cart.getCustomerId());
    }

    @Test
    void testAddValidItemToCart() {
        cart = new Cart(validCustomerId);
        cart.addItem("Apple", 10);

        assertEquals(1, cart.getItemCount());
    }

    @Test
    void testAddMultipleItemsToCart() {
        cart = new Cart(validCustomerId);

        cart.addItem("Apple", 3);
        cart.addItem("Banana", 2);
        cart.addItem("Ice Cream", 5);

        assertEquals(3, cart.getItemCount());
    }

    @Test
    void testAddInvalidItemToCart() {
        cart = new Cart(validCustomerId);
         Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cart.addItem("DRUGS", 50); 
        });

        assertEquals("Item is not in the catalog", exception.getMessage());
    }

    @Test
    void testInsertScriptIntoCart() {
        cart = new Cart(validCustomerId);
         Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cart.addItem("<html><head><script src='https://example.com/script.js'></script></head><body></body></html>", 50); 
        });

        assertEquals("Item is not in the catalog", exception.getMessage());
    }

    @Test
    void testAddInvalidQuantityToCart() {
        cart = new Cart(validCustomerId);
         Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cart.addItem("Banana", 5000); 
        });

        assertEquals("Cannot make single transaction of more than 100 items", exception.getMessage());
    }

    @Test
    void testAddNegativeQuantityToCart() {
        cart = new Cart(validCustomerId);
         Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cart.addItem("Banana", -5); 
        });

        assertEquals("Cannot put less than zero items in the cart", exception.getMessage());
    }

    @Test
    void testCartMatchesExpectedContents() {
        cart = new Cart(validCustomerId);
        cart.addItem("Apple", 3);
        cart.addItem("Banana", 2);

        Map<String, Integer> expectedItems = Map.of(
                "Apple", 3,
                "Banana", 2
        );

        Map<String, Integer> actualItems = cart.getItems();

        assertEquals(expectedItems, actualItems);
    }

    // Cannot modify items in the cart
    @Test 
    void testImmutableCartItems() {
        cart = new Cart(validCustomerId);

        cart.addItem("Apple", 3);
        Map<String, Integer> items = cart.getItems();

        assertThrows(UnsupportedOperationException.class, () -> {
            items.put("Banana", 2);
        });
    }
    
    @Test
    void testRemoveItemFromCart() {
        cart = new Cart(validCustomerId);

        cart.addItem("Ice Cream", 10);
        cart.removeItem("Ice Cream");

        assertEquals(0, cart.getItemCount());
    }

    @Test
    void testUpdateItemFromCart() {
        cart = new Cart(validCustomerId);

        cart.addItem("Ice Cream", 10);
        cart.updateItem("Ice Cream", 5);

        assertEquals(5, cart.getItems().get("Ice Cream"));
    }

    @Test
    void testGetCartTotal() {
        cart = new Cart(validCustomerId);

        cart.addItem("Ice Cream", 3);
        cart.addItem("Apple", 2);

        assertEquals(12.00, cart.getTotal());
    }
    
    @Test
    void testGetMaxTotalForSingleCart() {
        cart = new Cart(validCustomerId);

        cart.addItem("Apple", 100);
        cart.addItem("Banana",100);
        cart.addItem("Cookie", 100);
        cart.addItem("Doughnuts", 100);
        cart.addItem("Grapes", 100);
        cart.addItem("Honey", 100);
        cart.addItem("Ice Cream", 100);

        assertEquals(7750, cart.getTotal());
    }
}
