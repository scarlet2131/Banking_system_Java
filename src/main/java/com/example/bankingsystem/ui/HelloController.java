package com.example.bankingsystem.ui;

import com.example.bankingsystem.model.Transaction;
import com.example.bankingsystem.service.BankingService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class HelloController {

    private BankingService bankingService = new BankingService();
    private int accountId = 1;

    @FXML
    private Label balanceLabel;

    @FXML
    private TableView<Transaction> transactionsTable;

    @FXML private TableColumn<Transaction, String> transactionColumn;
    @FXML private TableColumn<Transaction, Double> amountColumn;
    @FXML private TableColumn<Transaction, Timestamp> dateColumn;



    @FXML
    public void initialize() {
        try {
            // Initialize the TableView with the transaction columns
            transactionColumn.setCellValueFactory(cellData -> cellData.getValue().transactionTypeProperty());
            amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
            dateColumn.setCellValueFactory(cellData -> cellData.getValue().transactionTimeProperty());

            updateBalanceLabel();
            updateTransactionTable();
        } catch (SQLException e) {
            showAlert("Error initializing data: " + e.getMessage());
        }
    }

    // Fetch and update balance
    private void updateBalanceLabel() throws SQLException {
        double balance = bankingService.getBalance(accountId);
        balanceLabel.setText("Account Balance: $" + balance);
    }

    // Fetch and update transaction table
    private void updateTransactionTable() throws SQLException {
        List<com.example.bankingsystem.model.Transaction> transactions = bankingService.getTransactionHistory(accountId);
        transactionsTable.getItems().clear();
        transactionsTable.getItems().addAll(transactions);
    }

    // Method to handle Deposit Button click
    @FXML
    public void onDepositButtonClick() {
        showAmountPopup("Deposit", "Enter amount to deposit:", (amount) -> {
            try {
                bankingService.depositMoney(accountId, amount);
                updateBalanceLabel();
                updateTransactionTable();
            } catch (SQLException e) {
                showAlert("Error during deposit: " + e.getMessage());
            }
        });
    }

    // Method to handle Withdraw Button click
    @FXML
    public void onWithdrawButtonClick() {
        showAmountPopup("Withdraw", "Enter amount to withdraw:", (amount) -> {
            try {
                bankingService.withdrawMoney(accountId, amount);
                updateBalanceLabel();
                updateTransactionTable();
            } catch (SQLException e) {
                showAlert("Error during withdrawal: " + e.getMessage());
            }
        });
    }

    // Method to handle Transfer Button click
    @FXML
    public void onTransferButtonClick() {
        // Ask for account ID and amount to transfer
        TextInputDialog accountDialog = new TextInputDialog();
        accountDialog.setTitle("Transfer");
        accountDialog.setHeaderText("Enter account ID to transfer to:");
        accountDialog.setContentText("Account ID:");

        accountDialog.showAndWait().ifPresent(accountIdStr -> {
            int toAccount = Integer.parseInt(accountIdStr);
            if (toAccount != 0) {
                showAmountPopup("Transfer", "Enter amount to transfer:", (amount) -> {
                    try {
                        bankingService.transferMoney(accountId, toAccount, amount);
                        updateBalanceLabel();
                        updateTransactionTable();
                    } catch (SQLException e) {
                        showAlert("Error during transfer: " + e.getMessage());
                    }
                });
            }
        });
    }

    // Method to show a popup for entering amount (for Deposit, Withdraw, Transfer)
    private void showAmountPopup(String title, String message, AmountCallback callback) {
        // Create the input dialog
        TextInputDialog amountDialog = new TextInputDialog();
        amountDialog.setTitle(title);
        amountDialog.setHeaderText(message);
        amountDialog.setContentText("Amount:");

        // Handle when the dialog is closed
        amountDialog.showAndWait().ifPresent(amountStr -> {
            try {
                double amount = Double.parseDouble(amountStr);
                if (amount > 0) {
                    callback.onAmountEntered(amount);
                } else {
                    showAlert("Please enter a valid positive amount.");
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid amount entered.");
            }
        });
    }

    // Show a simple alert
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Functional interface to handle amount input
    @FunctionalInterface
    interface AmountCallback {
        void onAmountEntered(double amount);
    }
}
