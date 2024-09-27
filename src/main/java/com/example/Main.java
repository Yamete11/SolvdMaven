package com.example;

import com.example.product.Category;
import com.example.product.Product;
import com.example.order.Order;
import com.example.threads.Connection;
import com.example.threads.ConnectionPool;
import com.example.threads.RunnableWay;
import com.example.threads.ThreadWay;
import com.example.user.Customer;
import com.example.user.Admin;
import com.example.user.CustomerType;
import com.example.utils.UserUtils;
import com.example.exception.InvalidUserException;
import com.example.exception.InvalidCategoryException;
import com.example.exception.DuplicateProductException;
import com.example.exception.ProductOutOfStockException;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Main {
    private static boolean isRunning = true;

    public static void main(String[] args) throws DuplicateProductException, InterruptedException {

        //Task 1
        /*Thread thread1 = new ThreadWay();
        Thread thread2 = new ThreadWay();

        thread1.start();
        thread2.start();
        Thread runnableThread1 = new Thread(new RunnableWay());
        Thread runnableThread2 = new Thread(new RunnableWay());

        runnableThread1.start();
        runnableThread2.start();

        try {
            thread1.join();
            thread2.join();
            runnableThread1.join();
            runnableThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All threads have completed execution");*/

        //Task 4

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        for (int i = 0; i < 7; i++) {
            final int threadId = i;
            CompletableFuture<Connection> futureConnection = connectionPool.acquireConnection();

            futureConnection.thenAccept(connection -> {
                if (connection != null) {
                    try {
                        connection.connect();
                        Thread.sleep(1000);
                        connection.disconnect();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    } finally {
                        connectionPool.releaseConnection(connection);
                    }
                } else {
                    System.out.println("Thread-" + threadId + " could not acquire a connection.");
                }
            }).exceptionally(ex -> {
                System.err.println("Error acquiring connection: " + ex.getMessage());
                return null;
            });
        }





        /*ECom eCom = new ECom();

        eCom.addProduct(() -> new Product("hello", 10.0, new Category("category", 12.0), 100), 100);*/


        //runInteractiveMode(eCom);
    }

    private static void runReflectionMode(ECom eCom) throws Exception {
        Class<?> aClass = ECom.class;

        System.out.println("Fields:\n");
        Arrays.stream(aClass.getDeclaredFields())
                .forEach(field -> {
                    System.out.println("Field Name: " + field.getName());
                    System.out.println("Modifiers: " + Modifier.toString(field.getModifiers()));
                    System.out.println("Type: " + field.getType());
                    System.out.println();
                });

        System.out.println("Constructors:\n");
        Arrays.stream(aClass.getDeclaredConstructors())
                .forEach(constructor -> {
                    System.out.println("Constructor: " + constructor.getName());
                    System.out.println("Modifiers: " + Modifier.toString(constructor.getModifiers()));
                    System.out.print("Parameters: ");
                    Arrays.stream(constructor.getParameterTypes())
                            .forEach(param -> System.out.print(param.getName() + " "));
                    System.out.println("\n");
                });

        System.out.println("Methods:\n");
        Arrays.stream(aClass.getDeclaredMethods())
                .forEach(method -> {
                    System.out.println("Method Name: " + method.getName());
                    System.out.println("Return Type: " + method.getReturnType());
                    System.out.println("Modifiers: " + Modifier.toString(method.getModifiers()));
                    System.out.print("Parameters: ");
                    Arrays.stream(method.getParameterTypes())
                            .forEach(param -> System.out.print(param.getName() + " "));
                    System.out.println("\n");
                });

        invokeReflectionExamples(aClass);
    }



    private static void invokeReflectionExamples(Class<?> aClass) throws Exception {
        Constructor<?> defaultConstructor = aClass.getDeclaredConstructor();
        defaultConstructor.setAccessible(true);
        ECom eComInstance = (ECom) defaultConstructor.newInstance();

        Method printAllCategoriesMethod = aClass.getDeclaredMethod("printAllCategories");
        printAllCategoriesMethod.setAccessible(true);
        printAllCategoriesMethod.invoke(eComInstance);

        Method printAllProductsMethod = aClass.getDeclaredMethod("printAllProducts", Consumer.class);
        printAllProductsMethod.setAccessible(true);
        printAllProductsMethod.invoke(eComInstance, (Consumer<Product>) product ->
                System.out.println("Product: " + product.getTitle() + ", Stock: " + eComInstance.getProducts().get(product)));
    }

    private static void runInteractiveMode(ECom eCom) {
        Scanner scanner = new Scanner(System.in);
        displayWelcomeMessage();

        while (isRunning) {
            System.out.print("Enter command: ");
            String command = scanner.nextLine().trim();

            if (command.isEmpty()) {
                System.out.println("You must enter a command");
            } else {
                handleCommand(command, eCom);
            }
        }
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
        Supplier<Product> productSupplier = () -> UserUtils.createProduct(eCom.getCategories());

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter stock quantity for the product: ");
        int stockQuantity;
        try {
            stockQuantity = Integer.parseInt(scanner.nextLine().trim());
            if (stockQuantity < 0) {
                throw new NumberFormatException("Stock quantity cannot be negative");
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Stock quantity must be a positive integer");
            return;
        }

        try {
            eCom.addProduct(productSupplier, stockQuantity);
            System.out.println("Product added.");
        } catch (DuplicateProductException | ProductOutOfStockException e) {
            System.err.println("Error adding product: " + e.getMessage());
        }
    }


    private static void displayWelcomeMessage() {
        System.out.println("========================== SolvdApp ==========================");
        System.out.println("This program allows you to manage orders, products, and invoices");
        displayHelpMessage();
    }

    private static void displayHelpMessage() {
        System.out.println("Commands:");
        System.out.println("-cc : --create-category : Creates a new category");
        System.out.println("-cp : --create-product : Creates a new product");
        System.out.println("-pc : --print-categories : Print all categories");
        System.out.println("-pp : --print-products : Print all products");
        System.out.println("-h : --help : Show this help message");
        System.out.println("-q : --quit : Quits the program");
    }
}
