package recordPage;

import MainPage.*;
import homePage.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import JDBC.Database;

public class informationPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable familytable;
    private DefaultTableModel tableModel;
    private JLabel ageValue, bdayValue, educationalValue, addressValue, numberValue, emailAddValue, occupationValue, companyValue;
    private JLabel monthlyValue, annualValue, placeValue, statusValue, sexValue, name, soloParentID;
    private JButton editButton;
    private JPanel dimPanel; 

    public static void launch() {
        EventQueue.invokeLater(() -> {
            try {
            	informationPage frame = new informationPage("2023-31134-SP-0");
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public informationPage(String applicantId) {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1300, 800);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(238, 235, 235));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        dimPanel = new JPanel();
        dimPanel.setBackground(new Color(0, 0, 0, 100));
        dimPanel.setBounds(0, 0, 1300, 800);
        dimPanel.setVisible(false);
        contentPane.add(dimPanel);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(52, 52, 52));
        topPanel.setBounds(0, 0, 1300, 30);
        contentPane.add(topPanel);
        topPanel.setLayout(null);

        JButton exitBTN = new JButton("X");
        exitBTN.setBackground(new Color(200, 50, 50));
        exitBTN.setForeground(Color.WHITE);
        exitBTN.setBounds(1260, 0, 40, 30);
        topPanel.add(exitBTN);
        styleButton(exitBTN);

        exitBTN.addActionListener(e -> {
            dispose();
            boolean newMemberOpen = false;
            for (Frame frame : Frame.getFrames()) {
                if (frame instanceof newMember && frame.isVisible()) {
                    newMemberOpen = true;
                    break;
                }
            }
            if (mainPage.instance != null && !newMemberOpen) {
                mainPage.instance.hideDim();
            }
        });
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	boolean newMemberOpen = false;
                for (Frame frame : Frame.getFrames()) {
                    if (frame instanceof newMember && frame.isVisible()) {
                        newMemberOpen = true;
                        break;
                    }
                }
                if (mainPage.instance != null && !newMemberOpen) {
                    mainPage.instance.hideDim();
                }
            }
        });

        JLabel memberInfo = new JLabel("MEMBER INFORMATION");
        memberInfo.setForeground(new Color(238, 235, 235));
        memberInfo.setFont(new Font("Tahoma", Font.BOLD, 16));
        memberInfo.setBounds(20, 5, 205, 20);
        topPanel.add(memberInfo);

        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(0, 28, 1300, 793);
        mainPanel.setBackground(new Color(238, 235, 235));
        contentPane.add(mainPanel);
        mainPanel.setLayout(null);

        ImageIcon iconLogo = new ImageIcon(informationPage.class.getResource("/imgs/icon.png"));
        JLabel icon = new JLabel(iconLogo);
        icon.setBounds(40, 20, 120, 110);
        mainPanel.add(icon);

        name = new JLabel("");
        name.setFont(new Font("Tahoma", Font.BOLD, 24));
        name.setBounds(175, 20, 440, 35);
        mainPanel.add(name);

        soloParentID = new JLabel("");
        soloParentID.setFont(new Font("Tahoma", Font.PLAIN, 20));
        soloParentID.setBounds(175, 65, 300, 20);
        mainPanel.add(soloParentID);

        statusValue = new JLabel("");
        statusValue.setFont(new Font("Tahoma", Font.PLAIN, 20));
        statusValue.setBounds(175, 95, 400, 20);
        mainPanel.add(statusValue);

        JPanel hrLine1 = new JPanel();
        hrLine1.setBounds(25, 150, 1250, 2);
        mainPanel.add(hrLine1);

        JPanel hrLine2 = new JPanel();
        hrLine2.setBackground(new Color(104, 104, 104));
        hrLine2.setBounds(25, 309, 1250, 2);
        mainPanel.add(hrLine2);

        JLabel basicInfo = new JLabel("BASIC INFORMATION");
        basicInfo.setFont(new Font("Tahoma", Font.BOLD, 16));
        basicInfo.setBounds(75, 162, 186, 20);
        mainPanel.add(basicInfo);

        ImageIcon basLogo = new ImageIcon(informationPage.class.getResource("/imgs/info.png"));
        JLabel basicLogo = new JLabel(basLogo);
        basicLogo.setBounds(35, 155, 30, 30);
        mainPanel.add(basicLogo);

        JLabel Age = new JLabel("Age");
        Age.setFont(new Font("Tahoma", Font.PLAIN, 14));
        Age.setBounds(40, 195, 45, 20);
        mainPanel.add(Age);

        ageValue = new JLabel("");
        ageValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
        ageValue.setBounds(40, 220, 121, 25);
        mainPanel.add(ageValue);

        JLabel birthday = new JLabel("Birthday");
        birthday.setFont(new Font("Tahoma", Font.PLAIN, 14));
        birthday.setBounds(40, 250, 85, 20);
        mainPanel.add(birthday);

        bdayValue = new JLabel("");
        bdayValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
        bdayValue.setBounds(40, 275, 185, 25);
        mainPanel.add(bdayValue);

        JLabel civilStatus = new JLabel("Civil Status");
        civilStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));
        civilStatus.setBounds(240, 195, 90, 20);
        mainPanel.add(civilStatus);

        statusValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
        statusValue.setBounds(240, 220, 110, 25);
        mainPanel.add(statusValue);

        JLabel sex = new JLabel("Sex");
        sex.setFont(new Font("Tahoma", Font.PLAIN, 14));
        sex.setBounds(240, 250, 90, 20);
        mainPanel.add(sex);

        sexValue = new JLabel("");
        sexValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
        sexValue.setBounds(240, 275, 110, 25);
        mainPanel.add(sexValue);

        JLabel educationalBG = new JLabel("Education Background");
        educationalBG.setFont(new Font("Tahoma", Font.PLAIN, 14));
        educationalBG.setBounds(370, 195, 151, 20);
        mainPanel.add(educationalBG);

        educationalValue = new JLabel("");
        educationalValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
        educationalValue.setBounds(370, 220, 185, 25);
        mainPanel.add(educationalValue);

        JPanel vrLine = new JPanel();
        vrLine.setBackground(new Color(104, 104, 104));
        vrLine.setBounds(562, 160, 2, 137);
        mainPanel.add(vrLine);

        ImageIcon conLogo = new ImageIcon(informationPage.class.getResource("/imgs/contact.png"));
        JLabel contactLogo = new JLabel(conLogo);
        contactLogo.setBounds(585, 155, 30, 30);
        mainPanel.add(contactLogo);

        JLabel contactInfo = new JLabel("CONTACT INFORMATION");
        contactInfo.setFont(new Font("Tahoma", Font.BOLD, 16));
        contactInfo.setBounds(625, 162, 230, 20);
        mainPanel.add(contactInfo);

        JLabel address = new JLabel("Address");
        address.setFont(new Font("Tahoma", Font.PLAIN, 14));
        address.setBounds(590, 195, 75, 20);
        mainPanel.add(address);

        addressValue = new JLabel("");
        addressValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
        addressValue.setBounds(590, 220, 325, 25);
        mainPanel.add(addressValue);

        JLabel mobileNo = new JLabel("Mobile Number");
        mobileNo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        mobileNo.setBounds(590, 250, 110, 20);
        mainPanel.add(mobileNo);

        numberValue = new JLabel("");
        numberValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
        numberValue.setBounds(590, 275, 160, 25);
        mainPanel.add(numberValue);

        JLabel emailAdd = new JLabel("Email Address");
        emailAdd.setFont(new Font("Tahoma", Font.PLAIN, 14));
        emailAdd.setBounds(958, 195, 100, 20);
        mainPanel.add(emailAdd);

        emailAddValue = new JLabel("");
        emailAddValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
        emailAddValue.setBounds(958, 220, 260, 25);
        mainPanel.add(emailAddValue);

        JLabel employment = new JLabel("EMPLOYMENT & INCOME DETAILS");
        employment.setFont(new Font("Tahoma", Font.BOLD, 16));
        employment.setBounds(70, 322, 407, 20);
        mainPanel.add(employment);

        ImageIcon empLogo = new ImageIcon(informationPage.class.getResource("/imgs/company.png"));
        JLabel employmentLogo = new JLabel(empLogo);
        employmentLogo.setBounds(35, 315, 30, 30);
        mainPanel.add(employmentLogo);

        JLabel occupation = new JLabel("Occupation");
        occupation.setFont(new Font("Tahoma", Font.PLAIN, 14));
        occupation.setBounds(40, 352, 85, 20);
        mainPanel.add(occupation);

        occupationValue = new JLabel("");
        occupationValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
        occupationValue.setBounds(40, 380, 290, 20);
        mainPanel.add(occupationValue);

        JLabel company = new JLabel("Name of Company");
        company.setFont(new Font("Tahoma", Font.PLAIN, 14));
        company.setBounds(392, 352, 145, 20);
        mainPanel.add(company);

        companyValue = new JLabel("");
        companyValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
        companyValue.setBounds(392, 380, 200, 20);
        mainPanel.add(companyValue);

        JLabel monthlyIncome = new JLabel("Monthly Income");
        monthlyIncome.setFont(new Font("Tahoma", Font.PLAIN, 14));
        monthlyIncome.setBounds(670, 352, 145, 20);
        mainPanel.add(monthlyIncome);

        monthlyValue = new JLabel("");
        monthlyValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
        monthlyValue.setBounds(670, 380, 145, 20);
        mainPanel.add(monthlyValue);

        JLabel annualIncome = new JLabel("Annual Income");
        annualIncome.setFont(new Font("Tahoma", Font.PLAIN, 14));
        annualIncome.setBounds(958, 352, 145, 20);
        mainPanel.add(annualIncome);

        annualValue = new JLabel("");
        annualValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
        annualValue.setBounds(958, 380, 145, 20);
        mainPanel.add(annualValue);

        JPanel hrLine3 = new JPanel();
        hrLine3.setBackground(new Color(104, 104, 104));
        hrLine3.setBounds(25, 425, 1250, 2);
        mainPanel.add(hrLine3);

        JLabel household = new JLabel("HOUSEHOLD MEMBERS");
        household.setFont(new Font("Tahoma", Font.BOLD, 16));
        household.setBounds(75, 442, 407, 20);
        mainPanel.add(household);

        ImageIcon hmLogo = new ImageIcon(informationPage.class.getResource("/imgs/home.png"));
        JLabel houseLogo = new JLabel(hmLogo);
        houseLogo.setBounds(35, 435, 30, 30);
        mainPanel.add(houseLogo);

        JPanel line = new JPanel();
        line.setBackground(new Color(0, 0, 0));
        line.setBounds(40, 535, 2, 216);
        mainPanel.add(line);

        editButton = new RoundedButton("Edit");
        editButton.setBackground(new Color(164, 164, 164));
        editButton.setBounds(1150, 30, 100, 30);
        editButton.setForeground(Color.BLACK);
        styleButton(editButton);
        editButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        mainPanel.add(editButton);

        editButton.addActionListener(e -> {
            addMemberPage page = new addMemberPage(applicantId, this, () -> {
                loadApplicantData(applicantId);
                setVisible(true);
                toFront();
            });
            page.setLocationRelativeTo(null);
            page.setVisible(true);
        });

        JLabel placeofBirth = new JLabel("Place of Birth");
        placeofBirth.setFont(new Font("Tahoma", Font.PLAIN, 14));
        placeofBirth.setBounds(370, 250, 151, 20);
        mainPanel.add(placeofBirth);

        placeValue = new JLabel("");
        placeValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
        placeValue.setBounds(370, 275, 185, 25);
        mainPanel.add(placeValue);

        String[] columns = {
                "Full Name", "Relationship", "Age", "Birthdate",
                "Civil Status", "Educational Attainment", "Occupation", "Monthly Income"
        };

        tableModel = new DefaultTableModel(columns, 20);
        familytable = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Component prepareEditor(TableCellEditor editor, int row, int column) {
                Component c = super.prepareEditor(editor, row, column);
                if (c instanceof JTextField) {
                    c.setBackground(new Color(238, 235, 235));
                }
                return c;
            }

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (isCellSelected(row, column)) {
                    c.setBackground(new Color(238, 235, 235));
                } else {
                    c.setBackground(new Color(238, 235, 235));
                }
                return c;
            }
        };

        HeaderRenderer headerRenderer = new HeaderRenderer();
        for (int i = 0; i < familytable.getColumnCount(); i++) {
            familytable.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        JTableHeader header = familytable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(255, 105, 180));
        header.setForeground(Color.WHITE);
        header.setOpaque(true);
        header.setBounds(39, 480, 1206, 55);
        mainPanel.add(header);

        familytable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        familytable.setRowHeight(43);
        familytable.setBounds(41, 535, 1203, 216);
        familytable.setGridColor(Color.BLACK);
        familytable.setShowGrid(true);
        familytable.setRowSelectionAllowed(false);
        familytable.setCellSelectionEnabled(false);
        familytable.setBackground(new Color(238, 235, 235));
        familytable.setSelectionBackground(familytable.getBackground());
        familytable.setSelectionForeground(familytable.getForeground());
        mainPanel.add(familytable);

        int[] columnWidths = {150, 120, 50, 100, 100, 160, 120, 100};
        for (int i = 0; i < columnWidths.length; i++) {
            familytable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        mainPanel.add(familytable);
        loadApplicantData(applicantId);
    }

    private void loadApplicantData(String applicantId) {
        try {
            Connection conn = Database.getConnection();
            String applicantQuery = "SELECT * FROM applicant_information WHERE applicant_id = ?";
            PreparedStatement applicantStmt = conn.prepareStatement(applicantQuery);
            applicantStmt.setString(1, applicantId);
            ResultSet rs = applicantStmt.executeQuery();

            if (rs.next()) {
                name.setText(rs.getString("name"));
                soloParentID.setText(rs.getString("applicant_id")); 
                String classification = rs.getString("classification");
                if (classification != null) {
                    classification = classification.replaceAll("\\s*\\(.*?\\)", "").trim();
                }
                statusValue.setText(classification != null && !classification.isEmpty() ? classification : "");
                ageValue.setText(rs.getInt("age") + " Years old");
                bdayValue.setText(rs.getString("birthdate"));
                statusValue.setText(rs.getString("civil_status"));
                sexValue.setText(rs.getString("sex"));
                educationalValue.setText(rs.getString("highest_educ_attainment"));
                placeValue.setText(rs.getString("birthplace"));
                addressValue.setText(rs.getString("address"));
                numberValue.setText(rs.getString("contact_num"));
                emailAddValue.setText(rs.getString("email_address"));
                occupationValue.setText(rs.getString("occupation"));
                companyValue.setText(rs.getString("company_name"));
                monthlyValue.setText("P " + rs.getString("monthly_income"));
                annualValue.setText("P " + rs.getString("annual_income"));
            }

            rs.close();
            applicantStmt.close();

            String familyQuery = "SELECT full_name, relationship, age, birthdate, civil_status, educational_attainment, occupation, monthly_income FROM fam_composition WHERE applicant_id = ?";
            PreparedStatement familyStmt = conn.prepareStatement(familyQuery);
            familyStmt.setString(1, applicantId);
            ResultSet famRS = familyStmt.executeQuery();

            tableModel.setRowCount(0);
            while (famRS.next()) {
                tableModel.addRow(new Object[]{
                    famRS.getString("full_name"),
                    famRS.getString("relationship"),
                    famRS.getInt("age"),
                    famRS.getString("birthdate"),
                    famRS.getString("civil_status"),
                    famRS.getString("educational_attainment"),
                    famRS.getString("occupation"),
                    famRS.getString("monthly_income")
                });
            }

            famRS.close();
            familyStmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    private JTextField textField(String text) {
        JTextField txt = new JTextField(text);
        txt.setFont(new Font("Tahoma", Font.PLAIN, 18));
        txt.setVisible(false);
        txt.setBackground(Color.WHITE);
        txt.setBorder(new LineBorder(Color.BLACK, 1));
        txt.setOpaque(true);
        return txt;
    }

    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public void showDim() {
        if (dimPanel != null) {
            dimPanel.setVisible(true);
            contentPane.setComponentZOrder(dimPanel, 0);
        }
    }

    public void hideDim() {
        if (dimPanel != null) {
            dimPanel.setVisible(false);
        }
    }
}