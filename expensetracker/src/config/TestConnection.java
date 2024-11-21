package config;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {

    public static void main(String[] args) {
        
        try {
            Connection connection = DatabaseConnection.getConnection();
            if (connection != null) {
                System.out.println("Connection established!");
                connection.close();
            } else {
                System.out.println("Connection failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
