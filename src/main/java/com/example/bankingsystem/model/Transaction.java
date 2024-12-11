package com.example.bankingsystem.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;

public class Transaction {
    private int transactionId;
    private int fromAccount;
    private int toAccount;
    private DoubleProperty amount;
    private StringProperty transactionType;
    private ObjectProperty<Timestamp> transactionTime;

    public Transaction(int transactionId, int fromAccount, int toAccount, double amount, String transactionType, Timestamp transactionTime) {
        this.transactionId = transactionId;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = new SimpleDoubleProperty(amount);
        this.transactionType = new SimpleStringProperty(transactionType);
        this.transactionTime = new SimpleObjectProperty<>(transactionTime);
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getFromAccount() {
        return fromAccount;
    }

    public int getToAccount() {
        return toAccount;
    }

    public double getAmount() {
        return amount.get();
    }

    public String getTransactionType() {
        return transactionType.get();
    }

    public Timestamp getTransactionTime() {
        return transactionTime.get();
    }

    // Properties for JavaFX bindings
    public DoubleProperty amountProperty() {
        return amount;
    }

    public StringProperty transactionTypeProperty() {
        return transactionType;
    }

    public ObjectProperty<Timestamp> transactionTimeProperty() {
        return transactionTime;
    }

    // Optional: Setters for properties if needed for JavaFX bindings
    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public void setTransactionType(String transactionType) {
        this.transactionType.set(transactionType);
    }

    public void setTransactionTime(Timestamp transactionTime) {
        this.transactionTime.set(transactionTime);
    }
}
