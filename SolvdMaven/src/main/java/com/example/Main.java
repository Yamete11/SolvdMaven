package com.example;

import com.example.ECom;
import com.example.user.Customer;
import com.example.utils.UserUtils;
import com.example.exception.*;
import com.example.product.*;
import com.example.user.*;
import com.example.order.*;
import com.example.utils.UserUtils;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static boolean run = true;

    public static void main(String[] args) {
        ECom eCom = new ECom();
        Scanner scanner = new Scanner(System.in);
        printInfo();
        while (run) {
            System.out.print("Enter command: ");
            String command = scanner.nextLine();
            if (command.length() == 0) {
                System.out.println("You have to enter a command");
            } else {
                switch (command) {
                    case "-cnc", "--create-new-customer" -> {
                        Customer newCustomer = UserUtils.createNewCustomer();
                        if (UserUtils.validateUser(newCustomer)) {
                            try {
                                eCom.addUser(newCustomer);
                                System.out.println("Customer added successfully");
                            } catch (InvalidUserException e) {
                                System.err.println("Error adding user: " + e.getMessage());
                            }
                        } else {
                            System.out.println("Invalid customer data");
                        }
                    }
                    case "-cna", "--create-new-admin" -> {
                        Admin newAdmin = UserUtils.createNewAdmin();
                        if (UserUtils.validateUser(newAdmin)) {
                            try {
                                eCom.addUser(newAdmin);
                                System.out.println("Admin added successfully");
                            } catch (InvalidUserException e) {
                                System.err.println("Error adding admin: " + e.getMessage());
                            }
                        } else {
                            System.out.println("Invalid admin data");
                        }
                    }
                    case "-cc", "--create-category" -> {
                        Category newCategory = UserUtils.createCategory();
                        try {
                            eCom.addCategory(newCategory);
                            System.out.println("Category added.");
                        } catch (InvalidCategoryException e) {
                            System.err.println("Error adding category: " + e.getMessage());
                        }
                    }
                    case "-cl", "--category-list" -> eCom.printAllCategories();
                    case "-cp", "--create-product" -> {
                        Product newProduct = UserUtils.createProduct(eCom.getCategories());
                        System.out.print("Enter stock quantity for the product: ");
                        int stockQuantity = scanner.nextInt();
                        scanner.nextLine();
                        try {
                            eCom.addProduct(newProduct, stockQuantity);
                            System.out.println("Product added.");
                        } catch (DuplicateProductException | ProductOutOfStockException e) {
                            System.err.println("Error adding product: " + e.getMessage());
                        }
                    }
                    case "-po", "--print-orders" -> eCom.printAllOrders();
                    case "-pu", "--print-users" -> eCom.printAllUsers();
                    case "-q", "--quit" -> {
                        run = false;
                        System.out.println("Exiting the program");
                    }
                    default -> System.out.println("Unknown command: " + command);
                }
            }
        }


    }

    private static void printInfo() {
        System.out.println("========================== SolvdApp ==========================");
        System.out.println("This program allows you to manage orders, products, and invoices.");
        System.out.println("Commands:");
        System.out.println("-cnc : --create-new-customer : Creates a new customer");
        System.out.println("-cna : --create-new-admin : Creates a new admin");
        System.out.println("-cc : --create-category : Creates a new category");
        System.out.println("-cl : --category-list : Print all the categories");
        System.out.println("-cp : --create-product : Create a new product");
        System.out.println("-po : --print-orders : Print all orders");
        System.out.println("-pu : --print-users : Print all users");
        System.out.println("-q : --quit : Quits the program.");
    }
}
