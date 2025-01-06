package cinema;

import java.sql.*;

public class MySQLConnection {
    public static void createMySQLConnection() {
        String host = "jdbc:mysql://127.0.0.1:3306/cinema_schema";
        String username = "root";
        String password = "password"; // enter password
        try {
            Connection connection = DriverManager.getConnection(host, username, password);
            System.out.println("Successfully connected to mySQL database");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from movies");
            while (resultSet.next()) {
                System.out.println("Title: " + resultSet.getString("title"));
            }
        }
        catch(SQLException e) {
            System.out.println("Failed to connect to mySQL database.");
            e.printStackTrace();
        }
    }
}
