package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CartTest {
    private Cart cart;
    // Test Shopping Cart Creation
    @Test
    void testInvalidCartCreationWrongFormat() {
        // Invalid customer ID
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Cart("123456789"); 
        });
        assertEquals("Customer ID does not follow correct format", exception.getMessage());
    }
    @Test
    void testInvalidCartCreationCartIDIsNull() {
        // Invalid customer ID
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Cart(null); 
        });
        assertEquals("Invalid customer ID", exception.getMessage());
    }

    @Test
    void testInvalidCartCreationIDIsEmpty() {
        // Invalid customer ID
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Cart(""); 
        });
        assertEquals("Invalid customer ID", exception.getMessage());
    }

    @Test
    void testCreateNewCart() {
        cart = new Cart("ABC89321BC-A");
        assertNotNull(cart);
        assertEquals(cart.getItems(), new HashMap<>());
    }

    // Test query cart ID
    @Test
    void testQueryCartID() {
        cart = new Cart("ABC89321BC-Q");
        UUID cartID = cart.getCartID();
        assertNotNull(cartID);
        assertEquals(4, cartID.version());
    }

    //Test get customer ID
    @Test 
    void testGetCartId() {
        cart = new Cart("ABC89321BC-Q");
        assertEquals("ABC89321BC-Q", cart.getCustomerId());
    }

    // Add Items to cart
    @Test
    void testAddValidItemToCart() {
        cart = new Cart("ABC89321BC-Q");
        cart.addItem("Apple", 10);
        assertEquals(1, cart.getItemCount());
    }

    @Test
    void testAddInvalidItemToCart() {
        cart = new Cart("ABC89321BC-Q");
         Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cart.addItem("DRUGS", 50); 
        });
        assertEquals("Item is not in the catalog", exception.getMessage());
    }

    @Test
    void testInsertScriptIntoCart() {
        cart = new Cart("ABC89321BC-Q");
         Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cart.addItem("<html><head><script src='https://example.com/script.js'></script></head><body></body></html>", 50); 
        });
        assertEquals("Item is not in the catalog", exception.getMessage());
    }

    @Test
    void testAddInvalidQuantityToCart() {
        cart = new Cart("ABC89321BC-Q");
         Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cart.addItem("Banana", 5000); 
        });
        assertEquals("Cannot make single transaction of more than 100 items", exception.getMessage());
    }

    @Test
    void testAddNegativeQuantityToCart() {
        cart = new Cart("ABC89321BC-Q");
         Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cart.addItem("Banana", -5); 
        });
        assertEquals("Cannot put less than zero items in the cart", exception.getMessage());
    }

    // Test Query items from cart
    @Test
    void testCartMatchesExpectedContents() {
        // Create a new Cart and add items
        Cart cart = new Cart("ABC89321BC-Q");
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
        Cart cart = new Cart("ABC89321BC-Q");
        cart.addItem("Apple", 3);
        Map<String, Integer> items = cart.getItems();
        assertThrows(UnsupportedOperationException.class, () -> {
            items.put("Banana", 2);
        });
    }
    
    @Test
    void testRemoveItemFromCart() {
        cart = new Cart("ABC89321BC-Q");
        cart.addItem("Ice Cream", 10);
        cart.removeItem("Ice Cream");
        assertEquals(0, cart.getItemCount());
    }

    @Test
    void testUpdateItemFromCart() {
        cart = new Cart("ABC89321BC-A");
        cart.addItem("Ice Cream", 10);
        cart.updateItem("Ice Cream", 5);
        assertEquals(5, cart.getItems().get("Ice Cream"));
    }
}
