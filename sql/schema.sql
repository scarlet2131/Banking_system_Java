CREATE DATABASE IF NOT EXISTS BankingSystem;

USE BankingSystem;

CREATE TABLE IF NOT EXISTS Clients (
                                       client_id INT AUTO_INCREMENT PRIMARY KEY,
                                       name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS Accounts (
                                        account_id INT AUTO_INCREMENT PRIMARY KEY,
                                        client_id INT NOT NULL,
                                        account_type VARCHAR(50) NOT NULL,
    balance DECIMAL(15, 2) NOT NULL,
    FOREIGN KEY (client_id) REFERENCES Clients(client_id)
    );

CREATE TABLE IF NOT EXISTS Transactions (
                                            transaction_id INT AUTO_INCREMENT PRIMARY KEY,
                                            from_account INT,
                                            to_account INT,
                                            amount DECIMAL(15, 2) NOT NULL,
    transaction_type VARCHAR(50) NOT NULL,
    transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (from_account) REFERENCES Accounts(account_id),
    FOREIGN KEY (to_account) REFERENCES Accounts(account_id)
    );

INSERT INTO Clients (name, email) VALUES ('John Doe', 'john@example.com'), ('Jane Smith', 'jane@example.com');

INSERT INTO Accounts (client_id, account_type, balance)
VALUES (1, 'Savings', 1000.00), (2, 'Checking', 500.00);

INSERT INTO Transactions (from_account, to_account, amount, transaction_type)
VALUES (1, 2, 100.00, 'Transfer');
