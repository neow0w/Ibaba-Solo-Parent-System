package recordPage;

import MainPage.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class informationPage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable familytable;
    private DefaultTableModel tableModel;
    private JTextField nameBar, placeofBirthBar, ageBar, birthdateBar, addressBar, emailAddBar, contactNumberBar;
    private JTextField highestEducationalBar, occupationBar, companyNameBar, annualIncomeBar, monthlyIncomeBar, soloParentIDBar;
    private JTextField marriedAbandonedBar, marriedAnnulledBar, othersBar;
    private JComboBox<String> civilstatusDropdown, sexDropdown, classificationDropdown;
    private JLabel ageValue, bdayValue, educationalValue, addressValue, numberValue, emailAddValue, occupationValue, companyValue;
    private JLabel monthlyValue, annualValue, placeValue, status, statusValue, sexValue, name, soloParentID;
    
    private boolean isEditMode = false;
    private JButton editButton;
    private static final String[] REQUIRES_DETAILS = {"Married, Abandoned", "Married, Annulled", "Others"};

    public static void launch() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    informationPage frame = new informationPage();
                    frame.setResizable(false);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public informationPage() {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1300, 800);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(238, 235, 235));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
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
        
        JLabel memberInfo = new JLabel("MEMBER INFORMATION");
        memberInfo.setForeground(new Color(238, 235, 235));
        memberInfo.setFont(new Font("Tahoma", Font.BOLD, 16));
        memberInfo.setBounds(20, 5, 205, 20);
        topPanel.add(memberInfo);
        
        exitBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean willOpenAddPage = false; 

                dispose();

                if (mainPage.instance != null && !willOpenAddPage) {
                	mainPage.instance.hideDim();
                }
            }
        });
        
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(0, 28, 1300, 793);
        mainPanel.setBackground(new Color(238, 235, 235));
        contentPane.add(mainPanel);
        mainPanel.setLayout(null);
        
        ImageIcon iconLogo = new ImageIcon(informationPage.class.getResource("/imgs/icon.png"));
        JLabel icon = new JLabel(iconLogo);
        icon.setBounds(40, 20, 120, 110);
        mainPanel.add(icon);
        
        name = new JLabel("Jana Agustin");
        name.setFont(new Font("Tahoma", Font.BOLD, 24));
        name.setBounds(175, 20, 440, 35);
        mainPanel.add(name);
        
        nameBar = textField("Jana Agustin");
        nameBar.setFont(new Font("Tahoma", Font.BOLD, 24));
        nameBar.setBounds(175, 20, 440, 35);
        mainPanel.add(nameBar);
        
        soloParentID = new JLabel("CH-0000-0000-0000");
        soloParentID.setFont(new Font("Tahoma", Font.PLAIN, 20));
        soloParentID.setBounds(175, 65, 200, 20);
        mainPanel.add(soloParentID);
        
        soloParentIDBar = textField("CH-0000-0000-0000");
        soloParentIDBar.setFont(new Font("Tahoma", Font.PLAIN, 20));
        soloParentIDBar.setBounds(175, 65, 200, 20);
        mainPanel.add(soloParentIDBar);
        
        status = new JLabel("Married, Widow/er");
        status.setFont(new Font("Tahoma", Font.PLAIN, 20));
        status.setBounds(175, 95, 192, 30);
        mainPanel.add(status);
        
        String[] classificationOption = { "Unmarried, Abandoned", "Unmarried, Separated", "Unmarried, Death of Partner", "Married, Abandoned", 
                                        "Married, Annulled", "Married, Separation de Facto", "Married, Legally Separated", "Married, Divorced",
                                        "Married, Widow/er", "Others"};
        
        classificationDropdown = createDropdown(classificationOption, "Married, Widow/er");
        classificationDropdown.setFont(new Font("Tahoma", Font.PLAIN, 20));
        classificationDropdown.setBounds(175, 95, 300, 30);
        mainPanel.add(classificationDropdown);
        
        marriedAbandonedBar = textField("Petsa ng Kasal");
        marriedAbandonedBar.setFont(new Font("Tahoma", Font.ITALIC, 18));
        marriedAbandonedBar.setForeground(Color.GRAY);
        marriedAbandonedBar.setBounds(175, 130, 200, 20);
        mainPanel.add(marriedAbandonedBar);
        
        marriedAbandonedBar.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (marriedAbandonedBar.getText().equals("Petsa ng Kasal")) {
                    marriedAbandonedBar.setText("");
                    marriedAbandonedBar.setForeground(Color.BLACK);
                    marriedAbandonedBar.setFont(new Font("Tahoma", Font.PLAIN, 18));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (marriedAbandonedBar.getText().isEmpty()) {
                    marriedAbandonedBar.setText("Petsa ng Kasal");
                    marriedAbandonedBar.setFont(new Font("Tahoma", Font.ITALIC, 18));
                    marriedAbandonedBar.setForeground(Color.GRAY);
                }
            }
        });
        
        marriedAnnulledBar = textField("Petsa ng Paghihiwalay");
        marriedAnnulledBar.setFont(new Font("Tahoma", Font.ITALIC, 18));
        marriedAnnulledBar.setForeground(Color.GRAY);
        marriedAnnulledBar.setBounds(175, 130, 200, 20);
        mainPanel.add(marriedAnnulledBar);
        
        marriedAnnulledBar.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (marriedAnnulledBar.getText().equals("Petsa ng Paghihiwalay")) {
                    marriedAnnulledBar.setText("");
                    marriedAnnulledBar.setForeground(Color.BLACK);
                    marriedAnnulledBar.setFont(new Font("Tahoma", Font.PLAIN, 18));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (marriedAnnulledBar.getText().isEmpty()) {
                    marriedAnnulledBar.setText("Petsa ng Paghihiwalay");
                    marriedAnnulledBar.setFont(new Font("Tahoma", Font.ITALIC, 18));
                    marriedAnnulledBar.setForeground(Color.GRAY);
                }
            }
        });
        
        othersBar = textField("Enter details");
        othersBar.setFont(new Font("Tahoma", Font.ITALIC, 18));
        othersBar.setForeground(Color.GRAY);
        othersBar.setBounds(175, 130, 200, 20);
        mainPanel.add(othersBar);
        
        othersBar.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (othersBar.getText().equals("Enter details")) {
                    othersBar.setText("");
                    othersBar.setForeground(Color.BLACK);
                    othersBar.setFont(new Font("Tahoma", Font.PLAIN, 18));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (othersBar.getText().isEmpty()) {
                    othersBar.setText("Enter details");
                    othersBar.setFont(new Font("Tahoma", Font.ITALIC, 18));
                    othersBar.setForeground(Color.GRAY);
                }
            }
        });
        
        classificationDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateClassificationDetailsVisibility();
            }
        });
        
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
        
        ageValue = new JLabel("30 Years old");
        ageValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
        ageValue.setBounds(40, 220, 121, 25);
        mainPanel.add(ageValue);
        
        ageBar = textField("30 Years old");
        ageBar.setBounds(40, 220, 121, 25);
        mainPanel.add(ageBar);
        
        JLabel birthday = new JLabel("Birthday");
        birthday.setFont(new Font("Tahoma", Font.PLAIN, 14));
        birthday.setBounds(40, 250, 85, 20);
        mainPanel.add(birthday);
        
        bdayValue = new JLabel("December 29, 2004");
        bdayValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
        bdayValue.setBounds(40, 275, 185, 25);
        mainPanel.add(bdayValue);
        
        birthdateBar = textField("December 29, 2004");
        birthdateBar.setBounds(40, 275, 185, 25);
        mainPanel.add(birthdateBar);
        
        JLabel civilStatus = new JLabel("Civil Status");
        civilStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));
        civilStatus.setBounds(240, 195, 90, 20);
        mainPanel.add(civilStatus);
        
        statusValue = new JLabel("Married");
        statusValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
        statusValue.setBounds(240, 220, 110, 25);
        mainPanel.add(statusValue);
        
        String[] civilStatusOption = { "Single", "Married" };
        
        civilstatusDropdown = createDropdown(civilStatusOption, "Married");
        civilstatusDropdown.setFont(new Font("Tahoma", Font.PLAIN, 18));
        civilstatusDropdown.setBounds(240, 220, 110, 25);
        mainPanel.add(civilstatusDropdown);
        
        JLabel sex = new JLabel("Sex");
        sex.setFont(new Font("Tahoma", Font.PLAIN, 14));
        sex.setBounds(240, 250, 90, 20);
        mainPanel.add(sex);
        
        sexValue = new JLabel("Female");
        sexValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
        sexValue.setBounds(240, 275, 110, 25);
        mainPanel.add(sexValue);
        
        String[] sexOption = { "Male", "Female" };
        
        sexDropdown = createDropdown(sexOption, "Female");
        sexDropdown.setFont(new Font("Tahoma", Font.PLAIN, 18));
        sexDropdown.setBounds(240, 275, 110, 25);
        mainPanel.add(sexDropdown);
        
        JLabel educationalBG = new JLabel("Education Background");
        educationalBG.setFont(new Font("Tahoma", Font.PLAIN, 14));
        educationalBG.setBounds(370, 195, 151, 20);
        mainPanel.add(educationalBG);
        
        educationalValue = new JLabel("College Graduate");
        educationalValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
        educationalValue.setBounds(370, 220, 185, 25);
        mainPanel.add(educationalValue);
        
        highestEducationalBar = textField("College Graduate");
        highestEducationalBar.setBounds(370, 220, 185, 25);
        mainPanel.add(highestEducationalBar);
        
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
        
        addressValue = new JLabel("23 Example Street, BRGY. Ibaba");
        addressValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
        addressValue.setBounds(590, 220, 325, 25);
        mainPanel.add(addressValue);
        
        addressBar = textField("23 Example Street, BRGY. Ibaba");
        addressBar.setBounds(590, 220, 325, 25);
        mainPanel.add(addressBar);
        
        JLabel mobileNo = new JLabel("Mobile Number");
        mobileNo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        mobileNo.setBounds(590, 250, 110, 20);
        mainPanel.add(mobileNo);
        
        numberValue = new JLabel("0917-0000-0000");
        numberValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
        numberValue.setBounds(590, 275, 160, 25);
        mainPanel.add(numberValue);
        
        contactNumberBar = textField("0917-0000-0000");
        contactNumberBar.setBounds(590, 275, 160, 25);
        mainPanel.add(contactNumberBar);
        
        JLabel emailAdd = new JLabel("Email Address");
        emailAdd.setFont(new Font("Tahoma", Font.PLAIN, 14));
        emailAdd.setBounds(958, 195, 100, 20);
        mainPanel.add(emailAdd);
        
        emailAddValue = new JLabel("janabeatrice@gmail.com");
        emailAddValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
        emailAddValue.setBounds(958, 220, 260, 25);
        mainPanel.add(emailAddValue);
        
        emailAddBar = textField("janabeatrice@gmail.com");
        emailAddBar.setBounds(958, 220, 260, 25);
        mainPanel.add(emailAddBar);
        
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
        
        occupationValue = new JLabel("Web Developer");
        occupationValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
        occupationValue.setBounds(40, 380, 290, 20);
        mainPanel.add(occupationValue);
        
        occupationBar = textField("Web Developer");
        occupationBar.setBounds(40, 380, 290, 20);
        mainPanel.add(occupationBar);
        
        JLabel company = new JLabel("Name of Company");
        company.setFont(new Font("Tahoma", Font.PLAIN, 14));
        company.setBounds(392, 352, 145, 20);
        mainPanel.add(company);
        
        companyValue = new JLabel("Socia");
        companyValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
        companyValue.setBounds(392, 380, 200, 20);
        mainPanel.add(companyValue);
        
        companyNameBar = textField("Socia");
        companyNameBar.setBounds(392, 380, 200, 20);
        mainPanel.add(companyNameBar);
        
        JLabel monthlyIncome = new JLabel("Monthly Income");
        monthlyIncome.setFont(new Font("Tahoma", Font.PLAIN, 14));
        monthlyIncome.setBounds(670, 352, 145, 20);
        mainPanel.add(monthlyIncome);
        
        monthlyValue = new JLabel("P 48,000");
        monthlyValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
        monthlyValue.setBounds(670, 380, 145, 20);
        mainPanel.add(monthlyValue);
        
        monthlyIncomeBar = textField("P 48,000");
        monthlyIncomeBar.setBounds(670, 380, 145, 20);
        mainPanel.add(monthlyIncomeBar);
        
        JLabel annualIncome = new JLabel("Annual Income");
        annualIncome.setFont(new Font("Tahoma", Font.PLAIN, 14));
        annualIncome.setBounds(958, 352, 145, 20);
        mainPanel.add(annualIncome);
        
        annualValue = new JLabel("P 100,000");
        annualValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
        annualValue.setBounds(958, 380, 145, 20);
        mainPanel.add(annualValue);
        
        annualIncomeBar = textField("P 100,000");
        annualIncomeBar.setBounds(958, 380, 145, 20);
        mainPanel.add(annualIncomeBar);
        
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

        editButton.addActionListener(e -> toggleEditMode());
        
        JLabel placeofBirth = new JLabel("Place of Birth");
        placeofBirth.setFont(new Font("Tahoma", Font.PLAIN, 14));
        placeofBirth.setBounds(370, 250, 151, 20);
        mainPanel.add(placeofBirth);
        
        placeValue = new JLabel("Laguna");
        placeValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
        placeValue.setBounds(370, 275, 185, 25);
        mainPanel.add(placeValue);
        
        placeofBirthBar = textField("Laguna");
        placeofBirthBar.setBounds(370, 275, 185, 25);
        mainPanel.add(placeofBirthBar);
        
        String[] columns = {
                "Full Name", "Relationship", "Age", "Birthdate",
                "Civil Status", "Educational Attainment", "Occupation", "Monthly Income"
        };

        tableModel = new DefaultTableModel(columns, 17);
        familytable = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
                return isEditMode;
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
        familytable.setBackground(new Color (238, 235, 235));
        familytable.setSelectionBackground(familytable.getBackground());
        familytable.setSelectionForeground(familytable.getForeground());
        mainPanel.add(familytable);

        int[] columnWidths = {150, 120, 50, 100, 100, 160, 120, 100};
        for (int i = 0; i < columnWidths.length; i++) {
            familytable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        mainPanel.add(familytable);
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

    private void toggle(JComponent label, JComponent field) {
        if (isEditMode) {
            label.setVisible(false);
            field.setVisible(true);
        } else {
            label.setVisible(true);
            field.setVisible(false);
        }
    }
    
    private void updateClassificationDetailsVisibility() {
        String selected = (String) classificationDropdown.getSelectedItem();
        marriedAbandonedBar.setVisible(isEditMode && selected.equals("Married, Abandoned"));
        marriedAnnulledBar.setVisible(isEditMode && selected.equals("Married, Annulled"));
        othersBar.setVisible(isEditMode && selected.equals("Others"));
    }
    
    private boolean checkEmptyFields() {
        String selected = (String) classificationDropdown.getSelectedItem();
        if (isEditMode) {
            if (selected.equals("Married, Abandoned") && 
                (marriedAbandonedBar.getText().isEmpty() || marriedAbandonedBar.getText().equals("Petsa ng Kasal"))) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter details for Married, Abandoned.", 
                    "Missing Information", 
                    JOptionPane.WARNING_MESSAGE);
                return false;
            }
            if (selected.equals("Married, Annulled") && 
                (marriedAnnulledBar.getText().isEmpty() || marriedAnnulledBar.getText().equals("Petsa ng Paghihiwalay"))) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter details for Married, Annulled.", 
                    "Missing Information", 
                    JOptionPane.WARNING_MESSAGE);
                return false;
            }
            if (selected.equals("Others") && 
                (othersBar.getText().isEmpty() || othersBar.getText().equals("Enter details"))) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter details for Others.", 
                    "Missing Information", 
                    JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }
        return true;
    }
    
    private void toggleEditMode() {
        if (isEditMode) {
            if (!checkEmptyFields()) {
                return; 
            }
        }
        
        isEditMode = !isEditMode;
        if (isEditMode) {
            editButton.setText("Save");
            editButton.setForeground(Color.WHITE);
            editButton.setBackground(new Color(200, 50, 50));
        } else {
            editButton.setText("Edit");
            editButton.setForeground(Color.BLACK);
            editButton.setBackground(new Color(164, 164, 164));
        }
        
        toggle(name, nameBar);
        toggle(soloParentID, soloParentIDBar);
        toggle(ageValue, ageBar);
        toggle(bdayValue, birthdateBar);
        toggle(educationalValue, highestEducationalBar);
        toggle(placeValue, placeofBirthBar);
        toggle(addressValue, addressBar);
        toggle(numberValue, contactNumberBar);
        toggle(emailAddValue, emailAddBar);
        toggle(occupationValue, occupationBar);
        toggle(companyValue, companyNameBar);
        toggle(monthlyValue, monthlyIncomeBar);
        toggle(annualValue, annualIncomeBar);
        toggle(status, classificationDropdown);
        toggle(sexValue, sexDropdown);
        toggle(statusValue, civilstatusDropdown);
        
        updateClassificationDetailsVisibility();
        
        familytable.repaint();
    }
    
    private JComboBox<String> createDropdown(String[] options, String selected) {
        JComboBox<String> combo = new JComboBox<>(options);
        combo.setVisible(false);
        combo.setSelectedItem(selected);
        combo.setBackground(new Color(238, 235, 235));
        combo.setBorder(null);
        combo.setOpaque(false);
        combo.setFocusable(false);

        combo.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = new JButton("▼");
                button.setBorder(null);
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setFocusable(false);
                return button;
            }
        });

        return combo;
    }

    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}