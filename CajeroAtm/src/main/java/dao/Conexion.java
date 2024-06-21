package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:sqlserver://localhost:1434;database=Atm";
    private static Connection connection;

    public static Connection obtenerConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(URL);
            return connection;
        } catch (ClassNotFoundException e) {
            System.err.println("Error: JDBC Driver class not found. Please check your classpath.");
        } catch (SQLException sqlException) {
            System.err.println("Error: Could not connect to SQL Server database.");
            sqlException.printStackTrace();
        }
        return null;
    }

    public static void cerrarConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException exception) {
                System.err.println("Error: Could not close connection.");
                exception.printStackTrace();
            }
        }
    }
}


