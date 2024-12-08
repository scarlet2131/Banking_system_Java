package com.example.bankingsystem.controller;

import com.example.bankingsystem.service.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/banking")
public class BankingController {

    @Autowired
    private BankingService bankingService;

    @PostMapping("/deposit")
    public String deposit(@RequestParam int accountId, @RequestParam double amount) throws SQLException {
        bankingService.depositMoney(accountId, amount);
        return "Deposit successful!";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam int accountId, @RequestParam double amount) throws SQLException {
        bankingService.withdrawMoney(accountId, amount);
        return "Withdrawal successful!";
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam int fromAccount, @RequestParam int toAccount, @RequestParam double amount) throws SQLException {
        bankingService.transferMoney(fromAccount, toAccount, amount);
        return "Transfer successful!";
    }

    @PostMapping("/pay-bill")
    public String payBill(@RequestParam int fromAccount, @RequestParam double amount) throws SQLException {
        bankingService.payUtilityBill(fromAccount, amount);
        return "Utility bill paid successfully!";
    }
}
