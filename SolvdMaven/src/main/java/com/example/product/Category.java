package com.example.product;

import java.util.Objects;

public class Category {
    private String title;
    private double vat;

    public Category(String title, double vat) {
        this.title = title;
        this.vat = vat;
    }

    public static double calculateVAT(double price, double vat) {
        return price * (vat / 100);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public String getTitle() {
        return title;
    }

    public double getVat() {
        return vat;
    }

    @Override
    public String toString() {
        return "Category: " + title + '\n' +
                "VAT: " + vat + "%";
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, vat);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Double.compare(category.vat, vat) == 0 &&
                title.equals(category.title);
    }
}
