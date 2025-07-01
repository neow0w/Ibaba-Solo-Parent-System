package recordPage;

import MainPage.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import JDBC.Database;

public class addMemberPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JTextField nameBar, placeofBirthBar, ageBar, birthdateBar, addressBar, emailAddBar, contactNumberBar;
    private JTextField highestEducationalBar, occupationBar, companyNameBar, annualIncomeBar, monthlyIncomeBar, soloParentID;
    private JButton cancel_backBTN, next_saveBTN;
    private JComboBox<String> civilstatusDropdown;
    private JRadioButton maleRadio;
    private JRadioButton femaleRadio;
    private ButtonGroup sexGroup;
    private Runnable onDataSaved;
    private informationPage parentInfoPage;
    private homePage home;
    private String editingApplicantId = null; 

    private int panelIndex = 0;
    private final String[] panelNames = {"applicant", "Family", "Classification"};

    private familyCompositionPanel familyCompositionPanel;
    private classificationPanel classificationPanel;

    public static void launch() {
        EventQueue.invokeLater(() -> {
            try {
                addMemberPage frame = new addMemberPage(() -> {
                    System.out.println("Data saved.");
                });
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public addMemberPage(Runnable onDataSaved) {
        this.onDataSaved = onDataSaved;
        initializeUI();
    }

    public addMemberPage(String applicantId, informationPage parent, Runnable onDataSaved) {
        this.onDataSaved = onDataSaved;
        this.parentInfoPage = parent;
        initializeUI();
        loadApplicantData(applicantId);
        if (parentInfoPage != null) {
            parentInfoPage.showDim();
        }
    }

    public addMemberPage(homePage home) {
        this(() -> {
            if (home != null) {
                home.refreshDashboardData();
            }
        });
        this.home = home;
    }

    private void initializeUI() {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 90, 1110, 760);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(238, 235, 235));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBounds(0, 30, 1110, 665);
        contentPane.add(mainPanel);

        JPanel applicantPanel = new JPanel();
        applicantPanel.setBackground(new Color(238, 235, 235));
        applicantPanel.setLayout(null);
        mainPanel.add(applicantPanel, "applicant");
        
        JLabel personalDetails = new JLabel("Personal Details");
        personalDetails.setFont(new Font("Tahoma", Font.BOLD, 16));
        personalDetails.setBounds(29, 130, 145, 17);
        applicantPanel.add(personalDetails);

        JPanel hrLine1 = new JPanel();
        hrLine1.setBackground(new Color(104, 104, 104));
        hrLine1.setBounds(30, 110, 1050, 2);
        applicantPanel.add(hrLine1);

        JLabel name = new JLabel("Name/Pangalan (GIVEN/MIDDLE/LAST):");
        name.setFont(new Font("Tahoma", Font.PLAIN, 16));
        name.setBounds(260, 130, 303, 17);
        applicantPanel.add(name);

        JLabel placeofBirth = new JLabel("Place of Birth/Lugar kung saan ipinanganak:");
        placeofBirth.setFont(new Font("Tahoma", Font.PLAIN, 16));
        placeofBirth.setBounds(665, 130, 339, 17);
        applicantPanel.add(placeofBirth);

        JLabel sex = new JLabel("Sex/Kasarian:");
        sex.setFont(new Font("Tahoma", Font.PLAIN, 16));
        sex.setBounds(390, 195, 110, 17);
        applicantPanel.add(sex);

        JLabel civilStatus = new JLabel("Civil Status: ");
        civilStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));
        civilStatus.setBounds(260, 260, 95, 17);
        applicantPanel.add(civilStatus);

        JPanel hrLine2 = new JPanel();
        hrLine2.setBackground(new Color(104, 104, 104));
        hrLine2.setBounds(29, 320, 1050, 2);
        applicantPanel.add(hrLine2);

        JLabel addressDetails = new JLabel("Address Details");
        addressDetails.setFont(new Font("Tahoma", Font.BOLD, 16));
        addressDetails.setBounds(29, 330, 145, 17);
        applicantPanel.add(addressDetails);

        JLabel address = new JLabel("Address/Tirahan:");
        address.setFont(new Font("Tahoma", Font.PLAIN, 16));
        address.setBounds(260, 330, 130, 17);
        applicantPanel.add(address);

        JLabel emailAdd = new JLabel("Email Address (Optional):");
        emailAdd.setFont(new Font("Tahoma", Font.PLAIN, 16));
        emailAdd.setBounds(665, 330, 195, 17);
        applicantPanel.add(emailAdd);

        JLabel contactNumber = new JLabel("Contact Number:");
        contactNumber.setFont(new Font("Tahoma", Font.PLAIN, 16));
        contactNumber.setBounds(260, 395, 130, 17);
        applicantPanel.add(contactNumber);

        JPanel hrLine3 = new JPanel();
        hrLine3.setBackground(new Color(104, 104, 104));
        hrLine3.setBounds(29, 460, 1050, 2);
        applicantPanel.add(hrLine3);

        JLabel educationalBackground = new JLabel("Educational Background");
        educationalBackground.setFont(new Font("Tahoma", Font.BOLD, 16));
        educationalBackground.setBounds(29, 470, 205, 20);
        applicantPanel.add(educationalBackground);

        JLabel highestEducational = new JLabel("Highest Educational Attainment:");
        highestEducational.setFont(new Font("Tahoma", Font.PLAIN, 16));
        highestEducational.setBounds(260, 470, 240, 17);
        applicantPanel.add(highestEducational);

        JPanel hrLine4 = new JPanel();
        hrLine4.setBackground(new Color(104, 104, 104));
        hrLine4.setBounds(29, 535, 1050, 2);
        applicantPanel.add(hrLine4);

        JLabel employment = new JLabel("Employment and");
        employment.setFont(new Font("Tahoma", Font.BOLD, 16));
        employment.setBounds(29, 545, 145, 17);
        applicantPanel.add(employment);

        JLabel income = new JLabel("Income Details");
        income.setFont(new Font("Tahoma", Font.BOLD, 16));
        income.setBounds(29, 567, 130, 17);
        applicantPanel.add(income);

        JLabel occupation = new JLabel("Occupation/Trabaho:");
        occupation.setFont(new Font("Tahoma", Font.PLAIN, 16));
        occupation.setBounds(260, 545, 160, 17);
        applicantPanel.add(occupation);

        JLabel companyName = new JLabel("Name of Company/Kumpanyang Pinagtatrabahuhan:");
        companyName.setFont(new Font("Tahoma", Font.PLAIN, 16));
        companyName.setBounds(260, 605, 380, 17);
        applicantPanel.add(companyName);

        JLabel annualIncome = new JLabel("Annual Family Income:");
        annualIncome.setFont(new Font("Tahoma", Font.PLAIN, 16));
        annualIncome.setBounds(665, 605, 180, 17);
        applicantPanel.add(annualIncome);

        JLabel monthlyIncome = new JLabel("Monthly Income/Buwanang Sweldo:");
        monthlyIncome.setFont(new Font("Tahoma", Font.PLAIN, 16));
        monthlyIncome.setBounds(665, 545, 260, 17);
        applicantPanel.add(monthlyIncome);

        JLabel applicantInformation = new JLabel("Applicant Information");
        applicantInformation.setFont(new Font("Tahoma", Font.BOLD, 14));
        applicantInformation.setBounds(215, 60, 165, 30);
        applicantPanel.add(applicantInformation);

        JPanel firstProcessPanel = new JPanel();
        firstProcessPanel.setBackground(new Color(255, 64, 169));
        firstProcessPanel.setBounds(275, 20, 40, 40);
        applicantPanel.add(firstProcessPanel);
        firstProcessPanel.setLayout(null);

        JLabel firstProcess = new JLabel("1");
        firstProcess.setForeground(new Color(255, 255, 255));
        firstProcess.setHorizontalAlignment(SwingConstants.CENTER);
        firstProcess.setFont(new Font("Tahoma", Font.BOLD, 16));
        firstProcess.setBounds(5, 10, 30, 20);
        firstProcessPanel.add(firstProcess);

        JPanel secondProcessPanel = new JPanel();
        secondProcessPanel.setLayout(null);
        secondProcessPanel.setBackground(new Color(189, 189, 189));
        secondProcessPanel.setBounds(540, 20, 40, 40);
        applicantPanel.add(secondProcessPanel);

        JLabel secondProcess = new JLabel("2");
        secondProcess.setHorizontalAlignment(SwingConstants.CENTER);
        secondProcess.setForeground(new Color(0, 0, 0));
        secondProcess.setFont(new Font("Tahoma", Font.BOLD, 16));
        secondProcess.setBounds(5, 10, 30, 20);
        secondProcessPanel.add(secondProcess);

        JLabel familyComposition = new JLabel("Family Composition");
        familyComposition.setFont(new Font("Tahoma", Font.BOLD, 14));
        familyComposition.setBounds(490, 60, 145, 30);
        applicantPanel.add(familyComposition);

        JPanel thirdProcessPanel = new JPanel();
        thirdProcessPanel.setLayout(null);
        thirdProcessPanel.setBackground(new Color(189, 189, 189));
        thirdProcessPanel.setBounds(795, 20, 40, 40);
        applicantPanel.add(thirdProcessPanel);

        JLabel thirdProcess = new JLabel("3");
        thirdProcess.setHorizontalAlignment(SwingConstants.CENTER);
        thirdProcess.setForeground(Color.BLACK);
        thirdProcess.setFont(new Font("Tahoma", Font.BOLD, 16));
        thirdProcess.setBounds(5, 10, 30, 20);
        thirdProcessPanel.add(thirdProcess);

        JLabel classification = new JLabel("Classification");
        classification.setFont(new Font("Tahoma", Font.BOLD, 14));
        classification.setBounds(770, 60, 95, 30);
        applicantPanel.add(classification);

        JPanel line1 = new JPanel();
        line1.setBackground(new Color(128, 128, 128));
        line1.setBounds(335, 40, 180, 4);
        applicantPanel.add(line1);

        JPanel line2 = new JPanel();
        line2.setBackground(new Color(128, 128, 128));
        line2.setBounds(595, 40, 180, 4);
        applicantPanel.add(line2);

        JLabel applicationDate = new JLabel("DATE OF APPLICATION:");
        applicationDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        applicationDate.setBounds(29, 710, 185, 17);
        contentPane.add(applicationDate);

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        String formattedDate = today.format(formatter);

        JLabel dateToday = new JLabel(formattedDate);
        dateToday.setFont(new Font("Tahoma", Font.PLAIN, 18));
        dateToday.setBounds(220, 705, 219, 25);
        contentPane.add(dateToday);

        JPanel topBorder = new JPanel();
        topBorder.setBackground(new Color(52, 52, 52));
        topBorder.setBounds(0, 0, 1110, 30);
        contentPane.add(topBorder);
        topBorder.setLayout(null);

        JButton ExitButton = new JButton("X");
        ExitButton.setBounds(1065, 0, 45, 30);
        topBorder.add(ExitButton);
        styleButton(ExitButton);

        JLabel memberForm = new JLabel("MEMBER FORM");
        memberForm.setFont(new Font("Segoe UI", Font.BOLD, 16));
        memberForm.setForeground(new Color(238, 235, 235));
        memberForm.setBounds(20, 5, 150, 20);
        topBorder.add(memberForm);

        ExitButton.addActionListener(e -> {
            dispose();
            if (parentInfoPage != null) {
                parentInfoPage.hideDim();
            } else if (mainPage.instance != null) {
                mainPage.instance.hideDim();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (parentInfoPage != null) {
                    parentInfoPage.hideDim();
                } else if (mainPage.instance != null) {
                    mainPage.instance.hideDim();
                }
            }
        });

        nameBar = new JTextField();
        nameBar.setBounds(260, 155, 384, 30);
        applicantPanel.add(nameBar);
        placeofBirthBar = new JTextField();
        placeofBirthBar.setBounds(665, 155, 393, 30);
        applicantPanel.add(placeofBirthBar);
        ageBar = new JTextField();
        ageBar.setBounds(260, 220, 75, 30);
        applicantPanel.add(ageBar);
        birthdateBar = new JTextField();
        birthdateBar.setBounds(665, 220, 187, 30);
        applicantPanel.add(birthdateBar);
        addressBar = new JTextField();
        addressBar.setBounds(260, 355, 384, 30);
        applicantPanel.add(addressBar);
        emailAddBar = new JTextField();
        emailAddBar.setBounds(665, 355, 393, 30);
        applicantPanel.add(emailAddBar);
        contactNumberBar = new JTextField();
        contactNumberBar.setBounds(260, 420, 205, 30);
        applicantPanel.add(contactNumberBar);
        highestEducationalBar = new JTextField();
        highestEducationalBar.setBounds(260, 495, 384, 30);
        applicantPanel.add(highestEducationalBar);
        occupationBar = new JTextField();
        occupationBar.setBounds(260, 568, 384, 30);
        applicantPanel.add(occupationBar);
        companyNameBar = new JTextField();
        companyNameBar.setBounds(260, 630, 384, 30);
        applicantPanel.add(companyNameBar);
        annualIncomeBar = new JTextField();
        annualIncomeBar.setBounds(665, 630, 384, 30);
        applicantPanel.add(annualIncomeBar);
        monthlyIncomeBar = new JTextField();
        monthlyIncomeBar.setBounds(665, 568, 384, 30);
        applicantPanel.add(monthlyIncomeBar);

        nameBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        placeofBirthBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        ageBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        birthdateBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        addressBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        emailAddBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        contactNumberBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        highestEducationalBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        occupationBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        companyNameBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        annualIncomeBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        monthlyIncomeBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        civilstatusDropdown = new JComboBox<>(new String[]{"Single", "Married"});
        civilstatusDropdown.setBounds(260, 285, 120, 25);
        applicantPanel.add(civilstatusDropdown);

        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");

        sexGroup = new ButtonGroup();
        sexGroup.add(maleRadio);
        sexGroup.add(femaleRadio);

        maleRadio.setBounds(388, 225, 66, 21);
        femaleRadio.setBounds(470, 225, 85, 21);

        applicantPanel.add(maleRadio);
        applicantPanel.add(femaleRadio);

        JLabel Age = new JLabel("Age:");
        Age.setFont(new Font("Tahoma", Font.PLAIN, 16));
        Age.setBounds(260, 195, 75, 17);
        applicantPanel.add(Age);

        JLabel birthdate = new JLabel("Birthdate (MM/DD/YYYY):");
        birthdate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        birthdate.setBounds(665, 195, 220, 17);
        applicantPanel.add(birthdate);

        soloParentID = new JTextField();
        soloParentID.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        soloParentID.setBounds(665, 280, 280, 30);
        applicantPanel.add(soloParentID);

        JLabel soloParentIDNumber = new JLabel("Solo Parent I.D Number");
        soloParentIDNumber.setFont(new Font("Tahoma", Font.PLAIN, 16));
        soloParentIDNumber.setBounds(665, 255, 220, 17);
        applicantPanel.add(soloParentIDNumber);

        familyCompositionPanel = new familyCompositionPanel();
        classificationPanel = new classificationPanel();
        mainPanel.add(familyCompositionPanel, "Family");
        mainPanel.add(classificationPanel, "Classification");

        cancel_backBTN = new RoundedButton("CANCEL");
        cancel_backBTN.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancel_backBTN.setForeground(new Color(0, 0, 0));
        cancel_backBTN.setBackground(new Color(213, 213, 213));
        cancel_backBTN.setBounds(815, 705, 110, 40);
        cancel_backBTN.addActionListener(e -> {
            if (panelIndex == 0) {
                dispose();
                if (parentInfoPage != null) {
                    parentInfoPage.hideDim();
                } else if (mainPage.instance != null) {
                    mainPage.instance.hideDim();
                }
            } else {
                panelIndex--;
                cardLayout.show(mainPanel, panelNames[panelIndex]);
                updateButtons();
            }
        });
        contentPane.add(cancel_backBTN);

        next_saveBTN = new RoundedButton("NEXT");
        next_saveBTN.setFont(new Font("Segoe UI", Font.BOLD, 14));
        next_saveBTN.setForeground(new Color(0, 0, 0));
        next_saveBTN.setBounds(939, 705, 110, 40);
        next_saveBTN.addActionListener(e -> {
            if (panelIndex == 0) {
                if (!validateApplicantPanel()) return;
            }
            if (panelIndex < panelNames.length - 1) {
                panelIndex++;
                cardLayout.show(mainPanel, panelNames[panelIndex]);
                updateButtons();
            } else {
                saveApplicantInformation();
                if (parentInfoPage != null) {
                    parentInfoPage.hideDim();
                } else if (mainPage.instance != null) {
                    mainPage.instance.hideDim();
                }
                if (onDataSaved != null) {
                    onDataSaved.run();
                }
                dispose();
            }
        });
        contentPane.add(next_saveBTN);
        updateButtons();
    }

    private String getSelectedSex() {
        if (maleRadio.isSelected()) return "Male";
        if (femaleRadio.isSelected()) return "Female";
        return "Unspecified";
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS applicant_information (" +
                "No INT AUTO_INCREMENT PRIMARY KEY, " +
                "applicant_id VARCHAR(255) NOT NULL, " +
                "name VARCHAR(255) NOT NULL, " +
                "birthplace VARCHAR(255) NOT NULL, " +
                "age INT, " +
                "birthdate VARCHAR(10), " + 
                "sex ENUM('Male', 'Female', 'Unspecified') NOT NULL, " +
                "civil_status ENUM('Single', 'Married') NOT NULL, " +
                "address VARCHAR(255), " +
                "email_address VARCHAR(255), " +
                "contact_num VARCHAR(15), " +
                "highest_educ_attainment VARCHAR(255), " +
                "occupation VARCHAR(255), " +
                "company_name VARCHAR(255), " +
                "monthly_income DECIMAL(15, 2), " +
                "annual_income DECIMAL(15, 2), " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                ")";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to initialize database table.");
        }
    }

    private void loadApplicantData(String applicantId) {
        this.editingApplicantId = applicantId;

        try (Connection conn = Database.getConnection()) {
            String query = "SELECT * FROM applicant_information WHERE applicant_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, applicantId); 
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    nameBar.setText(rs.getString("name"));
                    placeofBirthBar.setText(rs.getString("birthplace"));
                    ageBar.setText(String.valueOf(rs.getInt("age")));
                    birthdateBar.setText(rs.getString("birthdate"));
                    addressBar.setText(rs.getString("address"));
                    emailAddBar.setText(rs.getString("email_address"));
                    contactNumberBar.setText(rs.getString("contact_num"));
                    highestEducationalBar.setText(rs.getString("highest_educ_attainment"));
                    occupationBar.setText(rs.getString("occupation"));
                    companyNameBar.setText(rs.getString("company_name"));
                    monthlyIncomeBar.setText(String.valueOf(rs.getDouble("monthly_income"))); 
                    annualIncomeBar.setText(String.valueOf(rs.getDouble("annual_income"))); 
                    soloParentID.setText(rs.getString("applicant_id"));

                    String sex = rs.getString("sex");
                    if ("Male".equalsIgnoreCase(sex)) maleRadio.setSelected(true);
                    else if ("Female".equalsIgnoreCase(sex)) femaleRadio.setSelected(true);

                    civilstatusDropdown.setSelectedItem(rs.getString("civil_status"));
                }
            }
            familyCompositionPanel.loadFamilyCompositionByApplicantId(applicantId);
            classificationPanel.loadClassificationInformation(applicantId);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load applicant data: " + e.getMessage());
        }
    }

    private void saveApplicantInformation() {
        String civilStatus = (String) civilstatusDropdown.getSelectedItem();

        try (Connection conn = Database.getConnection()) {
            createTableIfNotExists(); 
            String sql;
            boolean isEditMode = editingApplicantId != null;

            if (isEditMode) {
                sql = "UPDATE applicant_information SET applicant_id=?, name=?, birthplace=?, age=?, birthdate=?, sex=?, civil_status=?, " +
                      "address=?, email_address=?, contact_num=?, highest_educ_attainment=?, occupation=?, company_name=?, " +
                      "monthly_income=?, annual_income=? WHERE applicant_id=?";
            } else {
                sql = "INSERT INTO applicant_information (applicant_id, name, birthplace, age, birthdate, sex, civil_status, " +
                      "address, email_address, contact_num, highest_educ_attainment, occupation, company_name, monthly_income, annual_income) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, soloParentID.getText());
                stmt.setString(2, nameBar.getText());
                stmt.setString(3, placeofBirthBar.getText());

                try {
                    int age = Integer.parseInt(ageBar.getText());
                    stmt.setInt(4, age);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid whole number for age.");
                    return;
                }
                stmt.setString(5, birthdateBar.getText());
                stmt.setString(6, getSelectedSex());
                stmt.setString(7, civilStatus);
                stmt.setString(8, addressBar.getText());
                stmt.setString(9, emailAddBar.getText());
                stmt.setString(10, contactNumberBar.getText());
                stmt.setString(11, highestEducationalBar.getText());
                stmt.setString(12, occupationBar.getText());
                stmt.setString(13, companyNameBar.getText());

                String monthlyIncomeText = monthlyIncomeBar.getText().replace("₱", "").trim();
                String annualIncomeText = annualIncomeBar.getText().replace("₱", "").trim();

                try {
                    double monthly = Double.parseDouble(monthlyIncomeText);
                    double annual = Double.parseDouble(annualIncomeText);
                    stmt.setDouble(14, monthly);
                    stmt.setDouble(15, annual);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Please enter valid numeric values for income.");
                    return;
                }

                if (isEditMode) {
                    stmt.setString(16, editingApplicantId); 
                }

                int result = stmt.executeUpdate();
                if (result > 0) {
                    String applicantId = soloParentID.getText(); 

                    String updateTimestampQuery = "UPDATE applicant_information SET updated_at = CURRENT_TIMESTAMP WHERE applicant_id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateTimestampQuery)) {
                        updateStmt.setString(1, applicantId);
                        updateStmt.executeUpdate();
                    }

                    familyCompositionPanel.insertOrUpdateFamilyComposition(familyCompositionPanel.getTable(), applicantId);
                    classificationPanel.classificationInformation(applicantId);

                    JOptionPane.showMessageDialog(this, isEditMode ? "Update successful!" : "Saved successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Save failed.");
                    return;
                }
            }

            if (onDataSaved != null) {
                onDataSaved.run();
                dispose();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage());
        }
    }

    private boolean validateApplicantPanel() {
        if (nameBar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name.");
            return false;
        }
        if (placeofBirthBar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your place of birth.");
            return false;
        }
        String ageText = ageBar.getText().trim();
        if (ageText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your age.");
            return false;
        }
        try {
            int age = Integer.parseInt(ageText);
            if (age < 1 || age > 100) {
                JOptionPane.showMessageDialog(this, "Invalid Age.");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Age must be a valid number.");
            return false;
        }
        String birthdateText = birthdateBar.getText().trim();
        if (birthdateText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your birthdate.");
            return false;
        }
        if (!birthdateText.matches("^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/\\d{4}$")) {
            JOptionPane.showMessageDialog(this, "Birthdate must be in MM/DD/YYYY format.");
            return false;
        }
        if (!maleRadio.isSelected() && !femaleRadio.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select a sex.");
            return false;
        }
        if (soloParentID.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your Solo Parent I.D Number.");
            return false;
        }
        if (addressBar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your address.");
            return false;
        }
        String contactNumberText = contactNumberBar.getText().trim();
        if (contactNumberText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your contact number.");
            return false;
        }
        if (!contactNumberText.matches("\\d{1,11}")) {
            JOptionPane.showMessageDialog(this, "Contact number must be 11 digits and contain only numbers.");
            return false;
        }
        if (highestEducationalBar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your highest educational attainment.");
            return false;
        }
        if (occupationBar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your occupation.");
            return false;
        }
        if (companyNameBar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your company name.");
            return false;
        }
        String annualIncomeText = annualIncomeBar.getText().trim().replace("₱", "");
        if (annualIncomeText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your annual income.");
            return false;
        }
        try {
            Double.parseDouble(annualIncomeText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Annual income must be a valid number.");
            return false;
        }
        String monthlyIncomeText = monthlyIncomeBar.getText().trim().replace("₱", "");
        if (monthlyIncomeText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your monthly income.");
            return false;
        }
        try {
            Double.parseDouble(monthlyIncomeText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Monthly income must be a valid number.");
            return false;
        }
        return true;
    }

    private void updateButtons() {
        cancel_backBTN.setText(panelIndex == 0 ? "CANCEL" : "BACK");
        next_saveBTN.setText(panelIndex == panelNames.length - 1 ? "SAVE" : "NEXT");
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(255, 0, 0));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}

class RoundedButton extends JButton {
    private Color pressedBackground = new Color(173, 216, 230);

    public RoundedButton(String text) {
        super(text);
        setBackground(new Color(255, 64, 169));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (getModel().isPressed()) {
            g2.setColor(pressedBackground);
        } else {
            g2.setColor(getBackground());
        }
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        g2.dispose();
        super.paintComponent(g);
    }
}