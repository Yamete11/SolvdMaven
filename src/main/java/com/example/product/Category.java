package com.example.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Objects;

public class Category {
    private String title;
    private double vat;

    public Category() {}

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

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    public static Category fromJson(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Category.class);
    }


    @Override
    public String toString() {
        return "Category: " + title + '\n' +
                "VAT: " + vat + " %";
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
