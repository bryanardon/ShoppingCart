package com.example;

public class Quantity {
    private final int numOfItems;
    Quantity(int numOfItems) {
        if (numOfItems <= 0) {
            throw new IllegalArgumentException("Cannot put less than zero items in the cart");
        }
        if (numOfItems > 100) {
            throw new IllegalArgumentException("Cannot make single transaction of more than 100 items");
        }
        this.numOfItems = numOfItems;
  }
    public int getNumOfItems() {
        return this.numOfItems;
    }

}
    

