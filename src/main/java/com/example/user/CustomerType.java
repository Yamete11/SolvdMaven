package com.example.user;

import java.util.Arrays;

public enum CustomerType {
    REGULAR("Regular Customer", 0.0),
    LOYAL("Loyal Customer", 5.0),
    VIP("VIP Customer", 10.0);

    private final String description;
    private final double discountRate;

    CustomerType(String description, double discountRate) {
        this.description = description;
        this.discountRate = discountRate;
    }

    public String getDescription() {
        return description;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public static CustomerType fromString(String type) {
        return Arrays.stream(values())
                .filter(ct -> ct.name().equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No enum constant for customer type " + type));
    }
}
