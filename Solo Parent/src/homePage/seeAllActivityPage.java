package homePage;

import MainPage.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import JDBC.Database;

public class seeAllActivityPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable summaryTable;

	public static void launch() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					seeAllActivityPage frame = new seeAllActivityPage();
					frame.setResizable(false);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public seeAllActivityPage() {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 20, 800, 810);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel SummaryTXT = new JLabel("Summary of Future Activities");
		SummaryTXT.setBounds(80, 10, 337, 47);
        SummaryTXT.setFont(new Font("Tahoma", Font.PLAIN, 24));
        getContentPane().add(SummaryTXT);
        
        JPanel activityTopBar = new RoundedPanel();
        activityTopBar.setBounds(20, 70, 760, 36);
        activityTopBar.setBackground(new Color( 255, 64, 169));
        getContentPane().add(activityTopBar);
        activityTopBar.setLayout(null);
        
        JLabel activity = new JLabel("ACTIVITY");
        activity.setFont(new Font("Tahoma", Font.BOLD, 16));
        activity.setForeground(new Color(0, 0, 0));
        activity.setBounds(30, 10, 84, 13);
        activityTopBar.add(activity);
        
        JLabel date = new JLabel("DATE\r\n");
        date.setForeground(Color.BLACK);
        date.setFont(new Font("Tahoma", Font.BOLD, 16));
        date.setBounds(215, 10, 54, 13);
        activityTopBar.add(date);
        
        JLabel budget = new JLabel("BUDGET\r\n");
        budget.setForeground(Color.BLACK);
        budget.setFont(new Font("Tahoma", Font.BOLD, 16));
        budget.setBounds(400, 10, 72, 13);
        activityTopBar.add(budget);
        
        JLabel status = new JLabel("STATUS");
        status.setForeground(Color.BLACK);
        status.setFont(new Font("Tahoma", Font.BOLD, 16));
        status.setBounds(590, 10, 72, 13);
        activityTopBar.add(status);
        
        JPanel summarytablePanel = new RoundedPanel();
        summarytablePanel.setBounds(21, 112, 760, 685);
        summarytablePanel.setLayout(null);
        summarytablePanel.setBackground(new Color(255,255,255));
        getContentPane().add(summarytablePanel);
        
        String[] columns = {"Activity", "Date", "Budget", "Status"};
        DefaultTableModel expModel = new DefaultTableModel(columns, 0);
        loadActivitiesFromDB(expModel);
        
        summaryTable = new JTable(expModel) {
        	@Override
        	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        		Component c = super.prepareRenderer(renderer, row, column);
        		JTextArea area = new JTextArea(getValueAt(row, column).toString());
        		area.setLineWrap(true);
        		area.setWrapStyleWord(true);
        		area.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        		area.setBackground(Color.WHITE);
        		area.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        		if (column == 3) {
        			if (getValueAt(row, column).toString().equalsIgnoreCase("Approved")) {
        				area.setForeground(Color.decode("#ff4fc3")); 
        			} else {
        				area.setForeground(Color.GRAY);
        			}
        		}
        		return area;
        	}
        	@Override
            public boolean isCellEditable(int row, int column) {

               return false;
            }
        };
        summaryTable.setRowHeight(65);
        summaryTable.setShowGrid(false);
        summaryTable.setIntercellSpacing(new Dimension(30, 5));
        summaryTable.setTableHeader(null);
        
        JScrollPane scrollPane = new JScrollPane(summaryTable);
        scrollPane.setBounds(3, 3, 751, 675); 
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        styleScrollBar(scrollPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        summarytablePanel.add(scrollPane); 
        
        JButton backBTN = new RoundedButton("Back");
        backBTN.setBounds(700, 20, 75, 30);
        backBTN.setBackground(new Color(200, 50, 50));
        getContentPane().add(backBTN);
        
        ImageIcon summarylogo1 = new ImageIcon(seeAllActivityPage.class.getResource("/imgs/summary1.png"));
        JLabel lblNewLabel = new JLabel(summarylogo1);
        lblNewLabel.setBounds(25, 10, 45, 45);
        contentPane.add(lblNewLabel);
        
        backBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPage.instance.hideDim();
				dispose();
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
	
	private void loadActivitiesFromDB(DefaultTableModel model) {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM planner_activities ORDER BY activity_date ASC");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String title = rs.getString("title");
                String date = rs.getDate("activity_date").toString(); 
                String fund = "₱ " + rs.getString("fund");
                String status = rs.getString("status");

                model.addRow(new String[]{title, date, fund, status});
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load activities from database.");
        }
    }
	
	private void styleScrollBar(JScrollPane scrollPane) {
	    JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
	    verticalScrollBar.setUI(new BasicScrollBarUI() {
	        @Override
	        protected void configureScrollBarColors() {
	            this.thumbColor = new Color(150, 150, 150); 
	            this.thumbDarkShadowColor = new Color(150, 150, 150);
	            this.thumbHighlightColor = new Color(150, 150, 150);
	            this.thumbLightShadowColor = new Color(150, 150, 150);
	            this.trackColor = new Color(245, 245, 245); 
	        }

	        @Override
	        protected JButton createDecreaseButton(int orientation) {
	            return createZeroButton();
	        }

	        @Override
	        protected JButton createIncreaseButton(int orientation) {
	            return createZeroButton();
	        }

	        private JButton createZeroButton() {
	            JButton button = new JButton();
	            button.setPreferredSize(new Dimension(0, 0));
	            button.setMinimumSize(new Dimension(0, 0));
	            button.setMaximumSize(new Dimension(0, 0));
	            return button;
	        }

	        @Override
	        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
	            Graphics2D g2 = (Graphics2D) g.create();
	            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	            g2.setPaint(thumbColor);
	            g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
	            g2.dispose();
	        }
	        
	    });

	    verticalScrollBar.setPreferredSize(new Dimension(8, Integer.MAX_VALUE));
	    verticalScrollBar.setOpaque(false);
	    scrollPane.setOpaque(false);
	    scrollPane.getViewport().setOpaque(false);
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


