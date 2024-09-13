package com.example;

import com.example.product.Product;
import com.example.product.Product;

import java.util.List;

public interface Searchable {
    Product searchProductByTitle(String name);
    List<Product> filterProductByCategory(String category);
}
