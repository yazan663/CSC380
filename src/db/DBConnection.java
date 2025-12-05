package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection;

    public static Connection getConnection() throws SQLException {

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                String url = "jdbc:mysql://localhost:3306/myphase2"; 
                String user = "root";
                String password = "";

                connection = DriverManager.getConnection(url, user, password);

                System.out.println("Connected to MySQL");
            } 
            catch (ClassNotFoundException e) {
                throw new SQLException("Driver not found", e);
            }
        }

        return connection;
    }
}
