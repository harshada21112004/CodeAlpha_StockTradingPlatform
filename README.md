

A Java Swing application that simulates a basic stock trading environment. Users can log in, buy and sell stocks, manage their portfolio, and track transaction history. The application uses JDBC for database connectivity and MySQL for data storage.

## Features

- User Login Authentication
- Display Available Stocks
- Buy Stocks
- Sell Stocks
- Portfolio Management
- Transaction History Tracking
- Account Balance Management
- Refresh Market Data
- GUI-Based Interface
- JDBC Database Connectivity
- Object-Oriented Programming (OOP)

## Technologies Used

- Java
- Java Swing
- JDBC
- MySQL
- OOP Concepts

## Database Tables

- users
- stocks
- portfolio
- transactions

## JDBC Database Connectivity

The application uses JDBC to connect Java with MySQL and perform:

- User Validation
- Stock Data Retrieval
- Portfolio Updates
- Transaction Management
- Balance Updates

## Initial Setup

1. Execute `stockdb.sql` in MySQL.
2. Insert at least one user record into the `users` table.
3. Configure database credentials in `DBConnection.java`.
4. Add MySQL Connector/J library.
5. Run `Main.java`.

## How to Use

1. Enter a valid username and login.
2. View available stocks in the Market Stocks section.
3. Enter a stock symbol and quantity.
4. Click Buy to purchase shares.
5. Click Sell to sell owned shares.
6. View portfolio holdings and transaction history.

## Screenshots

See the `screenshots` folder for application images.
