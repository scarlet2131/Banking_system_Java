package com.example.bankingsystem.model;

import java.sql.Timestamp;

public class Transaction {
    private int transactionId;
    private int fromAccount;
    private int toAccount;
    private double amount;
    private String transactionType;
    private Timestamp transactionTime;

    public Transaction(int transactionId, int fromAccount, int toAccount, double amount, String transactionType, Timestamp transactionTime) {
        this.transactionId = transactionId;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionTime = transactionTime;
    }

    // Getters and setters
}
