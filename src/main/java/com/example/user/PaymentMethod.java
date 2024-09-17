package com.example.user;

public enum PaymentMethod {
    CARD("Card Payment"),
    CASH("Cash Payment");

    private final String description;

    PaymentMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
