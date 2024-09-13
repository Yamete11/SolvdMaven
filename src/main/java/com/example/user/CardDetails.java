package com.example.user;

import java.time.LocalDate;
import java.util.Objects;

public class CardDetails {
    private String cardNumber;
    private String cardholderName;
    private LocalDate expirationDate;
    private String cvv;

    public CardDetails(String cardNumber, String cardholderName, LocalDate expirationDate, String cvv) {
        this.cardNumber = cardNumber;
        this.cardholderName = cardholderName;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    @Override
    public String toString() {
        return "Cardholder: " + cardholderName +
                ", Card Number: **** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, cardholderName, expirationDate, cvv);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardDetails that = (CardDetails) o;
        return cardNumber.equals(that.cardNumber) &&
                cardholderName.equals(that.cardholderName) &&
                expirationDate.equals(that.expirationDate) &&
                cvv.equals(that.cvv);
    }
}
