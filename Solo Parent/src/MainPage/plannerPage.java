package MainPage;

import plannerPage.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import JDBC.Database;

public class plannerPage extends JPanel {
    private static final long serialVersionUID = 1L;
    private final JPanel calendarPanel;
    private final JLabel monthLabel;
    private final Calendar calendar;
    private final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    private final Map<String, ActivityDetails> activities = new HashMap<>();
    private final Map<JButton, JPanel> underlineMap = new HashMap<>();
    private JTable activitytable;
    private JButton selectedButton = null;
    public static plannerPage instance;
    private JPanel mainPanel;
    private final JPanel allLine, approvedLine, pendingLine, reschedLine;

    public plannerPage() {
        instance = this;
        setLayout(null);
        setBackground(new Color(238, 235, 235));
        calendar = Calendar.getInstance();

        JPanel topPanel = new JPanel(null);
        topPanel.setBackground(getBackground());
        topPanel.setBounds(20, 10, 805, 100);

        monthLabel = new JLabel("", SwingConstants.LEADING);
        monthLabel.setFont(new Font("Segoe UI", Font.BOLD, 45));
        monthLabel.setForeground(new Color(255, 64, 169));
        monthLabel.setBounds(25, 0, 501, 60);
        topPanel.add(monthLabel);

        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        String dayName = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        JLabel day = new JLabel(dayName);
        day.setFont(new Font("Tahoma", Font.PLAIN, 24));
        day.setBounds(25, 65, 307, 30);
        topPanel.add(day);

        JButton prevBtn = new RoundedButton("<");
        JButton nextBtn = new RoundedButton(">");
        JButton goToBtn = new JButton("Go to Date");
        styleButton(goToBtn);

        prevBtn.setBounds(560, 60, 40, 30);
        goToBtn.setBounds(610, 60, 116, 30);
        nextBtn.setBounds(735, 60, 40, 30);

        topPanel.add(prevBtn);
        topPanel.add(goToBtn);
        topPanel.add(nextBtn);
        add(topPanel);

        prevBtn.addActionListener(e -> {
            calendar.add(Calendar.MONTH, -1);
            refreshCalendar();
        });

        nextBtn.addActionListener(e -> {
            calendar.add(Calendar.MONTH, 1);
            refreshCalendar();
        });

        goToBtn.addActionListener(e -> showGoToDatePicker());

        JPanel calendarBG = new RoundedPanel();
        calendarBG.setBounds(15, 115, 810, 560);
        calendarBG.setBackground(getBackground());
        calendarBG.setLayout(null);
        add(calendarBG);

        calendarPanel = new JPanel(new GridLayout(0, 7, 10, 10));
        calendarPanel.setBackground(getBackground());
        calendarPanel.setBounds(5, 5, 800, 550);
        calendarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        calendarBG.add(calendarPanel);

        JPanel activitiesPanel = new RoundedPanel();
        activitiesPanel.setBounds(841, 20, 460, 650);
        activitiesPanel.setBackground(new Color(238, 235, 235));
        add(activitiesPanel);
        activitiesPanel.setLayout(null);

        JLabel listofActivities = new JLabel("List of Planned Activities");
        listofActivities.setFont(new Font("Tahoma", Font.BOLD, 24));
        listofActivities.setBounds(30, 24, 315, 25);
        activitiesPanel.add(listofActivities);

        JPanel hrLine = new JPanel();
        hrLine.setBackground(new Color(52, 52, 52));
        hrLine.setBounds(10, 65, 440, 2);
        activitiesPanel.add(hrLine);

        JPanel tablePanel = new RoundedPanel();
        tablePanel.setBounds(10, 120, 440, 510);
        tablePanel.setLayout(null);
        tablePanel.setBackground(Color.WHITE);
        activitiesPanel.add(tablePanel);

        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.setBounds(5, 5, 430, 500);
        mainPanel.setLayout(new CardLayout());
        tablePanel.add(mainPanel);

        allLine = new JPanel();
        allLine.setBackground(new Color(255, 64, 169));
        allLine.setBounds(25, 108, 38, 4);
        activitiesPanel.add(allLine);

        approvedLine = new JPanel();
        approvedLine.setBackground(new Color(255, 64, 169));
        approvedLine.setBounds(90, 108, 95, 4);
        activitiesPanel.add(approvedLine);

        pendingLine = new JPanel();
        pendingLine.setBackground(new Color(255, 64, 169));
        pendingLine.setBounds(210, 108, 85, 4);
        activitiesPanel.add(pendingLine);

        reschedLine = new JPanel();
        reschedLine.setBackground(new Color(255, 64, 169));
        reschedLine.setBounds(315, 108, 115, 4);
        activitiesPanel.add(reschedLine);

        JButton btnAll = activityBTN("All", 15, 60, mainPanel, "all");
        JButton btnApproved = activityBTN("Approved", 70, 130, mainPanel, "approved");
        JButton btnPending = activityBTN("Pending", 190, 120, mainPanel, "pending");
        JButton btnResched = activityBTN("Reschedule", 295, 150, mainPanel, "resched");

        activitiesPanel.add(btnAll);
        activitiesPanel.add(btnApproved);
        activitiesPanel.add(btnPending);
        activitiesPanel.add(btnResched);

        underlineMap.put(btnAll, allLine);
        underlineMap.put(btnApproved, approvedLine);
        underlineMap.put(btnPending, pendingLine);
        underlineMap.put(btnResched, reschedLine);

        allLine.setVisible(false);
        approvedLine.setVisible(false);
        pendingLine.setVisible(false);
        reschedLine.setVisible(false);

        selectButton(btnAll);

        allPanel allPanel_ = new allPanel();
        mainPanel.add(allPanel_, "all");
        approvedPanel approvedPanel_ = new approvedPanel();
        mainPanel.add(approvedPanel_, "approved");
        pendingPanel pendingPanel_ = new pendingPanel();
        mainPanel.add(pendingPanel_, "pending");
        reschedPanel reschedPanel_ = new reschedPanel();
        mainPanel.add(reschedPanel_, "resched");

        JPanel noDataAll = createNoDataPanel("No Planned Activities So Far");
        mainPanel.add(noDataAll, "noDataAll");
        JPanel noDataApproved = createNoDataPanel("No Approved Activities So Far");
        mainPanel.add(noDataApproved, "noDataApproved");
        JPanel noDataPending = createNoDataPanel("No Pending Activities So Far");
        mainPanel.add(noDataPending, "noDataPending");
        JPanel noDataResched = createNoDataPanel("No Activities To Be Rescheduled So Far");
        mainPanel.add(noDataResched, "noDataResched");

        updateActivityTable(allPanel_.getTable());

        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        cl.show(mainPanel, "all");

        addMouseListenerToAll(this);

        loadActivitiesFromDB();
        refreshCalendar();
    }

    private JPanel createNoDataPanel(String message) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel label = new JLabel(message);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setEnabled(false);
        panel.add(label, gbc);
        return panel;
    }

    private void addMouseListenerToAll(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof Container) {
                addMouseListenerToAll((Container) comp);
            }
            comp.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (activitytable != null && !SwingUtilities.isDescendingFrom(e.getComponent(), activitytable)) {
                        activitytable.clearSelection();
                    }
                }
            });
        }
    }

    private JButton activityBTN(String text, int xPosition, int width, JPanel mainPanel, String cardName) {
        JButton button = new JButton(text);
        button.setBounds(xPosition, 80, width, 25);
        button.setFont(new Font("", Font.PLAIN, 18));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBackground(new Color(238, 235, 235));
        button.setForeground(Color.BLACK);

        button.addActionListener(e -> {
            selectButton(button);
            if (cardName != null && !cardName.isEmpty()) {
                CardLayout cl = (CardLayout) (mainPanel.getLayout());
                cl.show(mainPanel, cardName);
                updateActivityTableFromCard(cardName);
                filterTableByStatus(cardName);
                refreshCalendar(); 
            }
        });

        return button;
    }

    private void selectButton(JButton clickedButton) {
        if (selectedButton != null && selectedButton != clickedButton) {
            selectedButton.setForeground(Color.BLACK);
            selectedButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
            JPanel prevUnderline = underlineMap.get(selectedButton);
            if (prevUnderline != null) prevUnderline.setVisible(false);
        }

        clickedButton.setForeground(new Color(255, 64, 169));
        clickedButton.setFont(new Font("Tahoma", Font.BOLD, 18));
        JPanel newUnderline = underlineMap.get(clickedButton);
        if (newUnderline != null) newUnderline.setVisible(true);

        selectedButton = clickedButton;
    }

    private void updateActivityTableFromCard(String cardName) {
        Component panel = Arrays.stream(mainPanel.getComponents())
                .filter(c -> c.getName() != null && c.getName().equals(cardName))
                .findFirst()
                .orElse(null);
        if (panel instanceof allPanel) {
            activitytable = ((allPanel) panel).getTable();
        } else if (panel instanceof approvedPanel) {
            activitytable = ((approvedPanel) panel).getTable();
        } else if (panel instanceof pendingPanel) {
            activitytable = ((pendingPanel) panel).getTable();
        } else if (panel instanceof reschedPanel) {
            activitytable = ((reschedPanel) panel).getTable();
        }
    }

    public void updateActivityTable(JTable table) {
        this.activitytable = table;
    }

    private void updateMonthLabel() {
        monthLabel.setText(new SimpleDateFormat("MMMM yyyy").format(calendar.getTime()));
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(255, 64, 169));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void refreshCalendar() {
        calendarPanel.removeAll();
        updateMonthLabel();

        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String d : days) {
            JLabel label = new JLabel(d, SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 14));
            label.setForeground(d.equals("Sun") ? Color.RED : new Color(60, 60, 60));
            calendarPanel.add(label);
        }

        Calendar calendar_ = (Calendar) calendar.clone();
        calendar_.set(Calendar.DAY_OF_MONTH, 1);
        int firstDay = calendar_.get(Calendar.DAY_OF_WEEK) - 1;
        int totalDays = calendar_.getActualMaximum(Calendar.DAY_OF_MONTH);
        Calendar today = Calendar.getInstance();
        String selectedFilter = selectedButton != null ? selectedButton.getText().toLowerCase() : "all";

        for (int i = 0; i < firstDay; i++) {
            calendarPanel.add(new JPanel());
        }

        for (int day = 1; day <= totalDays; day++) {
            calendar_.set(Calendar.DAY_OF_MONTH, day);
            String key = sdf.format(calendar_.getTime());
            ActivityDetails actDetail = activities.get(key);

            RoundedCell cell = new RoundedCell();
            cell.setLayout(new BorderLayout(0, 5));
            cell.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
            cell.setBackground(Color.WHITE);

            if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                day == today.get(Calendar.DAY_OF_MONTH)) {
                cell.setBackground(new Color(255, 180, 218));
            }

            JLabel dayLabel = new JLabel(String.valueOf(day), SwingConstants.CENTER);
            dayLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            cell.add(dayLabel, BorderLayout.NORTH);

            boolean shouldDisplay = false;
            if (actDetail != null) {
                switch (selectedFilter) {
                    case "all":
                        shouldDisplay = true;
                        break;
                    case "approved":
                        shouldDisplay = "Approved".equalsIgnoreCase(actDetail.status);
                        break;
                    case "pending":
                        try {
                            Date activityDate = sdf.parse(key);
                            shouldDisplay = "Pending".equalsIgnoreCase(actDetail.status) &&
                                (activityDate.equals(today.getTime()) || activityDate.after(today.getTime()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case "reschedule":
                        try {
                            Date activityDate = sdf.parse(key);
                            Calendar todayCal = Calendar.getInstance();
                            todayCal.set(Calendar.HOUR_OF_DAY, 0);
                            todayCal.set(Calendar.MINUTE, 0);
                            todayCal.set(Calendar.SECOND, 0);
                            todayCal.set(Calendar.MILLISECOND, 0);
                            Date dateOnlyToday = todayCal.getTime();
                            shouldDisplay = "Pending".equalsIgnoreCase(actDetail.status) && activityDate.before(dateOnlyToday);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }

            if (shouldDisplay && actDetail != null) {
                String text = "<html><center>" + actDetail.title + "</center></html>";
                JLabel act = new JLabel(text, SwingConstants.CENTER);
                act.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                cell.add(act, BorderLayout.CENTER);
            }

            cell.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            cell.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    showActivityPopup(e, key, cell);
                }

                public void mouseEntered(MouseEvent e) {
                    cell.setBackground(new Color(255, 222, 239));
                }

                public void mouseExited(MouseEvent e) {
                    if (sdf.format(today.getTime()).equals(key)) {
                        cell.setBackground(new Color(255, 180, 218));
                    } else {
                        cell.setBackground(Color.WHITE);
                    }
                }
            });

            calendarPanel.add(cell);
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private void filterTableByStatus(String statusFilter) {
        DefaultTableModel tableModel = (DefaultTableModel) activitytable.getModel();
        tableModel.setRowCount(0);

        List<Map.Entry<String, ActivityDetails>> sortedList = new ArrayList<>(activities.entrySet());
        sortedList.sort(Comparator.comparing(entry -> {
            try {
                return sdf.parse(entry.getKey());
            } catch (Exception e) {
                return new Date();
            }
        }));

        Date today = new Date();
        boolean hasData = false;

        for (Map.Entry<String, ActivityDetails> entry : sortedList) {
            String dateStr = entry.getKey();
            ActivityDetails detail = entry.getValue();

            try {
                Date activityDate = sdf.parse(dateStr);

                switch (statusFilter.toLowerCase()) {
                    case "all":
                        tableModel.addRow(new String[]{detail.title, dateStr + " - " + detail.status});
                        hasData = true;
                        break;
                    case "approved":
                        if ("Approved".equalsIgnoreCase(detail.status)) {
                            tableModel.addRow(new String[]{detail.title, dateStr});
                            hasData = true;
                        }
                        break;
                    case "pending":
                        if ("Pending".equalsIgnoreCase(detail.status) && (activityDate.equals(today) || activityDate.after(today))) {
                            tableModel.addRow(new String[]{detail.title, dateStr});
                            hasData = true;
                        }
                        break;
                    case "reschedule":
                        Calendar todayCal = Calendar.getInstance();
                        todayCal.set(Calendar.HOUR_OF_DAY, 0);
                        todayCal.set(Calendar.MINUTE, 0);
                        todayCal.set(Calendar.SECOND, 0);
                        todayCal.set(Calendar.MILLISECOND, 0);
                        Date dateOnlyToday = todayCal.getTime();

                        if ("Pending".equalsIgnoreCase(detail.status) && activityDate.before(dateOnlyToday)) {
                            tableModel.addRow(new String[]{detail.title, dateStr});
                            hasData = true;
                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        CardLayout cl = (CardLayout) mainPanel.getLayout();
        if (!hasData) {
            String noDataCard = "noData" + statusFilter.substring(0, 1).toUpperCase() + statusFilter.substring(1);
            cl.show(mainPanel, noDataCard);
        } else {
            cl.show(mainPanel, statusFilter);
        }
    }

    private void showActivityPopup(MouseEvent e, String dateKey, Component parent) {
        JPopupMenu popup = new JPopupMenu();
        popup.setLayout(new BorderLayout());
        popup.setBackground(Color.WHITE);

        JPanel popupBG = new JPanel(new GridBagLayout());
        popupBG.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        popupBG.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 5, 3, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel popupTitle = new JLabel("Activity for " + dateKey);
        popupTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        popupTitle.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        popupBG.add(popupTitle, gbc);

        ActivityDetails details = activities.get(dateKey);

        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;

        if (details != null) {
            gbc.gridy++;
            gbc.gridx = 0;
            popupBG.add(makeFieldLabel("Title:"), gbc);
            gbc.gridx = 1;
            popupBG.add(makeFieldValue(details.title), gbc);

            gbc.gridy++;
            gbc.gridx = 0;
            popupBG.add(makeFieldLabel("Fund:"), gbc);
            gbc.gridx = 1;
            popupBG.add(makeFieldValue(details.fund), gbc);

            gbc.gridy++;
            gbc.gridx = 0;
            popupBG.add(makeFieldLabel("Status:"), gbc);
            gbc.gridx = 1;
            popupBG.add(makeFieldValue(details.status), gbc);

            gbc.gridy++;
            gbc.gridx = 0;
            popupBG.add(makeFieldLabel("Description:"), gbc);

            gbc.gridx = 1;
            JTextArea description = new JTextArea(details.description);
            description.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            description.setWrapStyleWord(true);
            description.setLineWrap(true);
            description.setEditable(false);
            description.setOpaque(false);
            description.setBackground(null);
            description.setBorder(null);
            description.setMaximumSize(new Dimension(250, 80));
            popupBG.add(description, gbc);
        } else {
            gbc.gridy++;
            gbc.gridx = 0;
            gbc.gridwidth = 2;
            JLabel noActivity = new JLabel("No activity.");
            noActivity.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            popupBG.add(noActivity, gbc);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        buttonPanel.setBackground(Color.WHITE);
        JButton editBtn = new JButton(details != null ? "Edit" : "Add");
        JButton delBtn = new JButton("Delete");

        styleMiniButton(editBtn);
        styleMiniButton(delBtn);

        boolean isPastDate = false;
        try {
            Date activityDate = sdf.parse(dateKey);
            Calendar todayCal = Calendar.getInstance();
            todayCal.set(Calendar.HOUR_OF_DAY, 0);
            todayCal.set(Calendar.MINUTE, 0);
            todayCal.set(Calendar.SECOND, 0);
            todayCal.set(Calendar.MILLISECOND, 0);
            Date dateOnlyToday = todayCal.getTime();
            isPastDate = activityDate.before(dateOnlyToday);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (details == null && isPastDate) {
            editBtn.setEnabled(false);
            editBtn.setBackground(new Color(200, 200, 200));
            editBtn.setToolTipText("Cannot add activities to past dates");
        }

        editBtn.addActionListener(ev -> {
            popup.setVisible(false);
            showEditDialog(dateKey);
        });

        delBtn.addActionListener(ev -> {
            activities.remove(dateKey);
            deleteActivityFromDB(dateKey);
            popup.setVisible(false);
            loadActivitiesFromDB();
            refreshCalendar();
        });

        buttonPanel.add(editBtn);
        if (details != null) buttonPanel.add(delBtn);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        popupBG.add(buttonPanel, gbc);

        popup.add(popupBG);
        popup.pack();

        Window window = SwingUtilities.getWindowAncestor(parent);
        if (window == null) return;

        Rectangle windowBounds = window.getBounds();
        Point parentOnScreen = parent.getLocationOnScreen();
        Dimension popupSize = popup.getPreferredSize();

        int x = e.getX();
        int y = e.getY();

        int screenX = parentOnScreen.x + x;
        int screenY = parentOnScreen.y + y;

        int adjustedX = x;
        int adjustedY = y;

        if (screenY + popupSize.height > windowBounds.y + windowBounds.height) {
            adjustedY = y - popupSize.height;
        }

        if (screenY + adjustedY < windowBounds.y) {
            adjustedY = windowBounds.y - parentOnScreen.y + 10;
        }

        if (screenX + popupSize.width > windowBounds.x + windowBounds.width) {
            adjustedX = x - popupSize.width;
        }

        popup.show(parent, adjustedX, adjustedY);
    }

    private JLabel makeFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return label;
    }

    private JLabel makeFieldValue(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        return label;
    }

    public void showEditDialog(String dateKey) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(238, 235, 235));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 4, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        ActivityDetails existing = activities.get(dateKey);

        JTextField titleField = new JTextField(existing != null ? existing.title : "");
        titleField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)));
        JTextField fundField = new JTextField(existing != null ? existing.fund : "");
        fundField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)));
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Approved", "Pending"});
        if (existing != null) statusBox.setSelectedItem(existing.status);

        JTextArea descArea = new JTextArea(existing != null ? existing.description : "");
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)));
        JScrollPane scroll = new JScrollPane(descArea);
        scroll.setPreferredSize(new Dimension(250, 80));
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

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

            boolean isPastDate = false;
            try {
                Date activityDate = sdf.parse(dateKey);
                Calendar todayCal = Calendar.getInstance();
                todayCal.set(Calendar.HOUR_OF_DAY, 0);
                todayCal.set(Calendar.MINUTE, 0);
                todayCal.set(Calendar.SECOND, 0);
                todayCal.set(Calendar.MILLISECOND, 0);
                Date dateOnlyToday = todayCal.getTime();
                isPastDate = activityDate.before(dateOnlyToday);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (existing == null && isPastDate) {
                JOptionPane.showMessageDialog(this, "Cannot add activities to past dates.", "Invalid Date", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!title.isEmpty() || !desc.isEmpty()) {
                ActivityDetails details = new ActivityDetails(title, fund, status, desc);
                activities.put(dateKey, details);
                saveActivityToDB(dateKey, details);
            } else {
                activities.remove(dateKey);
                deleteActivityFromDB(dateKey);
            }
            loadActivitiesFromDB();
            refreshCalendar();

            String cardName;
            JButton targetButton;

            if (status == null || status.isEmpty()) {
                cardName = "all";
                targetButton = getButtonForUnderline(allLine);
            } else if (status.equalsIgnoreCase("Approved")) {
                cardName = "approved";
                targetButton = getButtonForUnderline(approvedLine);
            } else if (status.equalsIgnoreCase("Pending")) {
                try {
                    Date activityDate = sdf.parse(dateKey);
                    Calendar todayCal = Calendar.getInstance();
                    todayCal.set(Calendar.HOUR_OF_DAY, 0);
                    todayCal.set(Calendar.MINUTE, 0);
                    todayCal.set(Calendar.SECOND, 0);
                    todayCal.set(Calendar.MILLISECOND, 0);
                    Date dateOnlyToday = todayCal.getTime();

                    cardName = activityDate.before(dateOnlyToday) ? "reschedule" : "pending";
                    targetButton = getButtonForUnderline(cardName.equals("reschedule") ? reschedLine : pendingLine);
                } catch (Exception e) {
                    e.printStackTrace();
                    cardName = "all";
                    targetButton = getButtonForUnderline(allLine);
                }
            } else {
                cardName = "all";
                targetButton = getButtonForUnderline(allLine);
            }

            if (targetButton == null) {
                cardName = "all";
                targetButton = getButtonForUnderline(allLine);
            }

            selectButton(targetButton);
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, cardName);
            updateActivityTableFromCard(cardName);
            filterTableByStatus(cardName);
        }
    }

    private JButton getButtonForUnderline(JPanel underline) {
        return underlineMap.keySet().stream()
                .filter(btn -> underlineMap.get(btn) == underline)
                .findFirst()
                .orElse(null);
    }

    private void styleMiniButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setBackground(new Color(255, 64, 169));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void showGoToDatePicker() {
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "MM-dd-yyyy"));

        Window parentWindow = SwingUtilities.getWindowAncestor(this);

        int getDate = JOptionPane.showConfirmDialog(
                parentWindow,
                dateSpinner,
                "Select a Date to Jump",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (getDate == JOptionPane.OK_OPTION) {
            Date selected = (Date) dateSpinner.getValue();
            calendar.setTime(selected);
            refreshCalendar();
        }
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS planner_activities (" +
                "activity_date DATE PRIMARY KEY, " +
                "title VARCHAR(255), " +
                "fund VARCHAR(50), " +
                "status VARCHAR(50), " +
                "description TEXT," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                ")";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to initialize database table.");
        }
    }

    private void loadActivitiesFromDB() {
        createTableIfNotExists();
        activities.clear();

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int retryCount = 3;

        while (retryCount > 0) {
            try (Connection conn = Database.getConnection()) {
                if (conn == null) {
                    System.err.println("Database connection is null. Retrying...");
                    retryCount--;
                    Thread.sleep(1000);
                    continue;
                }

                String query = "SELECT * FROM planner_activities WHERE YEAR(activity_date) = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, currentYear);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        String dateKey = sdf.format(rs.getDate("activity_date"));
                        ActivityDetails detail = new ActivityDetails(
                            rs.getString("title"),
                            rs.getString("fund"),
                            rs.getString("status"),
                            rs.getString("description")
                        );
                        activities.put(dateKey, detail);
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
            }
        }

        if (retryCount == 0) {
            JOptionPane.showMessageDialog(this, "Failed to connect to the database after multiple attempts. Check server status.");
        }

        filterTableByStatus("all");
    }

    private void saveActivityToDB(String dateKey, ActivityDetails details) {
        createTableIfNotExists();
        try (Connection conn = Database.getConnection()) {
            java.util.Date utilDate = sdf.parse(dateKey);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            String checkSql = "SELECT COUNT(*) FROM planner_activities WHERE activity_date = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setDate(1, sqlDate);
                ResultSet rs = checkStmt.executeQuery();
                rs.next();
                int count = rs.getInt(1);

                if (count == 0) {
                    String insertSql = "INSERT INTO planner_activities " +
                            "(activity_date, title, fund, status, description, updated_at) " +
                            "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setDate(1, sqlDate);
                        insertStmt.setString(2, details.title);
                        insertStmt.setString(3, details.fund);
                        insertStmt.setString(4, details.status);
                        insertStmt.setString(5, details.description);
                        insertStmt.executeUpdate();
                    }
                } else {
                    String updateSql = "UPDATE planner_activities SET title = ?, fund = ?, status = ?, description = ? WHERE activity_date = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setString(1, details.title);
                        updateStmt.setString(2, details.fund);
                        updateStmt.setString(3, details.status);
                        updateStmt.setString(4, details.description);
                        updateStmt.setDate(5, sqlDate);
                        updateStmt.executeUpdate();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save activity.");
        }
    }

    private void deleteActivityFromDB(String dateKey) {
        try (Connection conn = Database.getConnection()) {
            java.util.Date utilDate = sdf.parse(dateKey);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            String deleteSql = "DELETE FROM planner_activities WHERE activity_date = ?";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                deleteStmt.setDate(1, sqlDate);
                deleteStmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to delete activity.");
        }
    }

    private static class RoundedCell extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    public static class ActivityDetails {
        public String title;
        public String fund;
        public String status;
        public String description;

        public ActivityDetails(String title, String fund, String status, String description) {
            this.title = title;
            this.fund = fund;
            this.status = status;
            this.description = description;
        }

        @Override
        public String toString() {
            return title + " - " + status;
        }
    }

    public void styleScrollBar(JScrollPane scrollPane) {
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