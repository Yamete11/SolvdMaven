package com.example.product;

public interface Reviewable {
    void addReview(String reviewerName, String comment, ReviewRating rating);
    double getAverageRating();
}
