CREATE DATABASE IF NOT EXISTS expensesDB;
USE expensesDB;

CREATE TABLE IF NOT EXISTS Expenses (
    id INT auto_increment primary KEY,
    amount DECIMAL(10, 2) NOT NULL,
    category VARCHAR(255) NOT NULL,
    description TEXT,
    date DATETIME NOT NULL
);