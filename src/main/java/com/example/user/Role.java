package com.example.user;

import java.util.Arrays;

public enum Role {
    SYSTEM_ADMIN("System Administrator"),
    MANAGER("Manager");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Role fromString(String input) {
        return Arrays.stream(Role.values())
                .filter(role -> role.name().equalsIgnoreCase(input) || role.getDescription().equalsIgnoreCase(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid role: " + input));
    }

    @Override
    public String toString() {
        return name() + " (" + description + ")";
    }
}
