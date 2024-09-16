package com.example;

import com.example.fileHandler.FileHandler;
import com.example.order.Order;
import com.example.custom.CustomLinkedList;
import com.example.exception.*;
import com.example.order.Order;
import com.example.product.Category;
import com.example.product.Product;
import com.example.user.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class ECom implements Searchable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ECom.class);
    private List<Order> orders;
    private List<User> users;
    private Set<Category> categories;
    private Map<Product, Integer> products;
    private static int EComInstance;

    private final FileHandler fileHandler = new FileHandler();


    public ECom(List<Order> orders, List<User> users, Set<Category> categories) {
        this.orders = orders;
        this.users = users;
        this.categories = categories;
        this.products = new HashMap<>();
        EComInstance++;
        LOGGER.info("ECom class instance created");
    }

    public ECom() {
        this.orders = new CustomLinkedList<>();
        this.users = new ArrayList<>();
        this.categories = new HashSet<>();
        this.products = new HashMap<>();
        EComInstance++;
        loadData();
        LOGGER.info("ECom class instance created");
    }

    private void loadData() {
        try {
            this.categories = fileHandler.loadCategories();
            this.products = fileHandler.loadProducts();
        } catch (IOException e) {
            LOGGER.error("Error loading data: " + e.getMessage());
        }
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        LOGGER.info("Order added: " + order.toString());
        try {
            fileHandler.saveOrder(order);
        } catch (IOException e) {
            LOGGER.error("Error saving order: " + e.getMessage());
        }
    }

    public void addUser(User user) {
        if (user == null) {
            throw new InvalidUserException("User cannot be null");
        }
        this.users.add(user);
        LOGGER.info("User added successfully.");
        try {
            fileHandler.saveUser(user);
        } catch (IOException e) {
            LOGGER.error("Error saving user: " + e.getMessage());
        }
    }

    public void addCategory(Category category) {
        if (category == null || category.getTitle() == null || category.getTitle().isEmpty()) {
            throw new InvalidCategoryException("Category is invalid or has no title");
        }
        if (categories.add(category)) {
            LOGGER.info("Category added: " + category.getTitle());
            try {
                fileHandler.saveCategory(category);
            } catch (IOException e) {
                LOGGER.error("Error saving category: " + e.getMessage());
            }
        } else {
            System.out.println("Category already exists: " + category.getTitle());
        }
    }

    public void addProduct(Product product, int stockQuantity) throws DuplicateProductException {
        if (stockQuantity <= 0) {
            throw new ProductOutOfStockException("Product stock must be greater than zero");
        }
        if (this.products.containsKey(product)) {
            throw new DuplicateProductException("Product with title " + product.getTitle() + " already exists");
        }
        this.products.put(product, stockQuantity);
        LOGGER.info("Product added successfully with stock quantity: " + stockQuantity);
        try {
            fileHandler.saveProduct(product);
        } catch (IOException e) {
            LOGGER.error("Error saving product: " + e.getMessage());
        }
    }

    @Override
    public Product searchProductByTitle(String title) {
        for (Product product : products.keySet()) {
            if (product.getTitle().equalsIgnoreCase(title)) {
                LOGGER.info("Product found with title: '" + title + "'");
                return product;
            }
        }
        LOGGER.info("No product found with the title: '" + title + "'");
        return null;
    }

    @Override
    public List<Product> filterProductByCategory(String category) {
        List<Product> result = new ArrayList<>();
        for (Product product : products.keySet()) {
            if (product.getCategory().getTitle().equalsIgnoreCase(category)) {
                result.add(product);
            }
        }
        return result;
    }

    public void printAllOrders() {
        if (orders.isEmpty()) {
            System.out.println("The list of orders is empty");
        } else {
            orders.forEach(System.out::println);
        }
    }

    public void printAllUsers() {
        if (users.isEmpty()) {
            System.out.println("The list of users is empty");
        } else {
            users.forEach(System.out::println);
        }
    }

    public void printAllCategories() {
        if (categories.isEmpty()) {
            System.out.println("The list of categories is empty");
        } else {
            categories.forEach(category -> System.out.println(category + "\n"));
        }
    }

    public void printAllProducts() {
        if (products.isEmpty()) {
            System.out.println("The list of products is empty");
        } else {
            for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                Product product = entry.getKey();
                System.out.println(product +  "\n");
            }
        }
    }

    public double calculateTotalRevenue() {
        return orders.stream().mapToDouble(Order::getTotalAmount).sum();
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public static void setEComInstance(int EComInstance) {
        ECom.EComInstance = EComInstance;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<User> getUsers() {
        return users;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public static int getEComInstance() {
        return EComInstance;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("E-Commerce System Overview\n");
        sb.append("==========================\n");

        sb.append("Users:\n");
        if (users.isEmpty()) {
            sb.append("The list of users is empty\n");
        } else {
            users.forEach(user -> sb.append(user).append("\n"));
        }

        sb.append("\nOrders:\n");
        if (orders.isEmpty()) {
            sb.append("The list of orders is empty\n");
        } else {
            orders.forEach(order -> sb.append(order).append("\n"));
        }

        sb.append("\nCategories:\n");
        if (categories.isEmpty()) {
            sb.append("The list of categories is empty\n");
        } else {
            categories.forEach(category -> sb.append(category).append("\n"));
        }

        sb.append("\nProducts:\n");
        if (products.isEmpty()) {
            sb.append("The list of products is empty\n");
        } else {
            for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                Product product = entry.getKey();
                int stock = entry.getValue();
                sb.append(product).append(", Stock: ").append(stock).append("\n");
            }
        }

        sb.append("\nTotal Revenue: $").append(calculateTotalRevenue()).append("\n");
        sb.append("==========================");

        return sb.toString();
    }
}
