package recordPage;

import MainPage.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class addMemberPage extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JTextField nameBar, placeofBirthBar, ageBar, birthdateBar, addressBar, emailAddBar, contactNumberBar;
    private JTextField highestEducationalBar, occupationBar, companyNameBar, annualIncomeBar, monthlyIncomeBar, soloParentID;
    private JButton cancel_backBTN, next_saveBTN;
    private JComboBox<String> civilstatusDropdown;
    private JCheckBox maleCheckbox, femaleCheckBox;

    private int panelIndex = 0;
    private final String[] panelNames = {"applicant", "Family", "Classification"};

    private familyCompositionPanel familyCompositionPanel;
    private classificationPanel classificationPanel;

    public static void launch() {
        EventQueue.invokeLater(() -> {
            try {
                addMemberPage frame = new addMemberPage();
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public addMemberPage() {
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
		
		JPanel thridProcessPanel = new JPanel();
		thridProcessPanel.setLayout(null);
		thridProcessPanel.setBackground(new Color(189, 189, 189));
		thridProcessPanel.setBounds(795, 20, 40, 40);
		applicantPanel.add(thridProcessPanel);
		
		JLabel thirdProcess = new JLabel("3");
		thirdProcess.setHorizontalAlignment(SwingConstants.CENTER);
		thirdProcess.setForeground(Color.BLACK);
		thirdProcess.setFont(new Font("Tahoma", Font.BOLD, 16));
		thirdProcess.setBounds(5, 10, 30, 20);
		thridProcessPanel.add(thirdProcess);
		
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
        
        ExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        dispose(); 
		        if (mainPage.instance != null) {
		        	mainPage.instance.hideDim(); 
		        }
			}
		});
        
        nameBar = new JTextField(); nameBar.setBounds(260, 155, 384, 30); applicantPanel.add(nameBar);
        placeofBirthBar = new JTextField(); placeofBirthBar.setBounds(665, 155, 393, 30); applicantPanel.add(placeofBirthBar);
        ageBar = new JTextField(); ageBar.setBounds(260, 220, 75, 30); applicantPanel.add(ageBar);
        birthdateBar = new JTextField(); birthdateBar.setBounds(665, 220, 187, 30); applicantPanel.add(birthdateBar);
        addressBar = new JTextField(); addressBar.setBounds(260, 355, 384, 30); applicantPanel.add(addressBar);
        emailAddBar = new JTextField(); emailAddBar.setBounds(665, 355, 393, 30); applicantPanel.add(emailAddBar);
        contactNumberBar = new JTextField(); contactNumberBar.setBounds(260, 420, 205, 30); applicantPanel.add(contactNumberBar);
        highestEducationalBar = new JTextField(); highestEducationalBar.setBounds(260, 495, 384, 30); applicantPanel.add(highestEducationalBar);
        occupationBar = new JTextField(); occupationBar.setBounds(260, 568, 384, 30); applicantPanel.add(occupationBar);
        companyNameBar = new JTextField(); companyNameBar.setBounds(260, 630, 384, 30); applicantPanel.add(companyNameBar);
        annualIncomeBar = new JTextField(); annualIncomeBar.setBounds(665, 630, 384, 30); applicantPanel.add(annualIncomeBar);
        monthlyIncomeBar = new JTextField(); monthlyIncomeBar.setBounds(665, 568, 384, 30); applicantPanel.add(monthlyIncomeBar);
        
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

        maleCheckbox = new JCheckBox("Male"); maleCheckbox.setBounds(388, 225, 66, 21); applicantPanel.add(maleCheckbox);
        femaleCheckBox = new JCheckBox("Female"); femaleCheckBox.setBounds(470, 225, 85, 21); applicantPanel.add(femaleCheckBox);
 
        JLabel Age = new JLabel("Age:\r\n");
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
            if (panelIndex == 0) dispose();
            else {
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
        	    JOptionPane.showMessageDialog(addMemberPage.this, "Saved successfully!");
        	    dispose();
        	}
        });
        contentPane.add(next_saveBTN);
        updateButtons();
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
        if (ageBar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your age.");
            return false;
        }
        if (birthdateBar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your birthdate.");
            return false;
        }
        if (!maleCheckbox.isSelected() && !femaleCheckBox.isSelected()) {
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
        if (contactNumberBar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your contact number.");
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
        if (annualIncomeBar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your annual income.");
            return false;
        }
        if (monthlyIncomeBar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your monthly income.");
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

