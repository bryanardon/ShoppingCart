package com.example;

import java.util.regex.Pattern;

final class CustomerId {
    private final String lexeme;

    CustomerId(String lexeme) {
        if (lexeme == null || lexeme.isEmpty()) {
            throw new IllegalArgumentException("Invalid customer ID");
        }
        if (!Pattern.matches("^(\\p{L}{3}\\d{5}\\p{L}{2}-[AQ])$", lexeme)){
            throw new IllegalArgumentException("Customer ID does not follow correct format");
        }
        this.lexeme = lexeme;
    }

    public String getCustomerId() {
        return this.lexeme;
    }
}
