package com.example;

import com.example.product.Product;
import com.example.product.Product;

import java.util.List;
import java.util.Optional;

public interface Searchable {
    Optional<Product> searchProductByTitle(String name);
    Optional<List<Product>> filterProductByCategory(String category);
}
