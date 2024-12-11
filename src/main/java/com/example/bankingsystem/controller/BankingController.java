package com.example.bankingsystem.controller;

import com.example.bankingsystem.model.Transaction;
import com.example.bankingsystem.service.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/banking")
public class BankingController {

    @Autowired
    private BankingService bankingService;

    // Deposit Money
    @PostMapping("/deposit")
    public String deposit(@RequestParam int accountId, @RequestParam double amount) throws SQLException {
        bankingService.depositMoney(accountId, amount);
        return "Deposit successful!";
    }

    // Withdraw Money
    @PostMapping("/withdraw")
    public String withdraw(@RequestParam int accountId, @RequestParam double amount) throws SQLException {
        bankingService.withdrawMoney(accountId, amount);
        return "Withdrawal successful!";
    }

    // Transfer Money
    @PostMapping("/transfer")
    public String transfer(@RequestParam int fromAccount, @RequestParam int toAccount, @RequestParam double amount) throws SQLException {
        bankingService.transferMoney(fromAccount, toAccount, amount);
        return "Transfer successful!";
    }

    // Pay Utility Bill
    @PostMapping("/pay-utility-bill")
    public String payUtilityBill(@RequestParam int accountId, @RequestParam double amount) throws SQLException {
        bankingService.payUtilityBill(accountId, amount);
        return "Utility bill paid successfully!";
    }

    // Get Account Balance
    @GetMapping("/balance")
    public double getBalance(@RequestParam int accountId) throws SQLException {
        return bankingService.getBalance(accountId);
    }

    // Get Transaction History
    @GetMapping("/transactions")
    public List<Transaction> getTransactionHistory(@RequestParam int accountId) throws SQLException {
        return bankingService.getTransactionHistory(accountId);
    }
}
