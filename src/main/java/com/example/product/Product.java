package com.example.product;

import com.example.utils.Discountable;
import com.example.utils.Taxable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Product implements Reviewable, Taxable, Discountable {
    private String title;
    private double price;
    private Category category;
    private List<Review> reviews;
    private double taxRate;
    private double discountPercentage;
    private int stockQuantity;

    public Product() {
        this.reviews = new ArrayList<>();
    }

    public Product(String title, double price, Category category, int stockQuantity) {
        this.title = title;
        this.price = price;
        this.category = category;
        this.reviews = new ArrayList<>();
        this.taxRate = category.getVat();
        this.stockQuantity = stockQuantity;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    public static Product fromJson(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(json, Product.class);
    }

    @Override
    public void applyDiscount(double percentage) {
        if (percentage < 0 || percentage > 100) {
            System.out.println("Invalid discount percentage. Must be between 0 and 100");
        } else {
            discountPercentage = percentage;
            price -= price * (discountPercentage / 100);
            System.out.println("Applied " + percentage + "% discount. New price: " + price);
        }
    }

    @Override
    public void removeDiscount() {
        price += price * (discountPercentage / 100);
        discountPercentage = 0;
        System.out.println("Discount removed. Price reset to original: " + price);
    }

    @Override
    public double calculateTax() {
        return price * (taxRate / 100);
    }

    @Override
    public double getAverageTaxRate() {
        return taxRate;
    }

    @Override
    public void addReview(String reviewerName, String comment, ReviewRating rating) {
        reviews.add(new Review(reviewerName, comment, rating));
    }

    @Override
    public double getAverageRating() {
        return reviews.stream()
                .mapToDouble(review -> review.getRating().getRating())
                .average()
                .orElse(0.0);
    }

    public double calculatePriceWithVAT() {
        double vatAmount = price * (category.getVat() / 100);
        return price + vatAmount;
    }

    @Override
    public String toString() {
        StringBuilder productInfo = new StringBuilder("Title: " + title + '\n' +
                "Price: " + price + " (without VAT)" + '\n' +
                "Price with VAT: " + calculatePriceWithVAT() + '\n' +
                "Stock Quantity: " + stockQuantity + '\n' +
                category.toString() + '\n' +
                "Average Rating: " + getAverageRating() + "/5" + '\n' +
                "Reviews:\n");

        if (reviews != null && !reviews.isEmpty()) {
            reviews.stream()
                    .forEach(review -> productInfo.append(review.toString()).append("\n\n"));
        } else {
            productInfo.append("No reviews available.\n");
        }

        return productInfo.toString();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategory(Category category) {
        this.category = category;
        this.taxRate = category.getVat();
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, price, category);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 &&
                title.equals(product.title) &&
                category.equals(product.category);
    }
}
