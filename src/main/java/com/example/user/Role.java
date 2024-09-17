package com.example.user;

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
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(input) || role.getDescription().equalsIgnoreCase(input)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + input);
    }

    @Override
    public String toString() {
        return name() + " (" + description + ")";
    }
}
