package com.example.product;

public interface Reviewable {
    void addReview(String reviewerName, String comment, int rating);
    double getAverageRating();
}
