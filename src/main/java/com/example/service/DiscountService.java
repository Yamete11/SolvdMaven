package com.example.service;

import com.example.utils.Discountable;

public class DiscountService {
    public void applySeasonalDiscount(Discountable item, double discountPercentage) {
        System.out.println("Applying seasonal discount of " + discountPercentage + "%");
        item.applyDiscount(discountPercentage);
    }

    public void removeDiscount(Discountable item) {
        System.out.println("Removing discount from item");
        item.removeDiscount();
    }
}
