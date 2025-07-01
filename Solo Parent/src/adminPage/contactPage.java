package adminPage;

import MainPage.*;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;

public class contactPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void launch() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					contactPage frame = new contactPage();
	                frame.setResizable(false);
	                frame.setLocationRelativeTo(null);
	                frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public contactPage() {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 670, 570);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ImageIcon contactLogo1 = new ImageIcon(contactPage.class.getResource("/imgs/contact1.png"));
		JLabel contact = new JLabel(contactLogo1);
		contact.setBounds(25, 15, 40, 40);
		getContentPane().add(contact);
		
		JLabel contactTeam = new JLabel("CONTACT THE TEAM");
		contactTeam.setFont(new Font("Tahoma", Font.BOLD, 18));
		contactTeam.setBounds(80, 20, 200, 20);
		getContentPane().add(contactTeam);
		
		JLabel contactDesc = new JLabel("Access the profiles and contact details of the development team");
		contactDesc.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contactDesc.setBounds(80, 42, 450, 15);
		getContentPane().add(contactDesc);
		
		JPanel hrLine2 = new JPanel();
		hrLine2.setBackground(new Color(52, 52, 52));
		hrLine2.setBounds(20, 70, 630, 2);
		getContentPane().add(hrLine2);
		
		JPanel janaPanel = new RoundedPanel();
		janaPanel.setBounds(20, 80, 630, 85);
		getContentPane().add(janaPanel);
		janaPanel.setLayout(null);
		
        ImageIcon janaIMG = new ImageIcon(contactPage.class.getResource("/imgs/jana.png"));
		JLabel jana = new JLabel(janaIMG);
		jana.setBounds(20, 10, 65, 65);
		janaPanel.add(jana);
		
		JLabel janaName = new JLabel("AGUSTIN, JANA BEATRICE");
		janaName.setFont(new Font("Tahoma", Font.BOLD, 20));
		janaName.setBounds(105, 20, 400, 25);
		janaPanel.add(janaName);
		
		JLabel janaNumber = new JLabel("0995 364 9868");
		janaNumber.setFont(new Font("Tahoma", Font.PLAIN, 16));
		janaNumber.setBounds(105, 47, 200, 18);
		janaPanel.add(janaNumber);
		
		JPanel pvPanel = new RoundedPanel();
		pvPanel.setBounds(20, 176, 630, 85);
		getContentPane().add(pvPanel);
		pvPanel.setLayout(null);
		
        ImageIcon pvIMG = new ImageIcon(contactPage.class.getResource("/imgs/pv.png"));
		JLabel pv = new JLabel(pvIMG);
		pv.setBounds(20, 10, 65, 65);
		pvPanel.add(pv);
		
		JLabel pvName = new JLabel("CAPACIO, PEAVEY IYA");
		pvName.setFont(new Font("Tahoma", Font.BOLD, 20));
		pvName.setBounds(105, 20, 400, 25);
		pvPanel.add(pvName);
		
		JLabel pvNumber = new JLabel("0995 364 9868");
		pvNumber.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pvNumber.setBounds(105, 47, 200, 18);
		pvPanel.add(pvNumber);
		
		JPanel sephPanel = new RoundedPanel();
		sephPanel.setBounds(20, 273, 630, 85);
		getContentPane().add(sephPanel);
		sephPanel.setLayout(null);
		
        ImageIcon sephIMG = new ImageIcon(contactPage.class.getResource("/imgs/seph.png"));
		JLabel seph = new JLabel(sephIMG);
		seph.setBounds(20, 10, 65, 65);
		sephPanel.add(seph);
		
		JLabel sephName = new JLabel("DESALIT, JOSEPH ISMAEL");
		sephName.setFont(new Font("Tahoma", Font.BOLD, 20));
		sephName.setBounds(105, 20, 400, 25);
		sephPanel.add(sephName);
		
		JLabel sephNumber = new JLabel("0995 364 9868");
		sephNumber.setFont(new Font("Tahoma", Font.PLAIN, 16));
		sephNumber.setBounds(105, 47, 200, 18);
		sephPanel.add(sephNumber);
		
		JPanel eliPanel = new RoundedPanel();
		eliPanel.setBounds(20, 370, 630, 85);
		getContentPane().add(eliPanel);
		eliPanel.setLayout(null);
		
        ImageIcon eliIMG = new ImageIcon(contactPage.class.getResource("/imgs/eli.png"));
		JLabel eli = new JLabel(eliIMG);
		eli.setBounds(20, 10, 65, 65);
		eliPanel.add(eli);
		
		JLabel eliName = new JLabel("PENUS, ELIAND JOHN");
		eliName.setFont(new Font("Tahoma", Font.BOLD, 20));
		eliName.setBounds(105, 20, 400, 25);
		eliPanel.add(eliName);
		
		JLabel eliNumber = new JLabel("0995 364 9868");
		eliNumber.setFont(new Font("Tahoma", Font.PLAIN, 16));
		eliNumber.setBounds(105, 47, 200, 18);
		eliPanel.add(eliNumber);
		
		JPanel earlPanel = new RoundedPanel();
		earlPanel.setBounds(20, 466, 630, 85);
		getContentPane().add(earlPanel);
		earlPanel.setLayout(null);
		
        ImageIcon earlIMG = new ImageIcon(contactPage.class.getResource("/imgs/earl.png"));
		JLabel earl = new JLabel(earlIMG);
		earl.setBounds(20, 10, 65, 65);
		earlPanel.add(earl);
		
		JLabel earlName = new JLabel("PERUCHO, EARL MICHAEL");
		earlName.setFont(new Font("Tahoma", Font.BOLD, 20));
		earlName.setBounds(105, 20, 400, 25);
		earlPanel.add(earlName);
		
		JLabel earlNumber = new JLabel("0995 364 9868");
		earlNumber.setFont(new Font("Tahoma", Font.PLAIN, 16));
		earlNumber.setBounds(105, 47, 200, 18);
		earlPanel.add(earlNumber);
        
		JButton closeBTN = new RoundedButton("Close");
		closeBTN.setBounds(560, 15, 85, 30);
		styleButton(closeBTN);
		contentPane.add(closeBTN);
		
		closeBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        dispose(); 
		        if (mainPage.instance != null) {
		        	mainPage.instance.hideDim(); 
		        }
			}
		});
		
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (mainPage.instance != null) {
                    mainPage.instance.hideDim();
                }
            }
        });
	}
	private void styleButton(JButton button) {
        button.setBackground(new Color(200, 50, 50));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Tahoma", Font.BOLD, 14));
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

class RoundedPanel extends JPanel 	{
	private static final long serialVersionUID = 1L;
	private int cornerRadius = 20;
	
	public RoundedPanel() {
		setOpaque(false);
		setBackground(new Color(238, 235, 235));
	}
	
	public RoundedPanel(int radius) {
		this();
		this.cornerRadius = radius;
	}
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        g2.setColor(new Color(58, 58, 58));
        g2.setStroke(new BasicStroke(1));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        g2.dispose();
    }
}
