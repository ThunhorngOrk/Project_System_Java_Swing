package Library_management_system.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // 1. DATABASE URL: format is jdbc:subprotocol://hostname:port/database_name
    private static final String URL = "jdbc:mysql://localhost:3306/library_management_system";

    private static final String USER = "root";
    private static final String PASSWORD = "Horng160806";

    public static Connection getConnection() throws SQLException {
        try {
            // 3. DATABASE DRIVER (Ensure your project libraries include this connector jar)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}