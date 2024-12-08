package com.example.bankingsystem.service;

import com.example.bankingsystem.db.DatabaseConnection;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class BankingService {

    public void depositMoney(int accountId, double amount) throws SQLException {
        String query = "UPDATE Accounts SET balance = balance + ? WHERE account_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountId);
            stmt.executeUpdate();
        }
    }

    public void withdrawMoney(int accountId, double amount) throws SQLException {
        String query = "UPDATE Accounts SET balance = balance - ? WHERE account_id = ? AND balance >= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountId);
            stmt.setDouble(3, amount);
            stmt.executeUpdate();
        }
    }

    public void transferMoney(int fromAccount, int toAccount, double amount) throws SQLException {
        withdrawMoney(fromAccount, amount);
        depositMoney(toAccount, amount);
    }

    public void payUtilityBill(int accountId, double amount) throws SQLException {
        withdrawMoney(accountId, amount);
    }
}
