package MainPage;

import adminPage.contactPage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class adminPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public adminPanel() {
		setLayout(null);
		
		JLabel support = new JLabel("SUPPORT");
		support.setFont(new Font("Tahoma", Font.BOLD, 28));
		support.setBounds(90, 23, 140, 30);
		add(support);
		
		JLabel supportDescription = new JLabel("Use this section to update your password and contact support.");
		supportDescription.setFont(new Font("Tahoma", Font.PLAIN, 16));
		supportDescription.setBounds(90, 60, 480, 20);
		add(supportDescription);
		
		JPanel hrLine1 = new JPanel();
		hrLine1.setBackground(new Color(52, 52, 52));
		hrLine1.setBounds(20, 100, 1300, 2);
		add(hrLine1);
		
		JPanel changePassPanel = new RoundedPanel();
		changePassPanel.setBounds(21, 110, 559, 570);
		add(changePassPanel);
		changePassPanel.setLayout(null);
		
		JLabel updatePass = new JLabel("UPDATE PASSWORD");
		updatePass.setFont(new Font("Tahoma", Font.BOLD, 26));
		updatePass.setBounds(130, 100, 290, 30);
		changePassPanel.add(updatePass);
		
		JLabel updatePassDesc = new JLabel("Update your password to keep your account secure");
		updatePassDesc.setFont(new Font("Tahoma", Font.PLAIN, 16));
		updatePassDesc.setBounds(85, 140, 380, 20);
		changePassPanel.add(updatePassDesc);
		
		JPasswordField currentPasswordField = new JPasswordField();
		JPanel currentPanel = createPasswordFieldWithToggle(currentPasswordField);
		currentPanel.setBounds(85, 225, 380, 40);
		changePassPanel.add(currentPanel);
		
		JLabel current = new JLabel("CURRENT PASSWORD");
		current.setFont(new Font("Tahoma", Font.BOLD, 14));
		current.setBounds(85, 200, 200, 20);
		changePassPanel.add(current);
		
		JPasswordField newPasswordField = new JPasswordField();
		JPanel newPanel = createPasswordFieldWithToggle(newPasswordField);
		newPanel.setBounds(85, 305, 380, 40);
		changePassPanel.add(newPanel);

		JLabel newPassword = new JLabel("NEW PASSWORD");
		newPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		newPassword.setBounds(85, 280, 200, 20);
		changePassPanel.add(newPassword);
		
		JPasswordField confirmPasswordField = new JPasswordField();
		JPanel confirmPanel = createPasswordFieldWithToggle(confirmPasswordField);
		confirmPanel.setBounds(85, 385, 380, 40);
		changePassPanel.add(confirmPanel);
		
		JLabel confirm = new JLabel("CONFIRM PASSWORD");
		confirm.setFont(new Font("Tahoma", Font.BOLD, 14));
		confirm.setBounds(85, 360, 200, 20);
		changePassPanel.add(confirm);
		
		String currentPassword = "1234";
				
		JButton setPassBTN = new RoundedButton("SET NEW PASSWORD");
		setPassBTN.setBounds(85, 500, 380, 40);
		setPassBTN.setBackground(new Color(52, 52, 52));
		setPassBTN.setForeground(new Color(255, 64, 169));
		setPassBTN.setFont(new Font("Segoe UI", Font.BOLD, 18));
		changePassPanel.add(setPassBTN);
		
		setPassBTN.addActionListener(e -> {
			if (currentPasswordField == null || newPasswordField == null || confirmPasswordField == null) {
		        JOptionPane.showMessageDialog(null, "Password fields are not initialized.", "Error", JOptionPane.ERROR_MESSAGE);
		        return;
		    }
			
			String currentPass = new String(currentPasswordField.getPassword()).trim();
		    String newPass = new String(newPasswordField.getPassword()).trim();
		    String confirmPass = new String(confirmPasswordField.getPassword()).trim();

		    if (newPass.isEmpty() || confirmPass.isEmpty() || currentPass.isEmpty()) {
		        JOptionPane.showMessageDialog(null, "Password fields cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
		    } else if (!currentPass.equals(currentPassword)) {
		        JOptionPane.showMessageDialog(null, "Current password is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
		    } else if (!newPass.equals(confirmPass)) {
		        JOptionPane.showMessageDialog(null, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
		    } else {
		        JOptionPane.showMessageDialog(null, "Password updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
		    }
		});
		
		
		ImageIcon changePassLogo = new ImageIcon(adminPanel.class.getResource("/imgs/changePass.png"));
		JLabel changePass = new JLabel(changePassLogo);
		changePass.setBounds(230, 20, 60, 60);
		changePassPanel.add(changePass);
		
		
		JPanel FAQPanel = new RoundedPanel();
		FAQPanel.setBounds(590, 112, 730, 568);
		add(FAQPanel);
		FAQPanel.setLayout(null);
		
		ImageIcon faqLogo = new ImageIcon(adminPanel.class.getResource("/imgs/FAQLogo.png"));
		JLabel FAQLogo = new JLabel(faqLogo);
		FAQLogo.setBounds(20, 15, 50, 50);
		FAQPanel.add(FAQLogo);
		
		JLabel FrequentlyAsked = new JLabel("FREQUENTLY ASKED QUESTIONS");
		FrequentlyAsked.setFont(new Font("Tahoma", Font.BOLD, 22));
		FrequentlyAsked.setBounds(90, 15, 450, 25);
		FAQPanel.add(FrequentlyAsked);
		
		JLabel faqDescription = new JLabel("Helping you understand the RAISE system better");
		faqDescription.setFont(new Font("Tahoma", Font.PLAIN, 14));
		faqDescription.setBounds(90, 45, 400, 20);
		FAQPanel.add(faqDescription);
		
		JPanel faqPanel1 = new RoundedPanel();
		faqPanel1.setBackground(new Color(238, 235, 235));
		faqPanel1.setBounds(20, 80, 690, 475);
		FAQPanel.add(faqPanel1);
		faqPanel1.setLayout(null);
		
		JLabel Q1 = new JLabel("Q1: The member/activity I added isn't showing up in the list.");
		Q1.setFont(new Font("Tahoma", Font.BOLD, 18));
		Q1.setBounds(20, 15, 640, 20);
		faqPanel1.add(Q1);
		
		JLabel Q1_A = new JLabel("A: Try restarting the program or existing and reopening it. This will refresh the member");
		Q1_A.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Q1_A.setBounds(20, 45, 650, 20);
		faqPanel1.add(Q1_A);
		
		JLabel Q1_A1 = new JLabel("and activity list display.");
		Q1_A1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Q1_A1.setBounds(20, 70, 650, 20);
		faqPanel1.add(Q1_A1);
		
		JPanel line1 = new JPanel();
		line1.setBackground(new Color(0, 0, 0));
		line1.setBounds(20, 105, 650, 2);
		faqPanel1.add(line1);
		
		JLabel Q2 = new JLabel("Q2: I can't find a specific member.");
		Q2.setFont(new Font("Tahoma", Font.BOLD, 18));
		Q2.setBounds(20, 120, 640, 20);
		faqPanel1.add(Q2);
		
		JLabel Q2_A = new JLabel("A: Use the search bar and double-check for spelling errors or typos in the name. Partial name searched are supported.");
		Q2_A.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Q2_A.setBounds(20, 150, 650, 20);
		faqPanel1.add(Q2_A);
		
		JLabel Q2_A1 = new JLabel("name searched are supported.");
		Q2_A1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Q2_A1.setBounds(20, 175, 650, 20);
		faqPanel1.add(Q2_A1);
		
		JPanel line2 = new JPanel();
		line2.setBackground(Color.BLACK);
		line2.setBounds(20, 210, 650, 2);
		faqPanel1.add(line2);
		
		JLabel Q3 = new JLabel("Q3: Some member information is incorrect.");
		Q3.setFont(new Font("Tahoma", Font.BOLD, 18));
		Q3.setBounds(20, 225, 640, 20);
		faqPanel1.add(Q3);
		
		JLabel Q3_A = new JLabel("A: You can edit member details from the Members List. Click the edit button on the top");
		Q3_A.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Q3_A.setBounds(20, 255, 650, 20);
		faqPanel1.add(Q3_A);
		
		JLabel Q3_A1 = new JLabel("right of the member's profile, make the changes, then click Save in the same location.");
		Q3_A1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Q3_A1.setBounds(20, 280, 650, 20);
		faqPanel1.add(Q3_A1);
		
		JPanel line3 = new JPanel();
		line3.setBackground(Color.BLACK);
		line3.setBounds(20, 315, 650, 2);
		faqPanel1.add(line3);
		
		JLabel Q4 = new JLabel("Q4: I want to add an activity in a specific date.");
		Q4.setFont(new Font("Tahoma", Font.BOLD, 18));
		Q4.setBounds(20, 330, 640, 20);
		faqPanel1.add(Q4);
		
		JLabel Q4_A = new JLabel("A: Use the Go to Date and type the date you want to add an activity. Make sure to follow");
		Q4_A.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Q4_A.setBounds(20, 360, 650, 20);
		faqPanel1.add(Q4_A);
		
		JLabel Q4_A1 = new JLabel("the format given in the Go to Date bar. ");
		Q4_A1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Q4_A1.setBounds(20, 385, 650, 20);
		faqPanel1.add(Q4_A1);
		
		JPanel line4 = new JPanel();
		line4.setBackground(Color.BLACK);
		line4.setBounds(20, 415, 650, 2);
		faqPanel1.add(line4);
		
		JLabel Q4_A_1 = new JLabel("If problems persist, please contact the development team using");
		Q4_A_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Q4_A_1.setBounds(120, 420, 500, 20);
		faqPanel1.add(Q4_A_1);
		
		JLabel Q4_A1_1 = new JLabel("the contact information provided ");
		Q4_A1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Q4_A1_1.setBounds(180, 445, 250, 20);
		faqPanel1.add(Q4_A1_1);
		
		JButton contactBTN = new JButton("here.");
		contactBTN.setHorizontalAlignment(SwingConstants.LEFT);
		contactBTN.setBounds(413, 445, 75, 20);
		faqPanel1.add(contactBTN);
		styleButton(contactBTN);
		
		contactBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contactPage contact = new contactPage();
				mainPage.instance.showDim();
				contact.setLocationRelativeTo(null);
				contact.setVisible(true);
			}
		});
		
        ImageIcon supportLogo = new ImageIcon(adminPanel.class.getResource("/imgs/support.png"));
		JLabel supportIMG = new JLabel(supportLogo);
		supportIMG.setBounds(25, 25, 50, 50);
		add(supportIMG);
		
	}
	
	private void styleButton(JButton button) {
        button.setBackground(new Color(238, 235, 235));	
        button.setForeground(new Color(81, 81, 255));
        button.setFocusPainted(false);
        button.setFont(new Font("Tahoma", Font.BOLD, 16));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
	
	private JPanel createPasswordFieldWithToggle(JPasswordField passwordField) {
		JPanel container = new JPanel(null);
	    container.setBounds(85, 0, 380, 40);
	    container.setBackground(Color.WHITE);

	    passwordField.setBounds(0, 0, 340, 40);
	    passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
	    passwordField.setEchoChar('•');
	    passwordField.setBackground(new Color(255, 255, 255));
	    passwordField.setBorder(null); 
	    passwordField.setOpaque(true);
	    container.add(passwordField);
	    
	    ImageIcon hidePass = new ImageIcon(adminPanel.class.getResource("/imgs/hidePass.png"));
	    ImageIcon showPass = new ImageIcon(adminPanel.class.getResource("/imgs/showPass.png"));
	    
	    JButton toggle = new JButton(hidePass);

	    toggle.setBounds(340, 0, 40, 40);
	    toggle.setFont(new Font("Tahoma", Font.BOLD, 16));
	    toggle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    toggle.setBackground(new Color(255, 255, 255));
	    toggle.setFocusPainted(false);
	    toggle.setBorder(null); 
	    toggle.setOpaque(true);
	    container.add(toggle);

	    final boolean[] isShown = {false};
	    toggle.addActionListener(e -> {
	        isShown[0] = !isShown[0];
	        passwordField.setEchoChar(isShown[0] ? (char) 0 : '•');
	        toggle.setIcon(isShown[0] ? showPass : hidePass);
	    });

	    return container;
	}
}
