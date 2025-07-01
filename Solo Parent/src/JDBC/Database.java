package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class Database {
    private static final String PROPERTIES_FILE = "config.properties";
    private static String DB_NAME;
    private static String URL_WITH_DB;
    private static String URL_WITHOUT_DB;
    private static String USERNAME;
    private static String PASSWORD;

    static {
        Properties props = new Properties();
        try (FileInputStream input = new FileInputStream(PROPERTIES_FILE)) {
            props.load(input);
            DB_NAME = props.getProperty("db.name", "solo_parent_system");
            URL_WITH_DB = props.getProperty("db.url.with_db", "jdbc:mysql://localhost:3306/") + DB_NAME;
            URL_WITHOUT_DB = props.getProperty("db.url.with_db", "jdbc:mysql://localhost:3306/");
            USERNAME = props.getProperty("db.user", "root");
            PASSWORD = props.getProperty("db.password", "admin123");
            if (DB_NAME == null || URL_WITH_DB == null || USERNAME == null || PASSWORD == null) {
                throw new IllegalStateException("Missing required database properties in " + PROPERTIES_FILE);
            }
            initializeDatabase();
        } catch (IOException e) {
            System.err.println("Warning: Could not load " + PROPERTIES_FILE + ". Using default credentials.");
            DB_NAME = "solo_parent_system";
            URL_WITHOUT_DB = "jdbc:mysql://localhost:3306/";
            URL_WITH_DB = "jdbc:mysql://localhost:3306/" + DB_NAME;
            USERNAME = "root";
            PASSWORD = "admin123";
            initializeDatabase();
        }
    }

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(URL_WITHOUT_DB, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            System.out.println("MySQL database initialized.");
        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL_WITH_DB, USERNAME, PASSWORD);
            return conn;
        } catch (SQLException e) {
            System.err.println("Failed to connect to MySQL: " + e.getMessage());
            throw e;
        }
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("Connection test successful.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}