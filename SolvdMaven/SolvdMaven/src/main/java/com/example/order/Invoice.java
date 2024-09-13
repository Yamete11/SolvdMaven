package com.example.order;

import com.example.user.Address;

import java.time.LocalDate;
import java.util.Objects;

public final class Invoice {
    private String invoiceNumber;
    private Order order;
    private Address billingAddress;
    private LocalDate issueDate;
    private double totalAmount;

    public Invoice(Order order) {
        this.invoiceNumber = generateInvoiceNumber();
        this.order = order;
        this.billingAddress = order.getCustomer().getAddress();
        this.issueDate = LocalDate.now();
        this.totalAmount = calculateTotalAmount();
    }

    private String generateInvoiceNumber() {
        return "INV-" + System.currentTimeMillis();
    }

    private double calculateTotalAmount() {
        return order.getTotalAmount();
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public Order getOrder() {
        return order;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "Invoice Number: " + invoiceNumber + '\n' +
                "Issue Date: " + issueDate + '\n' +
                "Billing Address: " + billingAddress + '\n' +
                "Order Details: \n" + order.toString() + '\n' +
                "Total Amount Due: $" + totalAmount + '\n';
    }

    public void printInvoice() {
        System.out.println(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceNumber, order, billingAddress, issueDate, totalAmount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Double.compare(invoice.totalAmount, totalAmount) == 0 &&
                invoiceNumber.equals(invoice.invoiceNumber) &&
                order.equals(invoice.order) &&
                billingAddress.equals(invoice.billingAddress) &&
                issueDate.equals(invoice.issueDate);
    }
}
