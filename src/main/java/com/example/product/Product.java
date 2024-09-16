package com.example.product;

import com.example.utils.Discountable;
import com.example.utils.Taxable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Product implements Reviewable, Taxable, Discountable {
    private String title;
    private double price;
    private int stockQuantity;
    private Category category;
    private List<Review> reviews;
    private double taxRate;
    private double discountPercentage;

    public Product(String title, double price, int stockQuantity, Category category) {
        this.title = title;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.reviews = new ArrayList<>();
        this.taxRate = category.getVat();
    }

    public Product(String title, double price, Category category) {
        this.title = title;
        this.price = price;
        this.category = category;
        this.reviews = new ArrayList<>();
        this.taxRate = category.getVat();
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
    public void addReview(String reviewerName, String comment, int rating) {
        reviews.add(new Review(reviewerName, comment, rating));
    }

    @Override
    public double getAverageRating() {
        if (reviews.isEmpty()) return 0.0;
        double totalRating = 0.0;
        for (Review review : reviews) {
            totalRating += review.getRating();
        }
        return totalRating / reviews.size();
    }

    public void updateStock(int quantity) {
        if (this.stockQuantity + quantity < 0) {
            System.out.println("You cannot reduce stock below zero");
        } else {
            this.stockQuantity += quantity;
            System.out.println("Stock updated successfully. New stock quantity: " + this.stockQuantity);
        }
    }

    public double calculatePriceWithVAT() {
        double vatAmount = price * (category.getVat() / 100);
        return price + vatAmount;
    }


    public static Product fromString(String productData, Set<Category> availableCategories) {
        String[] lines = productData.split("\n");
        for(String str : lines){
            System.out.println(str);
        }

        if (lines.length < 4) {
            throw new IllegalArgumentException("Invalid Product data: " + productData);
        }

        String title = lines[0].replace("Title:", "").trim();

        String priceString = lines[1].replace("Price:", "").replace(" (without VAT)", "").trim();
        double price;
        try {
            price = Double.parseDouble(priceString);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid price format: " + priceString, e);
        }

        String stockQuantityString = lines[3].replace("Stock Quantity:", "").trim();
        int stockQuantity;
        try {
            stockQuantity = Integer.parseInt(stockQuantityString);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid stock quantity format: " + stockQuantityString, e);
        }

        String categoryName = lines[4].replace("Category:", "").trim();
        Category category = getCategoryByName(categoryName, availableCategories);
        if (category == null) {
            throw new IllegalArgumentException("Category not found: " + categoryName);
        }

        return new Product(title, price, stockQuantity, category);
    }

    private static Category getCategoryByName(String name, Set<Category> categories) {
        for (Category category : categories) {
            if (category.getTitle().equalsIgnoreCase(name)) {
                return category;
            }
        }
        return null;
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
            for (Review review : reviews) {
                productInfo.append(review.toString()).append("\n\n");
            }
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

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public int getStockQuantity() {
        return stockQuantity;
    }

    public Category getCategory() {
        return category;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, price, stockQuantity, category);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 &&
                stockQuantity == product.stockQuantity &&
                title.equals(product.title) &&
                category.equals(product.category);
    }

}
