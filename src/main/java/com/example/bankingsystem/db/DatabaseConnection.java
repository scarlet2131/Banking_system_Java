//package com.example.bankingsystem.db;
//
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class DatabaseConnection {
//    private static final String URL = "jdbc:mysql://localhost:3306/BankingSystem";
//    private static final String USER = "root";
//    private static final String PASSWORD = "root";
//
//    public static Connection getConnection() {
////        try {
////            return DriverManager.getConnection(URL, USER, PASSWORD);
//        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
//            String schemaSQL = new String(Files.readAllBytes(Paths.get("../sql/schema.sql")));
//            Statement stmt = conn.createStatement();
//            stmt.execute(schemaSQL);
//            System.out.println("Database and tables created successfully.");
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
//        } catch (SQLException e) {
//            System.err.println("Error connecting to the database!");
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
package com.example.bankingsystem.db;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/BankingSystem?allowPublicKeyRetrieval=true&useSSL=true";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Establish the connection without specifying the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", USER, PASSWORD);
            System.out.println("Database connection established successfully.");

            // Create the database if it does not exist
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS BankingSystem;");
                System.out.println("Database 'BankingSystem' created or already exists.");
            }

            // Now connect to the actual database
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the BankingSystem database.");

            // Initialize the database schema
            initializeDatabase(conn);

        } catch (SQLException e) {
            System.err.println("Error connecting to the database!");
            e.printStackTrace();
        }
        return conn;
    }


    private static void initializeDatabase(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            // Check if the database is already initialized
            ResultSet rs = stmt.executeQuery("SHOW TABLES LIKE 'Clients';");
            if (rs.next()) {
                System.out.println("Database is already initialized. Skipping initialization.");
                return;
            }

            // Read schema file content
            String schemaSQL = new String(Files.readAllBytes(Paths.get("sql/schema.sql")));

            // Split SQL statements
            String[] statements = schemaSQL.split(";");

            // Execute each statement
            for (String statement : statements) {
                if (!statement.trim().isEmpty()) {
                    System.out.println("Executing: " + statement.trim());
                    stmt.execute(statement.trim());
                }
            }

            System.out.println("Database and tables initialized successfully.");
        } catch (SQLException e) {
            System.err.println("Error initializing the database!");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error reading schema file!");
            e.printStackTrace();
        }
    }
}
