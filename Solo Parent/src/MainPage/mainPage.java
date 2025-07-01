package MainPage;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class mainPage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JButton selectedButton = null;
    private JPanel contentPane;
    private JPanel adminBtnPanel;
    private JPanel dimPanel;
    public static mainPage instance;

    public static void launch() {
        EventQueue.invokeLater(() -> {
            try {
            	mainPage frame = new mainPage();
                frame.setTitle("Barangay Ibaba Solo Parent");
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public mainPage() {
    	instance = this;
    	setTitle("Barangay Ibaba Solo Parent");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1350, 820);
        
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1360, 830);
        setContentPane(layeredPane);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBounds(0, 0, 1360, 830);
        contentPane.setLayout(null);
        layeredPane.add(contentPane, JLayeredPane.DEFAULT_LAYER);
        
        ImageIcon logo = new ImageIcon(mainPage.class.getResource("/imgs/SPLogo.png"));
        Image logoSize = logo.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon resizedLogo = new ImageIcon(logoSize);
        setIconImage(resizedLogo.getImage());

        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(0, 92, 1350, 800);
        mainPanel.setLayout(new CardLayout());
        contentPane.add(mainPanel);

        JPanel topbar = new JPanel();	
        topbar.setBackground(new Color(52, 52, 52));
        topbar.setBounds(0, 0, 1350, 93);
        contentPane.add(topbar);
        topbar.setLayout(null);

        JLabel headerText = new JLabel("Magandang Araw, Ka Baranggay!");
        headerText.setFont(new Font("Tahoma", Font.BOLD, 24));
        headerText.setBounds(285, 14, 410, 40);
        headerText.setForeground(new Color(255, 64, 169));
        topbar.add(headerText);

        JLabel headerSubText = new JLabel("Isang barangay, Isang komunidad, Sama-sama sa kaunlaran");
        headerSubText.setFont(new Font("Tahoma", Font.BOLD, 10));
        headerSubText.setBounds(286, 45, 373, 28);
        headerSubText.setForeground(new Color(255, 64, 169));
        topbar.add(headerSubText);

        JPanel verLine = new JPanel();
        verLine.setBounds(265, 17, 2, 60);
        topbar.add(verLine);

        JPanel verLine_1 = new JPanel();
        verLine_1.setBounds(700, 17, 2, 60);
        topbar.add(verLine_1);

        ImageIcon sParentLogo = new ImageIcon(mainPage.class.getResource("/imgs/ibabaTXT.png"));
        JLabel spLogo = new JLabel(sParentLogo);
        spLogo.setBounds(10, 5, 250, 85);
        topbar.add(spLogo);

        JButton btnHome = topBarBTN("Home", 760, 95, mainPanel, "home");
        JButton btnRecords = topBarBTN("Records", 905, 115, mainPanel, "records");
        JButton btnPlanner = topBarBTN("Planner", 1070, 109, mainPanel, "planner");

        topbar.add(btnHome);
        topbar.add(btnRecords);
        topbar.add(btnPlanner);

        ArrayList<JButton> topbarButtons = new ArrayList<>();
        topbarButtons.add(btnHome);
        topbarButtons.add(btnRecords);
        topbarButtons.add(btnPlanner);

        selectButton(btnHome);

        ImageIcon adminRawIcon = new ImageIcon(mainPage.class.getResource("/imgs/adminLogo.png"));
        JPanel adminButtonPanel = createAdminButton(mainPanel, "admin", adminRawIcon);
        topbar.add(adminButtonPanel);

        homePage homePage_ = new homePage();
        mainPanel.add(homePage_, "home");
        mainPanel.add(new recordPage(), "records");
        mainPanel.add(new plannerPage(), "planner");
        mainPanel.add(new adminPanel(), "admin");

        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        cl.show(mainPanel, "home");
        
        dimPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0, 0, 0, 130));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        dimPanel.setOpaque(false);
        dimPanel.setBounds(0, 0, 1360, 830);
        dimPanel.setVisible(false);

        layeredPane.add(dimPanel, JLayeredPane.PALETTE_LAYER);
        
    }
    
    public void showDim() {
        dimPanel.setVisible(true);
    }

    public void hideDim() {
        dimPanel.setVisible(false);
    }

    private JButton topBarBTN(String text, int xPosition, int width, JPanel mainPanel, String cardName) {
        JButton button = new JButton(text);

        button.setBounds(xPosition, 27, width, 40);
        button.setFont(new Font("", Font.PLAIN, 20));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBackground(new Color(52, 52, 52));
        button.setForeground(Color.WHITE);

        button.addActionListener(e -> {
            selectButton(button);
            if (cardName != null && !cardName.isEmpty()) {
                CardLayout cl = (CardLayout) (mainPanel.getLayout());
                cl.show(mainPanel, cardName);
            }
        });

        return button;
    }

    private JPanel createAdminButton(JPanel mainPanel, String cardName, ImageIcon icon) {
        adminBtnPanel = new JPanel();
        adminBtnPanel.setLayout(null);
        adminBtnPanel.setBounds(1250, 15, 60, 60); 
        adminBtnPanel.setBackground(new Color(52, 52, 52));
        adminBtnPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setBounds(0, 0, 60, 60); 
        adminBtnPanel.add(iconLabel);
        
        adminBtnPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	 if (selectedButton != null) {
            	        selectedButton.setForeground(Color.WHITE);
            	        selectedButton.setFont(new Font("", Font.PLAIN, 20));
            	        selectedButton = null;
            	    }
            	    CardLayout cl = (CardLayout) (mainPanel.getLayout());
            	    cl.show(mainPanel, cardName);
            }
        });

        return adminBtnPanel;
    }
    
    private void selectButton(JButton clickedButton) {
        if (selectedButton != null && selectedButton != clickedButton) {
            selectedButton.setForeground(Color.WHITE);
            selectedButton.setFont(new Font("", Font.PLAIN, 20));
        }
        clickedButton.setForeground(new Color(255, 64, 169));
        clickedButton.setFont(new Font("", Font.BOLD, 20));
        selectedButton = clickedButton;
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


