package com.example.bankingsystem;

import com.example.bankingsystem.service.BankingService;

public class MainApp {
    public static void main(String[] args) {
        BankingService service = new BankingService();

        try {
            service.depositMoney(1, 1000);
            System.out.println("Deposit Successful!");

            service.withdrawMoney(1, 500);
            System.out.println("Withdrawal Successful!");

            service.transferMoney(1, 2, 200);
            System.out.println("Transfer Successful!");

            service.payUtilityBill(1, 150);
            System.out.println("Utility Bill Payment Successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
