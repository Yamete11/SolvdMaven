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

import java.lang.reflect.*;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class Main {
    private static boolean isRunning = true;

    public static void main(String[] args) throws Exception {
        ECom eCom = new ECom();

        eCom.addProduct(new Product("title"));

        Scanner scanner = new Scanner(System.in);
        System.out.println("Select mode:");
        System.out.println("1: Reflection");
        System.out.println("2: Interaction through flags");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                runReflectionMode(eCom);
                break;
            case 2:
                runInteractiveMode(eCom);
                break;
            default:
                System.out.println("Invalid choice. Bye");
                break;
        }
    }

    private static void runReflectionMode(ECom eCom) throws Exception {
        Class<?> aClass = ECom.class;

        System.out.println("Fields:\n");
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("Field Name: " + field.getName());
            System.out.println("Modifiers: " + Modifier.toString(field.getModifiers()));
            System.out.println("Type: " + field.getType());
            System.out.println();
        }

        System.out.println("Constructors:\n");
        Constructor<?>[] constructors = aClass.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println("Constructor: " + constructor.getName());
            System.out.println("Modifiers: " + Modifier.toString(constructor.getModifiers()));
            System.out.print("Parameters: ");
            Class<?>[] paramTypes = constructor.getParameterTypes();
            for (Class<?> param : paramTypes) {
                System.out.print(param.getName() + " ");
            }
            System.out.println("\n");
        }

        System.out.println("Methods:\n");
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("Method Name: " + method.getName());
            System.out.println("Return Type: " + method.getReturnType());
            System.out.println("Modifiers: " + Modifier.toString(method.getModifiers()));
            System.out.print("Parameters: ");
            Class<?>[] paramTypes = method.getParameterTypes();
            for (Class<?> param : paramTypes) {
                System.out.print(param.getName() + " ");
            }
            System.out.println("\n");
        }

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
                System.out.println("You must enter a command.");
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
