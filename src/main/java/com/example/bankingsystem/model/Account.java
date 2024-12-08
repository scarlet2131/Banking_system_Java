package com.example.bankingsystem.model;

public class Account {
    private int accountId;
    private int clientId;
    private String accountType;
    private double balance;

    public Account(int accountId, int clientId, String accountType, double balance) {
        this.accountId = accountId;
        this.clientId = clientId;
        this.accountType = accountType;
        this.balance = balance;
    }

    // Getters and setters
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
