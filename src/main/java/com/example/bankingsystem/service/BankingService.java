package com.example.bankingsystem.service;

import com.example.bankingsystem.db.DatabaseConnection;
//import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//@Service
public class BankingService {

    public void depositMoney(int accountId, double amount) throws SQLException {
        String query = "UPDATE Accounts SET balance = balance + ? WHERE account_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountId);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Account not found.");
            }
            recordTransaction(conn,null , "DEPOSIT", amount, accountId);
        }
    }

    public void withdrawMoney(int accountId, double amount) throws SQLException {
        String query = "UPDATE Accounts SET balance = balance - ? WHERE account_id = ? AND balance >= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountId);
            stmt.setDouble(3, amount);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Insufficient balance or account not found.");
            }
            recordTransaction(conn, accountId, "WITHDRAWAL", amount, null);
        }
    }

    public void transferMoney(int fromAccount, int toAccount, double amount) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction
            try {
                withdrawMoneyInternal(conn, fromAccount, amount); // Withdraw from sender
                depositMoneyInternal(conn, toAccount, amount);   // Deposit to recipient
                recordTransaction(conn, fromAccount, "TRANSFER_OUT", amount, toAccount);
                recordTransaction(conn, toAccount, "TRANSFER_IN", amount, fromAccount);
                conn.commit(); // Commit transaction if successful
            } catch (SQLException e) {
                conn.rollback(); // Rollback transaction on failure
                throw e;
            }
        }
    }

    public void payUtilityBill(int accountId, double amount) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            withdrawMoneyInternal(conn, accountId, amount);
            recordTransaction(conn, accountId, "UTILITY_BILL_PAYMENT", amount, null);
        }
    }

    public double getBalance(int accountId) throws SQLException {
        String query = "SELECT balance FROM Accounts WHERE account_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                } else {
                    throw new SQLException("Account not found.");
                }
            }
        }
    }

    public String getTransactionHistory(int accountId) throws SQLException {
        StringBuilder history = new StringBuilder();
        String query = "SELECT transaction_id, transaction_type, amount, transaction_time, to_account " +
                "FROM Transactions WHERE from_account = ? ORDER BY transaction_time DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    history.append("Transaction ID: ").append(rs.getInt("transaction_id"))
                            .append(", Type: ").append(rs.getString("transaction_type"))
                            .append(", Amount: ").append(rs.getDouble("amount"))
                            .append(", To Account: ").append(rs.getInt("to_account"))
                            .append(", Timestamp: ").append(rs.getTimestamp("transaction_time"))
                            .append("\n");
                }
            }
        }
        return history.toString();
    }

    // Internal methods for direct deposit and withdrawal during a transfer
    private void withdrawMoneyInternal(Connection conn, int accountId, double amount) throws SQLException {
        String query = "UPDATE Accounts SET balance = balance - ? WHERE account_id = ? AND balance >= ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountId);
            stmt.setDouble(3, amount);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Insufficient balance or account not found.");
            }
        }
    }

    private void depositMoneyInternal(Connection conn, int accountId, double amount) throws SQLException {
        String query = "UPDATE Accounts SET balance = balance + ? WHERE account_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountId);
            stmt.executeUpdate();
        }
    }

    // Record a transaction in the Transactions table
    private void recordTransaction(Connection conn,Integer fromAccount, String transactionType, double amount, Integer toAccount) throws SQLException {
        String query = "INSERT INTO Transactions (from_account, transaction_type, amount, to_account, transaction_time) " +
                "VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            if (fromAccount != null) {
                stmt.setInt(1, fromAccount);
            } else {
                stmt.setNull(1, java.sql.Types.INTEGER);
            }
//            stmt.setInt(1, fromAccount);
            stmt.setString(2, transactionType);
            stmt.setDouble(3, amount);
            if (toAccount != null) {
                stmt.setInt(4, toAccount);
            } else {
                stmt.setNull(4, java.sql.Types.INTEGER);
            }
            stmt.executeUpdate();
        }
    }
}
