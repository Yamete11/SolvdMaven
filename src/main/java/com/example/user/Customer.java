package com.example.user;

import com.example.exception.InsufficientFundsException;

import java.util.Objects;
import java.util.Scanner;

public class Customer extends User implements Payable{
    private Address address;
    private PaymentMethod paymentMethod;
    private CardDetails cardDetails;
    private double accountBalance;

    public Customer(String email, String password, String login, Address address, double accountBalance) {
        super(email, password, login);
        this.address = address;
        this.paymentMethod = PaymentMethod.CASH;
        this.cardDetails = null;
        this.accountBalance = accountBalance;
    }

    public Customer(String email, String password, String login, Address address, CardDetails cardDetails, double accountBalance) {
        super(email, password, login);
        this.address = address;
        this.paymentMethod = PaymentMethod.CARD;
        this.cardDetails = cardDetails;
        this.accountBalance = accountBalance;
    }

    @Override
    public String getAccountType() {
        return "Customer Account";
    }

    @Override
    public void resetPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your email address: ");
        String inputEmail = scanner.nextLine();

        if (inputEmail.equals(getEmail())) {
            System.out.print("Enter your new password: ");
            String newPassword = scanner.nextLine();
            this.setPassword(newPassword);
            System.out.println("Password has been successfully reset.");
        } else {
            System.out.println("Email address is incorrect. Cannot reset password.");
        }
    }

    @Override
    public boolean processPayment(double amount) {
        if (accountBalance >= amount) {
            accountBalance -= amount;
            System.out.println("Payment of $" + amount + " processed for customer");
            return true;
        } else {
            throw new InsufficientFundsException("Insufficient balance for payment");
        }
    }

    public void changeLogin(String newLogin) {
        updateLogin(newLogin);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public CardDetails getCardDetails() {
        return cardDetails;
    }

    public void setCardDetails(CardDetails cardDetails) {
        this.cardDetails = cardDetails;
    }

    @Override
    public String toString() {
        StringBuilder customerInfo = new StringBuilder(super.toString())
                .append("\nCustomer Details:\n")
                .append("  Address: ").append(address).append("\n")
                .append("  Payment Method: ").append(paymentMethod.getDescription());

        if (paymentMethod == PaymentMethod.CARD && cardDetails != null) {
            customerInfo.append("\n  Card Details: ").append(cardDetails);
        }

        return customerInfo.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), address, paymentMethod);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        if (!super.equals(o)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(address, customer.address) &&
                Objects.equals(paymentMethod, customer.paymentMethod);
    }

    @Override
    public boolean refundPayment(double amount) {
        accountBalance += amount;
        System.out.println("Refund of $" + amount + " processed for customer");
        return true;
    }

    @Override
    public String generateReceipt() {
        return "Receipt: Payment processed. Remaining balance: $" + accountBalance;
    }
}
