package mock_data_generator;

import java.sql.*;
import javax.swing.JOptionPane;
import JDBC.Database;

public class soloParent_MockData {

    public static void main(String[] args) {
        try (Connection conn = Database.getConnection()) {
            createApplicantTable(conn);
            createFamCompositionTable(conn);
            createPlannerTable(conn);
            createUsersTable(conn);
            insertMockApplicantData(conn);
            insertFamilyComposition(conn);
            insertPlannerActivities(conn);
            JOptionPane.showMessageDialog(null, "✅ All tables created and mock data inserted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "❌ Failed to initialize database: " + e.getMessage());
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

        String[][] users = {
            {"admin", "1234"},
            {"testuser", "testpass"}
        };

        try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
            for (String[] user : users) {
                pstmt.setString(1, user[0]);
                pstmt.setString(2, user[1]);
                pstmt.setString(3, user[0]);
                pstmt.executeUpdate();
            }
        }
    }

    private static void createApplicantTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS applicant_information (
                `No.` INT AUTO_INCREMENT PRIMARY KEY,
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

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
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

    private static void insertMockApplicantData(Connection conn) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM applicant_information WHERE applicant_id = ?";
        String insertSql = """
            INSERT INTO applicant_information (
                name, birthplace, age, birthdate, sex, civil_status, address,
                email_address, contact_num, highest_educ_attainment, occupation,
                company_name, monthly_income, annual_income, classification,
                date_of_wedding, date_of_separation, others, applicant_id
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

            Object[][] applicants = {
                {"Liza Moreno", "Quezon City", 27, "1998-11-25", "Female", "Single",
                        "Blk 2 Lot 8 Tandang Sora", "liza.moreno@example.com", "09171234500",
                        "Senior High", "Cashier", "Grocery Mart", "12000.00", "144000.00",
                        "Unmarried, Abandoned", null, null, "", "2023-03213-SP-0"},
                {"Ronald Vega", "Cavite", 39, "1986-05-10", "Male", "Married",
                        "Dasmariñas City", "ronald.vega@example.com", "09229998877",
                        "College Graduate", "Technician", "ElectroFix", "25000.00", "300000.00",
                        "Married, Annulled", "2011-02-14", "2019-06-01", "", "2023-04214-SP-0"},
                {"Carla Buenaventura", "Cebu City", 32, "1993-08-08", "Female", "Single",
                        "Mabolo, Cebu City", "carla.b@example.com", "09338887766",
                        "College", "Online Seller", "", "18000.00", "216000.00",
                        "Unmarried, Death of Partner", null, null, "", "2023-31134-SP-0"},
                {"Mark Solis", "Iloilo", 48, "1977-04-01", "Male", "Married",
                        "Jaro, Iloilo City", "marksolis@ymail.com", "09180001122",
                        "Post Graduate", "Professor", "WVSU", "40000.00", "480000.00",
                        "Married, Widow/er", "2000-05-15", null, "", "2023-12451-SP-0"},
                {"Jennylyn Arce", "Zamboanga", 34, "1991-07-17", "Female", "Single",
                        "Ayala, Zamboanga City", "jen.arce@gmail.com", "09092224455",
                        "Vocational", "Massage Therapist", "", "10000.00", "120000.00",
                        "Others", null, null, "Partner imprisoned for more than 10 years", "2023-52342-SP-0"}
            };

            for (Object[] a : applicants) {
                checkStmt.setString(1, (String) a[18]);
                ResultSet rs = checkStmt.executeQuery();
                rs.next();
                if (rs.getInt(1) == 0) {
                    insertStmt.setString(1, (String) a[0]);
                    insertStmt.setString(2, (String) a[1]);
                    insertStmt.setInt(3, (Integer) a[2]);
                    insertStmt.setDate(4, java.sql.Date.valueOf((String) a[3]));
                    insertStmt.setString(5, (String) a[4]);
                    insertStmt.setString(6, (String) a[5]);
                    insertStmt.setString(7, (String) a[6]);
                    insertStmt.setString(8, (String) a[7]);
                    insertStmt.setString(9, (String) a[8]);
                    insertStmt.setString(10, (String) a[9]);
                    insertStmt.setString(11, (String) a[10]);
                    insertStmt.setString(12, (String) a[11]);
                    insertStmt.setBigDecimal(13, new java.math.BigDecimal((String) a[12]));
                    insertStmt.setBigDecimal(14, new java.math.BigDecimal((String) a[13]));
                    insertStmt.setString(15, (String) a[14]);
                    insertStmt.setString(16, (String) a[15]);
                    insertStmt.setString(17, (String) a[16]);
                    insertStmt.setString(18, (String) a[17]);
                    insertStmt.setString(19, (String) a[18]);
                    insertStmt.executeUpdate();
                }
            }
        }
    }

    private static void insertFamilyComposition(Connection conn) throws SQLException {
        String sql = """
            INSERT INTO fam_composition (
                applicant_id, full_name, relationship, age, birthdate, civil_status,
                educational_attainment, occupation, monthly_income
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "2023-03213-SP-0");
            pstmt.setString(2, "Maria Moreno");
            pstmt.setString(3, "Mother");
            pstmt.setInt(4, 54);
            pstmt.setString(5, "1970-01-12");
            pstmt.setString(6, "Widowed");
            pstmt.setString(7, "High School");
            pstmt.setString(8, "Housewife");
            pstmt.setDouble(9, 0.00);
            pstmt.executeUpdate();

            pstmt.setString(1, "2023-04214-SP-0");
            pstmt.setString(2, "Carlos Vega");
            pstmt.setString(3, "Son");
            pstmt.setInt(4, 14);
            pstmt.setString(5, "2011-07-15");
            pstmt.setString(6, "Single");
            pstmt.setString(7, "Junior High School");
            pstmt.setString(8, "");
            pstmt.setDouble(9, 0.00);
            pstmt.executeUpdate();

            pstmt.setString(1, "2023-31134-SP-0");
            pstmt.setString(2, "Jasmine Buenaventura");
            pstmt.setString(3, "Daughter");
            pstmt.setInt(4, 7);
            pstmt.setString(5, "2018-06-10");
            pstmt.setString(6, "Single");
            pstmt.setString(7, "Elementary");
            pstmt.setString(8, "");
            pstmt.setDouble(9, 0.00);
            pstmt.executeUpdate();

            pstmt.setString(1, "2023-12451-SP-0");
            pstmt.setString(2, "Elaine Solis");
            pstmt.setString(3, "Daughter");
            pstmt.setInt(4, 21);
            pstmt.setString(5, "2003-03-25");
            pstmt.setString(6, "Single");
            pstmt.setString(7, "College");
            pstmt.setString(8, "Intern");
            pstmt.setDouble(9, 9000.00);
            pstmt.executeUpdate();

            pstmt.setString(1, "2023-52342-SP-0");
            pstmt.setString(2, "Luis Arce");
            pstmt.setString(3, "Father");
            pstmt.setInt(4, 62);
            pstmt.setString(5, "1963-11-11");
            pstmt.setString(6, "Married");
            pstmt.setString(7, "Elementary");
            pstmt.setString(8, "Retired Farmer");
            pstmt.setDouble(9, 3000.00);
            pstmt.executeUpdate();
        }
    }

    private static void insertPlannerActivities(Connection conn) throws SQLException {
        String sql = """
            INSERT INTO planner_activities (
                activity_date, title, fund, status, description
            ) VALUES (?, ?, ?, ?, ?)
        """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, java.sql.Date.valueOf("2025-07-10"));
            pstmt.setString(2, "Barangay Clean-up Drive");
            pstmt.setString(3, "Barangay Fund - ₱10,000");
            pstmt.setString(4, "Pending");
            pstmt.setString(5, "Organizing volunteers to clean streets and waterways.");
            pstmt.executeUpdate();

            pstmt.setDate(1, java.sql.Date.valueOf("2025-08-01"));
            pstmt.setString(2, "Back-to-School Support Program");
            pstmt.setString(3, "Education Fund - ₱25,000");
            pstmt.setString(4, "Approved");
            pstmt.setString(5, "Distribution of school supplies for indigent students.");
            pstmt.executeUpdate();

            pstmt.setDate(1, java.sql.Date.valueOf("2025-07-20"));
            pstmt.setString(2, "Free Medical Check-up");
            pstmt.setString(3, "Health Fund - ₱18,500");
            pstmt.setString(4, "Approved");
            pstmt.setString(5, "Medical services for seniors and children.");
            pstmt.executeUpdate();

            pstmt.setDate(1, java.sql.Date.valueOf("2025-08-15"));
            pstmt.setString(2, "Livelihood Skills Workshop");
            pstmt.setString(3, "Livelihood Fund - ₱12,000");
            pstmt.setString(4, "Pending");
            pstmt.setString(5, "Sewing and cooking workshop for solo parents.");
            pstmt.executeUpdate();

            pstmt.setDate(1, java.sql.Date.valueOf("2025-09-05"));
            pstmt.setString(2, "Feeding Program for Children");
            pstmt.setString(3, "Nutrition Fund - ₱15,000");
            pstmt.setString(4, "Approved");
            pstmt.setString(5, "One-week feeding project for malnourished children.");
            pstmt.executeUpdate();
        }
    }
}