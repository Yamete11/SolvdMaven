package com.example.utils;

import com.example.product.Product;
import com.example.user.User;

public class LambdaFunctions {
    public static UtilInterface<Product, Double> getProductPrice = (product) -> product.getPrice();

    public static UtilInterface<User, String> getUserEmail = (user) -> user.getEmail();

    public static UtilInterface<Product, String> formatProduct = (product) ->
            String.format("Product: %s, Price: %.2f, Stock: %d",
                    product.getTitle(), product.getPrice(), product.getStockQuantity());
}
