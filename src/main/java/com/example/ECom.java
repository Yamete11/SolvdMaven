package com.example;

import com.example.fileHandler.FileHandler;
import com.example.order.Order;
import com.example.custom.CustomLinkedList;
import com.example.exception.*;
import com.example.order.Order;
import com.example.product.Category;
import com.example.product.Product;
import com.example.user.User;

import com.example.utils.LambdaFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ECom implements Searchable {
    private Supplier<Stream<Product>> productStreamSupplier;
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
        this.productStreamSupplier = () -> products.keySet().stream();
        EComInstance++;
        LOGGER.info("ECom class instance created");
    }

    public ECom() {
        this.orders = new CustomLinkedList<>();
        this.users = new ArrayList<>();
        this.categories = new HashSet<>();
        this.products = new HashMap<>();
        this.productStreamSupplier = () -> products.keySet().stream();
        EComInstance++;
        loadData();
        LOGGER.info("ECom class instance created");
    }

    private void loadData() {
        this.categories = fileHandler.loadCategories();
        this.products = fileHandler.loadProducts();
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        LOGGER.info("Order added: " + order.toString());
    }

    public List<String> transformProducts(Function<Product, String> transformer) {
        return productStreamSupplier.get()
                .map(transformer)
                .toList();
    }

    public Optional<List<Product>> filterProductsByPrice(double minPrice) {
        List<Product> filteredProducts = productStreamSupplier.get()
                .filter(product -> product.getPrice() >= minPrice)
                .toList();
        return filteredProducts.isEmpty() ? Optional.empty() : Optional.of(filteredProducts);
    }

    public void addUser(User user) {
        if (user == null) {
            throw new InvalidUserException("User cannot be null");
        }
        this.users.add(user);
        LOGGER.info("User added successfully.");
    }

    public void addCategory(Category category) {
        if (category == null || category.getTitle() == null || category.getTitle().isEmpty()) {
            throw new InvalidCategoryException("Category is invalid or has no title");
        }
        if (categories.add(category)) {
            LOGGER.info("Category added: " + category.getTitle());
            fileHandler.saveCategory(category);
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
        fileHandler.saveProduct(product);
    }

    @Override
    public Optional<Product> searchProductByTitle(String title) {
        return products.keySet().stream()
                .filter(product -> product.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }


    @Override
    public Optional<List<Product>> filterProductByCategory(String category) {
        List<Product> filteredProducts = filterProducts(product -> product.getCategory().getTitle().equalsIgnoreCase(category));
        return filteredProducts.isEmpty() ? Optional.empty() : Optional.of(filteredProducts);
    }


    public List<Product> filterProducts(Predicate<Product> condition) {
        return products.keySet().stream()
                .filter(condition)
                .collect(Collectors.toList());
    }


    public void printAllOrders() {
        if (orders.isEmpty()) {
            System.out.println("The list of orders is empty");
        } else {
            orders.stream().forEach(System.out::println);
        }
    }


    public void printAllUsers() {
        if (users.isEmpty()) {
            System.out.println("The list of users is empty");
        } else {
            users.stream().forEach(System.out::println);
        }
    }


    public void printAllCategories() {
        if (categories.isEmpty()) {
            System.out.println("The list of categories is empty");
        } else {
            categories.forEach(category -> System.out.println(category + "\n"));
        }
    }


    public void printAllProducts(Consumer<Product> productConsumer) {
        if (products.isEmpty()) {
            System.out.println("The list of products is empty");
        } else {
            products.forEach((product, stock) -> productConsumer.accept(product));
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

    public void LambdasMethods() {
        System.out.println("Product prices:");
        products.keySet().forEach(product ->
                System.out.println(LambdaFunctions.getProductPrice.apply(product))
        );

        System.out.println("User emails:");
        users.forEach(user ->
                System.out.println(LambdaFunctions.getUserEmail.apply(user))
        );

        System.out.println("Formatted products:");
        products.keySet().forEach(product ->
                System.out.println(LambdaFunctions.formatProduct.apply(product))
        );
    }
}
