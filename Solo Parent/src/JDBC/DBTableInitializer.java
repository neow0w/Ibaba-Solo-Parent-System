package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class DBTableInitializer {

    public static void main(String[] args) {
        try (Connection conn = Database.getConnection()) {
        	createUsersTable(conn);
            createApplicantTable(conn);
            createFamCompositionTable(conn);
            createPlannerTable(conn);
            JOptionPane.showMessageDialog(null, "✅ All tables created or verified successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "❌ Failed to initialize database tables:\n" + e.getMessage());
        }
    }
    private static void createUsersTable(Connection conn) throws SQLException {
        String createSql = """
            CREATE TABLE IF NOT EXISTS users (
                id INT AUTO_INCREMENT PRIMARY KEY,
                username VARCHAR(50) NOT NULL UNIQUE,
                password VARCHAR(255) NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
            )
        """;

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createSql);
        }

        String insertSql = """
            INSERT INTO users (username, password)
            SELECT * FROM (SELECT ? AS username, ? AS password) AS tmp
            WHERE NOT EXISTS (
                SELECT 1 FROM users WHERE username = ?
            ) LIMIT 1
        """;

        try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
            pstmt.setString(1, "admin");
            pstmt.setString(2, "1234"); 
            pstmt.setString(3, "admin");
            pstmt.executeUpdate();
        }
    }

    private static void createApplicantTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS applicant_information (
                No INT AUTO_INCREMENT PRIMARY KEY,
                applicant_id VARCHAR(255) NOT NULL UNIQUE,  
                name VARCHAR(255) NOT NULL,
                birthplace VARCHAR(255) NOT NULL,
                age INT,
                birthdate DATE,
                sex ENUM('Male', 'Female', 'Unspecified') NOT NULL,
                civil_status ENUM('Single', 'Married') NOT NULL,
                address VARCHAR(255),
                email_address VARCHAR(255),
                contact_num VARCHAR(15),
                highest_educ_attainment VARCHAR(255),
                occupation VARCHAR(255),
                company_name VARCHAR(255),
                monthly_income DECIMAL(15, 2),
                annual_income DECIMAL(15, 2),
                classification VARCHAR(255),
                date_of_wedding VARCHAR(50),
                date_of_separation VARCHAR(50),
                others VARCHAR(255),
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
            )
        """;

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }


    private static void createFamCompositionTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS fam_composition (
                id INT AUTO_INCREMENT PRIMARY KEY,
                applicant_id VARCHAR(255) NOT NULL,
                full_name VARCHAR(255),
                relationship VARCHAR(100),
                age INT,
                birthdate VARCHAR(50),
                civil_status VARCHAR(50),
                educational_attainment VARCHAR(100),
                occupation VARCHAR(100),
                monthly_income DOUBLE,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                FOREIGN KEY (applicant_id) REFERENCES applicant_information(applicant_id)
            )
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.execute();
        }
    }


    private static void createPlannerTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS planner_activities (
                    activity_date DATE PRIMARY KEY,
                    title VARCHAR(255),
                    fund VARCHAR(50),
                    status VARCHAR(50),
                    description TEXT,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
            )
        """;

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
    
    
}