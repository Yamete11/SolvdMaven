package com.example.utils;

import com.example.product.Category;
import com.example.product.Product;
import com.example.user.*;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class UserUtils {

    public static boolean validateUser(User user) {
        boolean isEmailValid = user.getEmail().contains("@") && user.getEmail().contains(".");
        boolean isPasswordValid = user.getPassword().length() >= 6;
        return isEmailValid && isPasswordValid;
    }

    public static Product createProduct(Set<Category> categories) {
        Scanner scanner = new Scanner(System.in);

        String title;
        while (true) {
            System.out.print("Enter product title (up to 30 characters, no special symbols): ");
            title = scanner.nextLine();

            if (StringUtils.isBlank(title)) {
                System.out.println("Title cannot be empty or only whitespace");
            } else if (StringUtils.length(title) > 30) {
                System.out.println("Title is too long. It should be up to 30 characters");
            } else if (!StringUtils.isAlphanumericSpace(title)) {
                System.out.println("Title contains invalid characters. Please use only letters, numbers, and spaces");
            } else {
                title = StringUtils.trim(title);
                title = StringUtils.capitalize(title);
                break;
            }
        }

        double price;
        while (true) {
            System.out.print("Enter product price (must be greater than 0 and less than 10,000): ");
            try {
                price = Double.parseDouble(scanner.nextLine());

                if (price <= 0) {
                    System.out.println("Price must be greater than zero. Please enter a valid price");
                } else if (price > 10000) {
                    System.out.println("Price seems too high. Please enter a reasonable price (between 0 and 10,000)");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid price (e.g., 12.50)");
            }
        }

        int stockQuantity;
        while (true) {
            System.out.print("Enter stock quantity (must be between 0 and 10,000): ");
            try {
                stockQuantity = Integer.parseInt(scanner.nextLine());

                if (stockQuantity < 0) {
                    System.out.println("Stock quantity cannot be negative. Please enter a valid quantity (0 or higher)");
                } else if (stockQuantity > 10000) {
                    System.out.println("Stock quantity seems too high. Please enter a reasonable quantity (between 0 and 10,000)");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid stock quantity (e.g. 50)");
            }
        }

        System.out.println("Choose a category:");
        List<Category> categoryList = new ArrayList<>(categories);
        categoryList.stream()
                .forEach(category -> System.out.println(categoryList.indexOf(category) + ": " + category));


        Category category = null;
        while (category == null) {
            System.out.print("Enter category index (between 0 and " + (categoryList.size() - 1) + "): ");
            try {
                int categoryIndex = Integer.parseInt(scanner.nextLine());
                if (categoryIndex >= 0 && categoryIndex < categoryList.size()) {
                    category = categoryList.get(categoryIndex);
                } else {
                    System.out.println("Invalid index. Please enter a number between 0 and " + (categoryList.size() - 1));
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 0 and " + (categoryList.size() - 1));
            }
        }

        Product product = new Product(title, price, stockQuantity, category);
        System.out.println("Product created: " + product);
        return product;
    }

    public static Category createCategory() {
        Scanner scanner = new Scanner(System.in);

        String categoryName;
        while (true) {
            System.out.print("Enter category name (up to 30 characters, no special symbols): ");
            categoryName = scanner.nextLine();

            if (StringUtils.length(categoryName) > 30) {
                System.out.println("Category name is too long. It should be up to 30 characters");
            } else if (!StringUtils.isAlphanumericSpace(categoryName)) {
                System.out.println("Category name contains invalid characters. Please use only letters, numbers, and spaces");
            } else {
                categoryName = StringUtils.trim(categoryName);
                categoryName = StringUtils.capitalize(categoryName);
                break;
            }
        }

        double vat;
        while (true) {
            System.out.print("Enter VAT percentage (between 0 and 100): ");
            try {
                vat = Double.parseDouble(scanner.nextLine());
                if (vat < 0) {
                    System.out.println("VAT percentage cannot be negative. Please enter a valid percentage (0 or higher)");
                } else if (vat > 100) {
                    System.out.println("VAT percentage cannot exceed 100. Please enter a percentage between 0 and 100");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number for VAT (e.g. 18.0)");
            }
        }

        Category category = new Category(categoryName, vat);
        System.out.println("Category created: " + category);
        return category;
    }
}
