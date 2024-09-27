package com.example.order;

import com.example.product.Product;
import com.example.user.Customer;
import com.example.utils.Discountable;
import com.example.utils.Taxable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order implements Taxable, Discountable {
    private LocalDate orderDate;
    private double totalAmount;
    private Customer customer;
    private List<OrderItem> orderItems;
    private OrderStatus orderStatus;
    private double discountPercentage;

    public Order(Customer customer) {
        this.orderDate = LocalDate.now();
        this.totalAmount = 0.0;
        this.customer = customer;
        this.orderItems = new ArrayList<>();
        this.orderStatus = OrderStatus.PROCESSING;
        this.discountPercentage = 0.0;
    }

    @Override
    public void applyDiscount(double percentage) {
        if (percentage < 0 || percentage > 100) {
            System.out.println("Invalid discount percentage. Must be between 0 and 100");
        } else {
            discountPercentage = percentage;
            double discountAmount = totalAmount * (discountPercentage / 100);
            totalAmount -= discountAmount;
            System.out.println("Applied " + percentage + "% discount. New total amount: $" + totalAmount);
        }
    }

    @Override
    public void removeDiscount() {
        double discountAmount = totalAmount * (discountPercentage / 100);
        totalAmount += discountAmount;
        discountPercentage = 0;
        System.out.println("Discount removed. Total amount reset to original: $" + totalAmount);
    }

    @Override
    public double calculateTax() {
        return orderItems.stream()
                .mapToDouble(item -> item.getProduct().calculateTax() * item.getQuantity())
                .sum();
    }

    @Override
    public double getAverageTaxRate() {
        return orderItems.stream()
                .mapToDouble(item -> item.getProduct().getAverageTaxRate())
                .average()
                .orElse(0.0);
    }

    public void addItem(Product product, int quantity) {
        OrderItem newItem = new OrderItem(product, quantity);
        orderItems.add(newItem);
        calculateTotalAmount();
    }

    public double calculateTotalAmount() {
        totalAmount = orderItems.stream()
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();
        return totalAmount;
    }

    public void updateOrderStatus(OrderStatus newStatus) {
        this.orderStatus = newStatus;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    @Override
    public String toString() {
        StringBuilder orderInfo = new StringBuilder("Order Date: " + orderDate + '\n' +
                "Customer: " + customer.getEmail() + '\n' +
                "Total Amount: $" + totalAmount + '\n' +
                "Order Status: " + orderStatus + '\n' +
                "Items: \n");

        orderItems.stream()
                .forEach(item -> orderInfo.append(item).append('\n'));

        return orderInfo.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderDate, totalAmount, customer, orderItems, orderStatus);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Double.compare(order.totalAmount, totalAmount) == 0 &&
                orderDate.equals(order.orderDate) &&
                customer.equals(order.customer) &&
                Objects.equals(orderItems, order.orderItems) &&
                orderStatus.equals(order.orderStatus);
    }
}
