package com.example.user;

import java.util.Objects;
import java.util.Scanner;

public class Admin extends User {
    private String role;
    private String secretWord;
    private static final double DISCOUNT_RATE = 0.50;

    public Admin(String email, String password, String login, String role, String secretWord) {
        super(email, password, login);
        this.role = role;
        this.secretWord = secretWord;
    }

    @Override
    public String getAccountType() {
        return "Admin Account";
    }

    public void changeLogin(String newLogin) {
        updateLogin(newLogin);
    }

    @Override
    public void resetPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your secret word to reset the password: ");
        String inputSecretWord = scanner.nextLine();

        if (inputSecretWord.equals(secretWord)) {
            System.out.print("Enter your new password: ");
            String newPassword = scanner.nextLine();
            setPassword(newPassword);
            System.out.println("Password has been successfully reset.");
        } else {
            System.out.println("Secret word is incorrect. Cannot reset password.");
        }
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                "Admin Details:\n" +
                "  Role: " + role + "\n" +
                "  Discount Rate: " + DISCOUNT_RATE * 100 + "%";
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), role, secretWord);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Admin)) return false;
        if (!super.equals(o)) return false;
        Admin admin = (Admin) o;
        return role.equals(admin.role) && secretWord.equals(admin.secretWord);
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord;
    }

    public String getRole() {
        return role;
    }

    public String getSecretWord() {
        return secretWord;
    }
}
