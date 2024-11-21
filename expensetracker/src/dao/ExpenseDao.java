package dao;

import java.sql.Connection;
import java.util.List;

import models.Expense;

public interface ExpenseDao {

    boolean addExpense(Expense expense);

    Expense getExpense(int id);

    List<Expense> getExpenses();

    boolean updateExpense(Expense expense);

    boolean deleteExpense(int id);

    void setConnection(Connection connection);
}
