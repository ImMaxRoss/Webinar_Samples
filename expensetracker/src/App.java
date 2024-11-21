import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import config.DatabaseConnection;
import dao.ExpenseDaoImpl;
import models.Expense;

public class App {
    public static void main(String[] args) throws Exception {
        
        ExpenseDaoImpl expenseDao = new ExpenseDaoImpl();

        Connection connection = null;

        Scanner scanner = new Scanner(System.in);

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);
            expenseDao.setConnection(connection);

            boolean running = true;
            while (running) {

                System.out.println("Choose an option:");
                System.out.println("1. get all expenses");
                System.out.println("2. add expense");
                System.out.println("3. exit");

                int option = scanner.nextInt();


                switch (option) {
                    case 1:

                    List<Expense> expenses = expenseDao.getExpenses();
                    expenses.forEach(System.out::println);
                    break;

                    case 2:

                    System.out.println("Enter expense amount: ");
                    BigDecimal amount = scanner.nextBigDecimal();
                    scanner.nextLine();

                    System.out.println("Enter Category: ");
                    String category = scanner.nextLine();

                    System.out.println(" Enter description: ");
                    String description = scanner.nextLine();

                    Expense newExpense = new Expense(0, amount, category, description, LocalDateTime.now());
                    boolean isCreated = expenseDao.addExpense(newExpense);
                    System.out.println("Created: " + isCreated);

                    break;

                    case 3:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option");

                }

            }

            connection.commit();
            
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            scanner.close();
        }

        
    }
}
