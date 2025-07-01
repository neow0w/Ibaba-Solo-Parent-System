package MainPage;

import recordPage.*;
import java.awt.*;
import java.awt.event.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import JDBC.Database;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.*;

public class recordPage extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable membertable;
    private JTextField searchBar;
    private JButton addButton;
    private JPanel tablePanel; 
    private JLabel noRecordsLabel; 

    public recordPage() {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(238, 235, 235));
        setLayout(null);

        tablePanel = new JPanel();
        tablePanel.setLayout(null);
        tablePanel.setBounds(20, 115, 1290, 558);
        tablePanel.setBackground(new Color(238, 235, 235));
        add(tablePanel);

        String[] columns = {"No.", "Name", "Solo Parent ID#", "Birthday", "Sex", "Status"};
        DefaultTableModel expModel = new DefaultTableModel(columns, 0);

        membertable = new JTable(expModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                JTextArea area = new JTextArea(getValueAt(row, column).toString());
                area.setLineWrap(true);
                area.setWrapStyleWord(true);
                area.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                area.setBackground(new Color(238, 235, 235));
                area.setBorder(BorderFactory.createEmptyBorder(5, 30, 10, 10));

                if (!isRowSelected(row)) {
                    if (row % 2 == 0) {
                        area.setBackground(new Color(255, 255, 255));
                    } else {
                        area.setBackground(new Color(255, 242, 249));
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

        membertable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = membertable.getSelectedRow();
                if (selectedRow != -1 && e.getClickCount() == 1) {
                    int modelRow = membertable.convertRowIndexToModel(selectedRow);
                    Object idObj = membertable.getModel().getValueAt(modelRow, 2);
                    String applicantId = idObj.toString();
                    
                    informationPage info = new informationPage(applicantId);
                    mainPage.instance.showDim();
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
        membertable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        membertable.setIntercellSpacing(new Dimension(0, 0));
        membertable.setTableHeader(null);
        membertable.setBounds(0, 0, 1290, 558);
        tablePanel.add(membertable);

        JScrollPane scrollPane = new JScrollPane(membertable);
        scrollPane.setBounds(0, 0, 1290, 558);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        styleScrollBar(scrollPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tablePanel.add(scrollPane);

        JPanel tableHeader = new JPanel();
        tableHeader.setBackground(new Color(255, 64, 169));
        tableHeader.setBounds(20, 72, 1290, 43);
        add(tableHeader);
        tableHeader.setLayout(null);

        JLabel number = new JLabel("No.");
        number.setFont(new Font("Tahoma", Font.BOLD, 18));
        number.setBounds(25, 10, 40, 23);
        tableHeader.add(number);

        JLabel name = new JLabel("Name");
        name.setFont(new Font("Tahoma", Font.BOLD, 18));
        name.setBounds(150, 10, 65, 23);
        tableHeader.add(name);

        JLabel soloParentID = new JLabel("Solo Parent ID#");
        soloParentID.setFont(new Font("Tahoma", Font.BOLD, 18));
        soloParentID.setBounds(460, 10, 160, 23);
        tableHeader.add(soloParentID);

        JLabel birtday = new JLabel("Birthday\r\n");
        birtday.setFont(new Font("Tahoma", Font.BOLD, 18));
        birtday.setBounds(700, 10, 90, 23);
        tableHeader.add(birtday);

        JLabel sex = new JLabel("Sex");
        sex.setFont(new Font("Tahoma", Font.BOLD, 18));
        sex.setBounds(935, 10, 50, 23);
        tableHeader.add(sex);

        JLabel status = new JLabel("Status");
        status.setFont(new Font("Tahoma", Font.BOLD, 18));
        status.setBounds(1080, 10, 70, 23);
        tableHeader.add(status);

        searchBar = new RoundedTextField(10);
        searchBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchText = searchBar.getText();
                DefaultTableModel expModel = (DefaultTableModel) membertable.getModel();
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(expModel);
                membertable.setRowSorter(sorter);
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
            }
        });
        searchBar.setText("Search");
        searchBar.setBounds(41, 20, 300, 30);
        searchBar.setColumns(10);
        searchBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 3, 10));
        add(searchBar);

        searchBar.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchBar.getText().equals("Search")) {
                    searchBar.setText("");
                    searchBar.setForeground(Color.BLACK);
                    searchBar.setFont(new Font("Tahoma", Font.BOLD, 14));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchBar.getText().isEmpty()) {
                    searchBar.setText("Search");
                    searchBar.setForeground(Color.GRAY);
                    searchBar.setFont(new Font("Tahoma", Font.PLAIN, 14));
                }
            }
        });

        addButton = new RoundedButton("New Member");
        addButton.setBounds(1170, 20, 120, 30);
        add(addButton);
        addButton.setBackground(new Color(255, 64, 169));
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addMemberPage add = new addMemberPage(() -> loadApplicantData());
                mainPage.instance.showDim();
                add.setLocationRelativeTo(null);
                add.setVisible(true);
            }
        });
        loadApplicantData();

        Timer refreshTimer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadApplicantData();
            }
        });
        refreshTimer.setRepeats(true);
        refreshTimer.start();
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

    public void loadApplicantData() {
        int retryCount = 3;
        while (retryCount > 0) {
            try (Connection conn = Database.getConnection()) {
                if (conn == null) {
                    System.err.println("Database connection is null. Retrying...");
                    retryCount--;
                    Thread.sleep(1000); 
                    continue;
                }

                String query = "SELECT applicant_id, name, birthdate, sex, civil_status FROM applicant_information ORDER BY name ASC";
                try (PreparedStatement stmt = conn.prepareStatement(query);
                     ResultSet rs = stmt.executeQuery()) {

                    DefaultTableModel model = (DefaultTableModel) membertable.getModel();
                    model.setRowCount(0);

                    int count = 1;
                    boolean hasData = false;

                    while (rs.next()) {
                        hasData = true;
                        String name = rs.getString("name");
                        String id = rs.getString("applicant_id");
                        String birthdate = rs.getString("birthdate");
                        String sex = rs.getString("sex");
                        String status = rs.getString("civil_status");

                        if (name == null) name = "N/A";
                        if (id == null) id = "N/A";
                        if (birthdate == null) birthdate = "N/A";
                        if (sex == null) sex = "N/A";
                        if (status == null) status = "N/A";

                        model.addRow(new Object[]{count++, name, id, birthdate, sex, status});
                    }

                    tablePanel.removeAll();

                    if (hasData) {
                        tablePanel.add(membertable);
                        JScrollPane scrollPane = new JScrollPane(membertable);
                        scrollPane.setBounds(0, 0, 1290, 558);
                        scrollPane.setBorder(BorderFactory.createEmptyBorder());
                        styleScrollBar(scrollPane);
                        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                        tablePanel.add(scrollPane);
                    } else {
                        noRecordsLabel = new JLabel("No Records Found");
                        noRecordsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                        noRecordsLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        noRecordsLabel.setVerticalAlignment(SwingConstants.CENTER);
                        noRecordsLabel.setBounds(0, 0, 1290, 558);
                        tablePanel.add(noRecordsLabel);
                    }
                    break;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage() + ". Retries left: " + retryCount);
                retryCount--;
                if (retryCount > 0) {
                    try { Thread.sleep(1000); } catch (InterruptedException ie) { ie.printStackTrace(); }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Operation interrupted: " + e.getMessage());
                break;
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Unexpected error: " + e.getMessage());
                break;
            }
        }

        if (retryCount == 0) {
            JOptionPane.showMessageDialog(this, "Failed to connect to the database after multiple attempts. Check server status.");
        }

        tablePanel.revalidate();
        tablePanel.repaint();
        revalidate();
        repaint();
    }
}

class RoundedTextField extends JTextField {
    private static final long serialVersionUID = 1L;
    private int cornerRadius = 20;

    public RoundedTextField(int columns) {
        super(columns);
        setOpaque(false);
        setBackground(new Color(255, 255, 255));
        setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 10));
    }

    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(getBackground());
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(58, 58, 58));
        g2.setStroke(new BasicStroke(1));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        g2.dispose();
    }
}