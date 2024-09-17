package com.example.utils;

import com.example.product.Category;
import com.example.product.Product;
import com.example.user.*;

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

    /*public static Customer createNewCustomer() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter customer email: ");
        String email = scanner.nextLine();
        System.out.print("Enter customer password: ");
        String password = scanner.nextLine();
        System.out.print("Enter customer login: ");
        String login = scanner.nextLine();

        Address address = null;
        while (address == null) {
            System.out.print("Enter customer address (street, city, postal code, country): ");
            String addressInput = scanner.nextLine();
            String[] addressParts = addressInput.split(", ");
            if (addressParts.length == 4) {
                address = new Address(addressParts[0], addressParts[1], addressParts[2], addressParts[3]);
            } else {
                System.out.println("Invalid address format. Please enter in the format: street, city, postal code, country");
            }
        }

        System.out.print("Would you like to add card details? (yes/no): ");
        String addCardDetails = scanner.nextLine();

        CardDetails cardDetails = null;
        if (addCardDetails.equalsIgnoreCase("yes")) {
            System.out.print("Enter card number: ");
            String cardNumber = scanner.nextLine();
            System.out.print("Enter cardholder name: ");
            String cardholderName = scanner.nextLine();
            System.out.print("Enter expiration date (YYYY-MM-DD): ");
            LocalDate expirationDate = LocalDate.parse(scanner.nextLine());
            System.out.print("Enter CVV: ");
            String cvv = scanner.nextLine();

            cardDetails = new CardDetails(cardNumber, cardholderName, expirationDate, cvv);
        }

        double accountBalance = 0.0;
        while (true) {
            System.out.print("Enter initial account balance (or leave blank for 0.0): ");
            String balanceInput = scanner.nextLine();
            if (balanceInput.isEmpty()) {
                break;
            }
            try {
                accountBalance = Double.parseDouble(balanceInput);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number");
            }
        }

        Customer newCustomer;
        if (cardDetails != null) {
            newCustomer = new Customer(email, password, login, address, cardDetails, accountBalance);
        } else {
            newCustomer = new Customer(email, password, login, address, accountBalance);
        }

        System.out.println("Customer created: " + newCustomer);
        return newCustomer;
    }*/

    public static Admin createNewAdmin() {
        Scanner scanner = new Scanner(System.in);

        String email;
        while (true) {
            System.out.print("Enter admin email: ");
            email = scanner.nextLine();
            if (isValidEmail(email)) {
                break;
            } else {
                System.out.println("Invalid email format. Please enter a valid email");
            }
        }

        String password;
        while (true) {
            System.out.print("Enter admin password (minimum 6 characters): ");
            password = scanner.nextLine();
            if (password.length() >= 6) {
                break;
            } else {
                System.out.println("Password is too short. Please enter a password with at least 6 characters");
            }
        }

        String login;
        while (true) {
            System.out.print("Enter admin login (no spaces): ");
            login = scanner.nextLine();
            if (!login.trim().isEmpty() && !login.contains(" ")) {
                break;
            } else {
                System.out.println("Login is invalid. It must not contain spaces and should not be empty");
            }
        }

        Role role = null;
        while (role == null) {
            System.out.println("Select admin role:");
            for (int i = 0; i < Role.values().length; i++) {
                System.out.println((i + 1) + ". " + Role.values()[i]);
            }
            System.out.print("Enter the corresponding number: ");
            String input = scanner.nextLine();
            try {
                int roleIndex = Integer.parseInt(input) - 1;
                role = Role.values()[roleIndex];
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid input. Please select a valid role number.");
            }
        }

        String secretWord;
        while (true) {
            System.out.print("Enter admin secret word: ");
            secretWord = scanner.nextLine();
            if (!secretWord.trim().isEmpty()) {
                break;
            } else {
                System.out.println("Secret word cannot be empty. Please enter a valid secret word");
            }
        }

        Admin newAdmin = new Admin(email, password, login, role, secretWord);
        System.out.println("Admin data: " + newAdmin);
        return newAdmin;
    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public static Product createProduct(Set<Category> categories) {
        Scanner scanner = new Scanner(System.in);

        String title;
        while (true) {
            System.out.print("Enter product title (up to 30 characters, no special symbols): ");
            title = scanner.nextLine();

            if (title.length() > 30) {
                System.out.println("Title is too long. It should be up to 30 characters");
            } else if (!title.matches("[a-zA-Z0-9 ]+")) {
                System.out.println("Title contains invalid characters. Please use only letters, numbers, and spaces");
            } else {
                break;
            }
        }

        double price;
        while (true) {
            System.out.print("Enter product price (must be greater than 0 and less than 10 000): ");
            try {
                price = Double.parseDouble(scanner.nextLine());

                if (price <= 0) {
                    System.out.println("Price must be greater than zero. Please enter a valid price");
                } else if (price > 10000) {
                    System.out.println("Price seems too high. Please enter a reasonable price (between 0 and 10 000)");
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
                    System.out.println("Stock quantity seems too high. Please enter a reasonable quantity (between 0 and 10 000)");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid stock quantity (e.g. 50)");
            }
        }

        System.out.println("Choose a category:");
        List<Category> categoryList = new ArrayList<>(categories);
        for (int i = 0; i < categoryList.size(); i++) {
            System.out.println("\n" + i + ": " + categoryList.get(i) + "\n");
        }

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

            if (categoryName.length() > 30) {
                System.out.println("Category name is too long. It should be up to 30 characters");
            } else if (!categoryName.matches("[a-zA-Z0-9 ]+")) {
                System.out.println("Category name contains invalid characters. Please use only letters, numbers, and spaces");
            } else {
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
