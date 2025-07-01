package homePage;

import MainPage.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import JDBC.Database;

public class checkPlanner extends JFrame {

    private JTable plannerTable;
    private DefaultTableModel model;
    private Map<Integer, plannerPage.ActivityDetails> activityMap = new HashMap<>();
    private JLabel noDataLabel; 
    private JPanel activitiesPanel; 

    public static void launch() {
        EventQueue.invokeLater(() -> {
            try {
                checkPlanner frame = new checkPlanner();
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                if (mainPage.instance != null) {
                    mainPage.instance.showDim();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public checkPlanner() {
        setUndecorated(true);
        setSize(500, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(238, 235, 235));

        activitiesPanel = new RoundedPanel(); 
        activitiesPanel.setBounds(12, 40, 475, 370);
        activitiesPanel.setBackground(Color.WHITE);
        activitiesPanel.setLayout(null);
        getContentPane().add(activitiesPanel);

        JLabel listOfActivities = new JLabel("List of Planned Activities");
        listOfActivities.setFont(new Font("Tahoma", Font.BOLD, 22));
        listOfActivities.setBounds(30, 20, 400, 25);
        activitiesPanel.add(listOfActivities);

        JLabel sortBy = new JLabel("Sort by:");
        sortBy.setFont(new Font("Tahoma", Font.PLAIN, 16));
        sortBy.setBounds(30, 50, 70, 20);
        activitiesPanel.add(sortBy);

        JLabel recent = new JLabel("Recently");
        recent.setFont(new Font("Tahoma", Font.BOLD, 16));
        recent.setBounds(102, 50, 100, 20);
        activitiesPanel.add(recent);

        JPanel hrLine = new JPanel();
        hrLine.setBackground(new Color(52, 52, 52));
        hrLine.setBounds(10, 80, 455, 2);
        activitiesPanel.add(hrLine);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(52, 52, 52));
        panel.setBounds(0, 0, 500, 30);
        getContentPane().add(panel);
        panel.setLayout(null);

        JLabel topText = new JLabel("LIST OF PLANNED ACIVITIES"); 
        topText.setForeground(new Color(255, 255, 255));
        topText.setFont(new Font("Segoe UI", Font.BOLD, 16));
        topText.setBounds(20, 5, 300, 20);
        panel.add(topText);

        JButton ExitButton = new JButton("X");
        ExitButton.setBounds(455, 0, 45, 30);
        panel.add(ExitButton);
        styleButton(ExitButton);

        ExitButton.addActionListener(e -> {
            dispose();
            if (mainPage.instance != null) {
                mainPage.instance.hideDim();
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

        String[] columns = {"Title", "Date", "Status"};
        model = new DefaultTableModel(columns, 0);

        plannerTable = new JTable(model) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                JTextArea area = new JTextArea(getValueAt(row, column).toString());
                area.setLineWrap(true);
                area.setWrapStyleWord(true);
                area.setFont(new Font("Segoe UI", Font.PLAIN, 18));
                area.setBackground(new Color(255, 255, 255));
                area.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(52, 52, 52)),
                        BorderFactory.createEmptyBorder(10, 20, 10, 10)
                ));
                if (column == 2) {
                    if (getValueAt(row, column).toString().equalsIgnoreCase("Approved")) {
                        area.setForeground(Color.decode("#ff4fc3"));
                    } else {
                        area.setForeground(Color.GRAY);
                    }
                }
                if (!isRowSelected(row)) {
                    area.setBackground(new Color(255, 255, 255));
                } else {
                    area.setBackground(new Color(180, 205, 255));
                    area.setFont(new Font("Segoe UI", Font.BOLD, 16));
                }
                return area;
            }
        };

        plannerTable.setShowGrid(false);
        plannerTable.setTableHeader(null);
        plannerTable.setRowHeight(70);
        plannerTable.setIntercellSpacing(new Dimension(0, 0));
        plannerTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        plannerTable.getColumnModel().getColumn(1).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(plannerTable);
        scrollPane.setBounds(10, 84, 455, 276);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        styleScrollBar(scrollPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        activitiesPanel.add(scrollPane);

        activityMap.put(0, new plannerPage.ActivityDetails(
            "Wheelchair Distribution", "₱10,000", "Approved", "Delivering wheelchairs to barangay residents."
        ));

        loadActivitiesFromDatabase(); 

        plannerTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && plannerTable.getSelectedRow() != -1) {
                int row = plannerTable.getSelectedRow();
                showEditDialog(row);
            }
        });

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (!SwingUtilities.isDescendingFrom(e.getComponent(), plannerTable)) {
                    plannerTable.clearSelection();
                }
            }
        });
    }

    private void showEditDialog(int row) {
        plannerPage.ActivityDetails existing = activityMap.get(row);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(238, 235, 235));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 4, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JTextField titleField = new JTextField(existing != null ? existing.title : "");
        JTextField fundField = new JTextField(existing != null ? existing.fund : "");
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Approved", "Pending"});
        if (existing != null) statusBox.setSelectedItem(existing.status);
        JTextArea descArea = new JTextArea(existing != null ? existing.description : "");
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(descArea);
        scroll.setPreferredSize(new Dimension(250, 80));

        Font labelFont = new Font("Segoe UI", Font.BOLD, 12);

        panel.add(new JLabel("Title:") {{ setFont(labelFont); }}, gbc);
        gbc.gridy++;
        panel.add(titleField, gbc);

        gbc.gridy++;
        panel.add(new JLabel("Allocated Fund:") {{ setFont(labelFont); }}, gbc);
        gbc.gridy++;
        panel.add(fundField, gbc);

        gbc.gridy++;
        panel.add(new JLabel("Status:") {{ setFont(labelFont); }}, gbc);
        gbc.gridy++;
        panel.add(statusBox, gbc);

        gbc.gridy++;
        panel.add(new JLabel("Project Description:") {{ setFont(labelFont); }}, gbc);
        gbc.gridy++;
        panel.add(scroll, gbc);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Activity",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String title = titleField.getText().trim();
            String fund = fundField.getText().trim();
            String status = (String) statusBox.getSelectedItem();
            String desc = descArea.getText().trim();

            if (!title.isEmpty()) {
                plannerPage.ActivityDetails updated = new plannerPage.ActivityDetails(title, fund, status, desc);
                activityMap.put(row, updated);
                model.setValueAt(title, row, 0);
                model.setValueAt("Edited", row, 1);
            }
        }
    }

    private void loadActivitiesFromDatabase() {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT title, activity_date, fund, status, description " +
                 "FROM planner_activities " +
                 "WHERE MONTH(activity_date) = MONTH(CURDATE()) " +
                 "AND YEAR(activity_date) = YEAR(CURDATE()) " +
                 "ORDER BY activity_date ASC"
             );
             ResultSet rs = stmt.executeQuery()) {

            model.setRowCount(0); 
            activityMap.clear();

            int rowIndex = 0;
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                String title = rs.getString("title");
                String date = rs.getString("activity_date");
                String fund = "₱ " + rs.getString("fund");
                String status = rs.getString("status");
                String desc = rs.getString("description");

                model.addRow(new Object[]{title, date, status});
                activityMap.put(rowIndex++, new plannerPage.ActivityDetails(title, fund, status, desc));
            }

            if (noDataLabel != null) {
                activitiesPanel.remove(noDataLabel);
                noDataLabel = null;
            }

            if (hasData) {
                plannerTable.setVisible(true);
            } else {
                noDataLabel = new JLabel("No Planned Activities");
                noDataLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                noDataLabel.setHorizontalAlignment(SwingConstants.CENTER);
                noDataLabel.setVerticalAlignment(SwingConstants.CENTER);
                noDataLabel.setBounds(0, 0, 475, 370); 
                noDataLabel.setOpaque(false); 
                noDataLabel.setEnabled(false); 
                activitiesPanel.add(noDataLabel);
                plannerTable.setVisible(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load monthly activities: " + e.getMessage());
        }
        activitiesPanel.revalidate();
        activitiesPanel.repaint();
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(200, 50, 50));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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

class RoundedPanel extends JPanel {
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