CREATE DATABASE stockdb;
USE stockdb;

--Users Table
CREATE TABLE users (
        user_id INT PRIMARY KEY AUTO_INCREMENT,
        username VARCHAR(50) NOT NULL,
        balance DOUBLE NOT NULL
);

--Stocks Table
CREATE TABLE stocks (
        stock_id INT PRIMARY KEY AUTO_INCREMENT,
        symbol VARCHAR(20) UNIQUE NOT NULL,
        company_name VARCHAR(100) NOT NULL,
        price DOUBLE NOT NULL
);

--Portfolio Table
CREATE TABLE portfolio (
        portfolio_id INT PRIMARY KEY AUTO_INCREMENT,
        user_id INT,
        stock_symbol VARCHAR(20),
        quantity INT,

        FOREIGN KEY (user_id)
        REFERENCES users(user_id)
);

--Transactions Table
CREATE TABLE transactions (
        transaction_id INT PRIMARY KEY AUTO_INCREMENT,
        user_id INT,
        stock_symbol VARCHAR(20),
        quantity INT,
        price DOUBLE,
        transaction_type VARCHAR(10),
        transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

        FOREIGN KEY (user_id)
        REFERENCES users(user_id)
);

-- Sample Stocks
INSERT INTO stocks(symbol, company_name, price) VALUES
('TCS','Tata Consultancy Services',4200),
('INFY','Infosys',1800),
('RELI','Reliance Industries',2800),
('HDFC','HDFC Bank',1700),
('WIPRO','Wipro',550);