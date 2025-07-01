package logInPage;

import MainPage.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import JDBC.Database;



public class loginPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField Username;
	private JPasswordField passwordField;
	private JTextField dummy;
	private JCheckBox showPass;
	private JPanel panel;
	private JLabel lblNewLabel;
	private JButton exitButton;

	public static void launch() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginPage frame = new loginPage();
					frame.setTitle("Barangay Ibaba Solo Parent");
                    frame.setResizable(false);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public loginPage() {
		setUndecorated(true);
		setTitle("Barangay Ibaba Solo Parent");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 440);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(37, 37, 37));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		ImageIcon Logo = new ImageIcon(loginPage.class.getResource("/imgs/SPLogo.png"));
        Image LogoSize = Logo.getImage().getScaledInstance(200,200, Image.SCALE_SMOOTH);
        ImageIcon resizedLogo = new ImageIcon(LogoSize);
        setIconImage(resizedLogo.getImage());

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		dummy = new JTextField();
		dummy.setBounds(0, 0, 0, 0);
		contentPane.add(dummy);
		dummy.setFocusable(true);
		dummy.requestFocusInWindow();
		
		ImageIcon brgyLogo = new ImageIcon(loginPage.class.getResource("/imgs/soloParentLogo.png"));
		JLabel BRGYLogo = new JLabel(brgyLogo);
		BRGYLogo.setBounds(50, 40, 170, 70);
		contentPane.add(BRGYLogo);
		
		ImageIcon bgLogo = new ImageIcon(loginPage.class.getResource("/imgs/bg.png"));
		JLabel BGLogo = new JLabel(bgLogo);
		BGLogo.setBounds(257, 20, 484, 423);
		contentPane.add(BGLogo);
		
		Username = new JTextField();
		Username.setFont(new Font("Tahoma", Font.PLAIN, 10));
		Username.setBackground(new Color(57, 57, 57));
		Username.setForeground(Color.GRAY);
		Username.setText("Username");
		Username.setBounds(60, 165, 154, 26);
		Username.setCaretColor(new Color(255, 255, 255));
		Username.setBorder(BorderFactory.createCompoundBorder(	
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(0, 7, 0, 0)
        ));
		contentPane.add(Username);
		Username.setColumns(10);
		
		 passwordField = new JPasswordField();
	     passwordField.setBounds(60, 200, 154, 26);
	     passwordField.setFont(new Font("Tahoma", Font.PLAIN, 10));
	     passwordField.setBackground(new Color(57, 57, 57));
	     passwordField.setForeground(Color.GRAY);
	     passwordField.setCaretColor(new Color(255, 255, 255));
	     passwordField.setText("Password");
	     passwordField.setEchoChar((char) 0);
	     passwordField.setBorder(BorderFactory.createCompoundBorder(
	    		 BorderFactory.createLineBorder(Color.GRAY, 1),
	     	BorderFactory.createEmptyBorder(0, 7, 0, 0) 
	     ));
	     
	     contentPane.add(passwordField);

		
		JLabel signInTXT = new JLabel("Sign In");
		signInTXT.setFont(new Font("Tahoma", Font.BOLD, 16));
		signInTXT.setForeground(new Color(255, 255, 255));
		signInTXT.setBounds(105, 120, 71, 26);
		contentPane.add(signInTXT);
		
		JButton btnLogo = new JButton(">");
		btnLogo.setBounds(115, 310, 45, 40);
		styleButton(btnLogo);
		contentPane.add(btnLogo);
		getRootPane().setDefaultButton(btnLogo);
		btnLogo.addActionListener(e -> handleLogin());
		
		showPass = new JCheckBox("Show password");
        showPass.setFont(new Font("Tahoma", Font.PLAIN, 10));
        showPass.setForeground(new Color(255, 255, 255));
        showPass.setBounds(57, 235, 106, 21);
        showPass.setBorderPainted(false);
        showPass.setContentAreaFilled(false);
        showPass.setFocusPainted(false);
        showPass.setOpaque(true);
        showPass.setBackground(new Color(37, 37, 37));
        showPass.addActionListener(e -> showPassword());
        Icon checkBox = new CheckBox(new Color(57, 57, 57));
        showPass.setIcon(checkBox);
        showPass.setSelectedIcon(checkBox);
        contentPane.add(showPass);
        
        panel = new JPanel();
        panel.setBackground(new Color(71, 71, 71));
        panel.setBounds(0, 0, 670, 20);
        contentPane.add(panel);
        panel.setLayout(null);
        
        lblNewLabel = new JLabel("SOLO PARENT ASSOCIATION RAISE");
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblNewLabel.setBounds(20, 0, 300, 20);
        panel.add(lblNewLabel);
        
        exitButton = new JButton("X");
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
        exitButton.setFocusPainted(false);
        exitButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        exitButton.setBackground(new Color(200, 50, 50));
        exitButton.setBounds(630, 0, 40, 20);
        panel.add(exitButton);
        
        exitButton.addActionListener(e -> dispose());
        
        Username.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (Username.getText().equals("Username")) {
                    Username.setText("");
                    Username.setForeground(new Color(255, 255, 255));
                    Username.setFont(new Font("Tahoma", Font.BOLD, 10));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (Username.getText().isEmpty()) {
                    Username.setText("Username");
                    Username.setForeground(Color.GRAY);
                }
            }
        });

        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                String input = new String(passwordField.getPassword());
                if (input.equals("Password")) {
                    passwordField.setText("");
                    passwordField.setForeground(new Color(255, 255, 255));
                    passwordField.setFont(new Font("Tahoma", Font.BOLD, 10));
                }

                if (showPass.isSelected()) {
                    passwordField.setEchoChar((char) 0); 
                } else {
                    passwordField.setEchoChar('•'); 
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String input = new String(passwordField.getPassword());
                if (input.isEmpty()) {
                    passwordField.setText("Password");
                    passwordField.setEchoChar((char) 0);
                    passwordField.setForeground(Color.GRAY);
                }
            }
        });
        
	}
	
	private void styleButton(JButton button) {
        button.setBackground(new Color(255, 64, 169));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
	
	private void handleLogin() {
	    String username = Username.getText().trim();
	    String password = new String(passwordField.getPassword()).trim();
        
        if (username.isEmpty() || username.equals("Username")) {
        	JOptionPane.showMessageDialog(this, "Please enter username.");
            return;
        }
        if (password.isEmpty() || password.equals("Password")) {
        	JOptionPane.showMessageDialog(this, "Please enter password.");
            return;
        }
        if (username.isEmpty() || password.isEmpty() || username.equals("Username") || password.equals("Password")) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.");
            return;
        }
        
	    try (var conn = Database.getConnection();
		         var stmt = conn.prepareStatement(
		             "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?")) {
		        stmt.setString(1, username);
		        stmt.setString(2, password); 
		        var rs = stmt.executeQuery();
		        if (rs.next() && rs.getInt(1) == 1) {
		            mainPage.launch();
		            dispose();
		        } else {
		            JOptionPane.showMessageDialog(this, "Invalid username or password.");
		        }
		    } catch (Exception ex) {
		        ex.printStackTrace();
		        JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
		    }
    }	
	
	private void showPassword() {
        String input = new String(passwordField.getPassword());
        boolean selected = showPass.isSelected();

        if (input.equals("Password")) {
            passwordField.setEchoChar((char) 0);
        } else {
            if (selected) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('•');
            }
        }
    }
}

class CheckBox implements Icon {
	private final Color bgColor;

    public CheckBox(Color bgColor) {
    	this.bgColor = bgColor;
    }
    
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
    	AbstractButton button = (AbstractButton) c;
        ButtonModel model = button.getModel();
        Graphics2D g2 = (Graphics2D) g.create();

        int size = getIconWidth();

        if (model.isSelected()) {
            g2.setColor(new Color(255, 64, 169));
            g2.fillRect(x + 1, y + 1, size - 2, size - 2);
        }

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(x + 1, y + 1, size - 2, size - 2);

        if (model.isSelected()) {
            g2.setColor(bgColor);
        } else {
            g2.setColor(new Color(37,37,37));
        }
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(x + 4, y + 8, x + 7, y + 11);
        g2.drawLine(x + 7, y + 11, x + 12, y + 4);

        g2.dispose();
    }
    
    @Override
    public int getIconWidth() {
        return 16;
    }

    @Override
    public int getIconHeight() {
        return 16;
    }
}