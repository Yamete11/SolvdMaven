package com.example.product;

import java.time.LocalDate;
import java.util.Objects;

public class Review {
    private String title;
    private String comment;
    private int rating;
    private final LocalDate reviewDate;

    public Review(String title, String comment, int rating) {
        this.title = title;
        this.comment = comment;
        this.rating = rating;
        this.reviewDate = LocalDate.now();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    @Override
    public String toString() {
        return "Title: " + title + '\n' +
                "Rating: " + rating + "/5" + '\n' +
                "Comment: " + comment + '\n' +
                "Date: " + reviewDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, comment, rating, reviewDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return rating == review.rating &&
                title.equals(review.title) &&
                comment.equals(review.comment) &&
                reviewDate.equals(review.reviewDate);
    }
}
