package com.example.product;

import java.util.Arrays;

public enum ReviewRating {
    ONE_STAR("Very Poor", 1),
    TWO_STAR("Poor", 2),
    THREE_STAR("Average", 3),
    FOUR_STAR("Good", 4),
    FIVE_STAR("Excellent", 5);

    private final String description;
    private final int rating;

    ReviewRating(String description, int rating) {
        this.description = description;
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public int getRating() {
        return rating;
    }

    public static ReviewRating fromRating(int rating) {
        return Arrays.stream(values())
                .filter(rr -> rr.rating == rating)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No enum constant for rating " + rating));
    }
}
