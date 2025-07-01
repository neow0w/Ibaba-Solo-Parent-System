package homePage;

import recordPage.informationPage;
import MainPage.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import JDBC.Database;

public class newMember extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable membertable;

	public static void launch() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					newMember frame = new newMember();
					frame.setResizable(false);
	                frame.setLocationRelativeTo(null);
	                frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public newMember() {
		setUndecorated(true);
		setBounds(100, 100, 1030, 280);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String[] columns = {"No.", "Name", "Solo Parent ID#", "Birthday", "Sex", "Status"};
	    DefaultTableModel expModel = new DefaultTableModel(columns, 0);
            
        membertable = new JTable(expModel) {
        	@Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            	JTextArea area = new JTextArea(getValueAt(row, column).toString());
            	area.setLineWrap(true);
            	area.setWrapStyleWord(true);
            	area.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            	area.setBackground(new Color(238,235,235));
            	area.setBorder(BorderFactory.createEmptyBorder(5, 30, 10, 10));
            	
            	if(!isRowSelected(row)) {
            		if (row % 2 == 0) {
            			area.setBackground(new Color(255,255,255)); 
            	    } else {
            	    	 area.setBackground(new Color(255,242,249)); 
            	    }
            	} else {
            	    area.setBackground(new Color(180, 205, 255));
            	    area.setFont(new Font("Segoe UI", Font.BOLD, 16));
            	}
            	
            	return area;	
            }
        	@Override
            public boolean isCellEditable(int row, int column) {

               return false;
            }
        };
        
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT applicant_id, name, birthdate, sex, civil_status FROM applicant_information";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                
                int count = 1;
                while (rs.next()) {
                    String id = rs.getString("applicant_id");
                    String name = rs.getString("name");
                    String birthdate = rs.getString("birthdate");
                    String sex = rs.getString("sex");
                    String status = rs.getString("civil_status");
                    expModel.addRow(new Object[]{count++, name, id, birthdate, sex, status});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
        
        membertable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = membertable.getSelectedRow();
                if (selectedRow != -1 && e.getClickCount() == 1) { 
                    int modelRow = membertable.convertRowIndexToModel(selectedRow);
                    Object idObj = membertable.getModel().getValueAt(modelRow, 2); 
                    String applicantId = idObj.toString();
                    
                    informationPage info = new informationPage(applicantId);
                    info.setLocationRelativeTo(null);
                    info.setVisible(true);
                }
            }
        });
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);

        membertable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // No.
        membertable.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);   // Name
        membertable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Solo Parent ID Number
        membertable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Birthday
        membertable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Sex
        membertable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer); // Status
 
        membertable.getColumnModel().getColumn(0).setPreferredWidth(30);
        membertable.getColumnModel().getColumn(1).setPreferredWidth(220);
        membertable.getColumnModel().getColumn(2).setPreferredWidth(150);
        membertable.getColumnModel().getColumn(3).setPreferredWidth(150);
        membertable.getColumnModel().getColumn(4).setPreferredWidth(60);
        membertable.getColumnModel().getColumn(5).setPreferredWidth(150);
    
        membertable.setRowHeight(57);
        membertable.setShowGrid(false);
        membertable.setIntercellSpacing(new Dimension(0, 0));
        membertable.setTableHeader(null);
        
        JScrollPane scrollPane = new JScrollPane(membertable);
        scrollPane.setBounds(20, 88, 990, 172); 
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        styleScrollBar(scrollPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            
        getContentPane().add(scrollPane);

        JPanel tableHeader = new JPanel();
        tableHeader.setBackground(new Color(255, 64, 169));
        tableHeader.setBounds(20, 45, 990, 43);
        getContentPane().add(tableHeader);
        tableHeader.setLayout(null);
        
        JLabel number = new JLabel("No.");
        number.setFont(new Font("Tahoma", Font.BOLD, 18));
        number.setBounds(25, 10, 40, 23);
        tableHeader.add(number);
    
	    JLabel name = new JLabel("Name");
	    name.setFont(new Font("Tahoma", Font.BOLD, 18));
	    name.setBounds(100, 10, 65, 23);
	    tableHeader.add(name);
	    
	    JLabel soloParentID = new JLabel("Solo Parent ID#");
	    soloParentID.setFont(new Font("Tahoma", Font.BOLD, 18));
	    soloParentID.setBounds(360, 10, 160, 23);
	    tableHeader.add(soloParentID);
	    
	    JLabel birtday = new JLabel("Birthday\r\n");
	    birtday.setFont(new Font("Tahoma", Font.BOLD, 18));
	    birtday.setBounds(550, 10, 90, 23);
	    tableHeader.add(birtday);
	    
	    JLabel sex = new JLabel("Sex");
	    sex.setFont(new Font("Tahoma", Font.BOLD, 18));
	    sex.setBounds(730, 10, 50, 23);
	    tableHeader.add(sex);
	    
	    JLabel status = new JLabel("Status");
	    status.setFont(new Font("Tahoma", Font.BOLD, 18));
	    status.setBounds(830, 10, 70, 23);
	    tableHeader.add(status);
	    
	    JPanel topPanel = new JPanel();
	    topPanel.setBackground(new Color(52, 52, 52));
	    topPanel.setBounds(0, 0, 1030, 30);
	    contentPane.add(topPanel);
	    topPanel.setLayout(null);
	    
	    JButton ExitButton = new JButton("X");
	    ExitButton.setForeground(Color.WHITE);
	    ExitButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
	    ExitButton.setFocusPainted(false);
	    ExitButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
	    ExitButton.setBackground(new Color(200, 50, 50));
	    ExitButton.setBounds(985, 1, 45, 30);
	    ExitButton.addActionListener(new ActionListener() {
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
	    
	    topPanel.add(ExitButton);
	    
	    JLabel newMember = new JLabel("NEW MEMBER");
	    newMember.setForeground(new Color(238, 235, 235));
	    newMember.setFont(new Font("Tahoma", Font.BOLD, 16));
	    newMember.setBounds(20, 5, 166, 20);
	    topPanel.add(newMember);
            
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
