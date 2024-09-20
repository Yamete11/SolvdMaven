package com.example;

import com.example.product.Category;
import com.example.product.Product;
import com.example.order.Order;
import com.example.user.Customer;
import com.example.user.Admin;
import com.example.user.CustomerType;
import com.example.utils.UserUtils;
import com.example.exception.InvalidUserException;
import com.example.exception.InvalidCategoryException;
import com.example.exception.DuplicateProductException;
import com.example.exception.ProductOutOfStockException;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static boolean isRunning = true;

    public static void main(String[] args) {
        ECom eCom = new ECom();
        Scanner scanner = new Scanner(System.in);

        displayWelcomeMessage();

        while (isRunning) {
            System.out.print("Enter command: ");
            String command = scanner.nextLine().trim();

            if (command.isEmpty()) {
                System.out.println("You must enter a command.");
            } else {
                handleCommand(command, eCom);
            }
        }



        /*ECom eCom = new ECom();
        eCom.printAllProducts(System.out::println);

        List<Product> expensiveProducts = eCom.filterProductsByPrice(150.0);
        expensiveProducts.forEach(System.out::println);*/

        /*ECom eCom = new ECom();
        eCom.printAllProducts(System.out::println);

        List<String> productDescriptions = eCom.transformProducts(product ->
                String.format("Product: %s, Price: %.2f, Stock: %d",
                        product.getTitle(), product.getPrice(), product.getStockQuantity())
        );

        productDescriptions.forEach(System.out::println);*/
    }

    private static void handleCommand(String command, ECom eCom) {
        switch (command) {
            case "-cc", "--create-category" -> handleCreateCategory(eCom);
            case "-cp", "--create-product" -> handleCreateProduct(eCom);
            case "-pc", "--print-categories" -> eCom.printAllCategories();
            case "-pp", "--print-products" -> eCom.printAllProducts(System.out::println);
            case "-h", "--help" -> displayHelpMessage();
            case "-q", "--quit" -> {
                isRunning = false;
                System.out.println("Exiting the program.");
            }
            default -> System.out.println("Unknown command: " + command);
        }
    }

    private static void handleCreateCategory(ECom eCom) {
        Category newCategory = UserUtils.createCategory();
        try {
            eCom.addCategory(newCategory);
            System.out.println("Category added.");
        } catch (InvalidCategoryException e) {
            System.err.println("Error adding category: " + e.getMessage());
        }
    }

    private static void handleCreateProduct(ECom eCom) {
        Product newProduct = UserUtils.createProduct(eCom.getCategories());
        try {
            eCom.addProduct(newProduct, newProduct.getStockQuantity());
            System.out.println("Product added.");
        } catch (DuplicateProductException | ProductOutOfStockException e) {
            System.err.println("Error adding product: " + e.getMessage());
        }
    }

    private static void displayWelcomeMessage() {
        System.out.println("========================== SolvdApp ==========================");
        System.out.println("This program allows you to manage orders, products, and invoices.");
        displayHelpMessage();
    }

    private static void displayHelpMessage() {
        System.out.println("Commands:");
        System.out.println("-cc : --create-category : Creates a new category");
        System.out.println("-cp : --create-product : Creates a new product");
        System.out.println("-pc : --print-categories : Print all categories");
        System.out.println("-pp : --print-products : Print all products");
        System.out.println("-h : --help : Show this help message");
        System.out.println("-q : --quit : Quits the program.");
    }
}
