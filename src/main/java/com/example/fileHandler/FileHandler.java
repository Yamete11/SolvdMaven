package com.example.fileHandler;

import com.example.order.Order;
import com.example.product.Category;
import com.example.product.Product;
import com.example.user.Admin;
import com.example.user.User;
import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FileHandler {
    private final File userFile = new File("db/users.txt");
    //private final File orderFile = new File("db/orders.txt");
    private final File categoryFile = new File("db/categories.txt");
    private final File productFile = new File("db/products.txt");

    public void saveUser(User user) throws IOException {
        appendToFile(userFile, user.toString());
    }

    public void saveCategory(Category category) throws IOException {
        appendToFile(categoryFile, category.toString());
    }

    public void saveProduct(Product product) throws IOException {
        appendToFile(productFile, product.toString());
    }

    private void appendToFile(File file, String line) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public List<User> loadUsers() throws IOException {
        List<String> lines = FileUtils.readLines(userFile, "UTF-8");
        List<User> users = new ArrayList<>();

        StringBuilder userData = new StringBuilder();
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            if (line.startsWith("User Information:")) {
                if (userData.length() > 0) {
                    User user = Admin.fromString(userData.toString());
                    users.add(user);
                    userData.setLength(0);
                }
            }

            userData.append(line).append("\n");
        }

        if (userData.length() > 0) {
            User user = Admin.fromString(userData.toString());
            users.add(user);
        }

        return users;
    }


    public Set<Category> loadCategories() throws IOException {
        List<String> lines = FileUtils.readLines(categoryFile, "UTF-8");
        Set<Category> categories = new HashSet<>();

        for (int i = 0; i < lines.size(); i += 2) {
            String categoryData = lines.get(i) + "\n" + lines.get(i + 1);
            Category category = Category.fromString(categoryData);
            categories.add(category);
        }

        return categories;
    }

    public Map<Product, Integer> loadProducts() throws IOException {
        Set<Category> categories = loadCategories();
        List<String> lines = FileUtils.readLines(productFile, "UTF-8");
        Map<Product, Integer> products = new HashMap<>();

        StringBuilder productData = new StringBuilder();
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            if (line.startsWith("Title: ")) {
                if (productData.length() > 0) {
                    Product product = Product.fromString(productData.toString(), categories);
                    products.put(product, product.getStockQuantity());
                    productData.setLength(0);
                }
            }

            productData.append(line).append("\n");
        }

        if (productData.length() > 0) {
            Product product = Product.fromString(productData.toString(), categories);
            products.put(product, product.getStockQuantity());
        }

        return products;
    }

}
