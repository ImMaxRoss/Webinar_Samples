package dao;

import models.Expense;
import config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class ExpenseDaoImpl implements ExpenseDao {

    private Connection connection;

    public ExpenseDaoImpl() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addExpense(Expense expense) {
        // SQL query to insert a new expense into the database
        String sql = "INSERT INTO expenses (amount, category, description, date) VALUES (?, ?, ?, ?)";
        // The RETURN_GENERATED_KEYS flag is used to retrieve 
        // any generated keys (like an auto-incremented ID).
        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            // Set the parameters for the prepared statement
            pstmt.setBigDecimal(1, expense.getAmount());
            pstmt.setString(2, expense.getCategory());
            pstmt.setString(3, expense.getDescription());
            pstmt.setObject(4, expense.getDate());
            // Execute the update and get the number of affected rows
            int rowsAffected = pstmt.executeUpdate();
    
            // If rows were affected, retrieve the generated keys
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    // If a generated key exists, set it to the expense's ID
                    if (generatedKeys.next()) {
                        expense.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Expense getExpense(int id) {
        String sql = "SELECT * FROM expenses WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Expense(
                        rs.getInt("id"),
                        rs.getBigDecimal("amount"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getObject("date", LocalDateTime.class)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Expense> getExpenses() {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expenses";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                expenses.add(new Expense(
                        rs.getInt("id"),
                        rs.getBigDecimal("amount"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getObject("date", LocalDateTime.class)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expenses;
    }

    @Override
    public boolean updateExpense(Expense expense) {
        String sql = "UPDATE expenses SET amount = ?, category = ?, description = ?, date = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setBigDecimal(1, expense.getAmount());
            pstmt.setString(2, expense.getCategory());
            pstmt.setString(3, expense.getDescription());
            pstmt.setObject(4, expense.getDate());
            pstmt.setInt(5, expense.getId());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteExpense(int id) {
        String sql = "DELETE FROM expenses WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}