package MainPage;

import homePage.*;

import java.awt.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Locale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import JDBC.Database;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class homePage extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable summaryTable;
    private JLabel checkPlanner;
    private JLabel newMember;
    private JTable recentWorksTable;
    private final JLabel monthLabel;
    private final Calendar calendar;
    private JLabel totalNumber;
    private JLabel totalMale;
    private JLabel totalFemale;
    private JPanel recentWorksPanel;
    private JPanel summarytablePanel;

    public homePage() {
        setLayout(null);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(238, 235, 235));
        calendar = Calendar.getInstance();
        
        JPanel activityPanel = new RoundedPanel();
        activityPanel.setBounds(30, 15, 755, 365);
        add(activityPanel);
        activityPanel.setLayout(null);
        
        JLabel SummaryTXT = new JLabel("Summary of Future Activities");
        SummaryTXT.setBounds(80, 12, 287, 30);
        SummaryTXT.setFont(new Font("Tahoma", Font.PLAIN, 22));
        activityPanel.add(SummaryTXT);
        
        JPanel activityTopBar = new RoundedPanel();
        activityTopBar.setBounds(21, 65, 713, 36);
        activityTopBar.setBackground(new Color(255, 64, 169));
        activityPanel.add(activityTopBar);
        activityTopBar.setLayout(null);
        
        JLabel activity = new JLabel("ACTIVITY");
        activity.setFont(new Font("Tahoma", Font.BOLD, 16));
        activity.setForeground(new Color(0, 0, 0));
        activity.setBounds(35, 10, 84, 13);
        activityTopBar.add(activity);
        
        JLabel date = new JLabel("DATE\r\n");
        date.setForeground(Color.BLACK);
        date.setFont(new Font("Tahoma", Font.BOLD, 16));
        date.setBounds(210, 10, 54, 13);
        activityTopBar.add(date);
        
        JLabel budget = new JLabel("BUDGET\r\n");
        budget.setForeground(Color.BLACK);
        budget.setFont(new Font("Tahoma", Font.BOLD, 16));
        budget.setBounds(385, 10, 72, 13);
        activityTopBar.add(budget);
        
        JLabel status = new JLabel("STATUS");
        status.setForeground(Color.BLACK);
        status.setFont(new Font("Tahoma", Font.BOLD, 16));
        status.setBounds(560, 10, 72, 13);
        activityTopBar.add(status);
        
        summarytablePanel = new RoundedPanel();
        summarytablePanel.setBounds(21, 111, 713, 207);
        summarytablePanel.setLayout(null);
        summarytablePanel.setBackground(new Color(255, 255, 255));
        activityPanel.add(summarytablePanel);
        
        summaryTable = new JTable(new DefaultTableModel(new String[]{"Title", "Date", "Fund", "Status"}, 0)) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                JTextArea area = new JTextArea(getValueAt(row, column).toString());
                area.setLineWrap(true);
                area.setWrapStyleWord(true);
                area.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                area.setBackground(Color.WHITE);
                area.setBorder(BorderFactory.createEmptyBorder(5, 20, 10, 10));
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
        summaryTable.setRowHeight(67);
        summaryTable.setShowGrid(false);
        summaryTable.setIntercellSpacing(new Dimension(20, 5));
        summaryTable.getColumnModel().getColumn(0).setPreferredWidth(250);
        summaryTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        summaryTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        summaryTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        summaryTable.setTableHeader(null);
        summaryTable.setBounds(3, 3, 705, 200);
        summarytablePanel.add(summaryTable);

        DefaultTableModel expModel = fetchPlannerActivities();
        if (expModel.getRowCount() > 0 && !expModel.getValueAt(0, 2).equals("No Planned Activities So Far")) {
            summaryTable.setModel(expModel);
        } else {
            JLabel noActivityLabel = new JLabel("No Planned Activities So Far");
            noActivityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            noActivityLabel.setHorizontalAlignment(SwingConstants.CENTER);
            noActivityLabel.setVerticalAlignment(SwingConstants.CENTER);
            noActivityLabel.setBounds(0, 0, 713, 207);
            summarytablePanel.add(noActivityLabel);
            summarytablePanel.remove(summaryTable);
        }
        
        JButton seeAllButton = new JButton("See All");
        seeAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seeAllActivityPage seeAll = new seeAllActivityPage();
                mainPage.instance.showDim();
                seeAll.setLocationRelativeTo(null);
                seeAll.setVisible(true);
            }
        });
        seeAllButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        seeAllButton.setBounds(330, 325, 82, 20);
        seeAllButton.setBorderPainted(false);
        seeAllButton.setContentAreaFilled(false);
        seeAllButton.setFocusPainted(false);
        seeAllButton.setOpaque(true);
        seeAllButton.setBackground(new Color(238, 235, 235));
        seeAllButton.setForeground(new Color(0, 0, 0));
        activityPanel.add(seeAllButton);
        
        ImageIcon sumLogo = new ImageIcon(homePage.class.getResource("/imgs/summary.png"));
        JLabel summaryLogo = new JLabel(sumLogo);
        summaryLogo.setBounds(25, 15, 40, 40);
        activityPanel.add(summaryLogo);
        
        JLabel summaryDescription = new JLabel("List of activities this year");
        summaryDescription.setFont(new Font("Tahoma", Font.PLAIN, 14));
        summaryDescription.setBounds(81, 38, 200, 13);
        activityPanel.add(summaryDescription);
        
        JPanel recentWorkPanel = new RoundedPanel();
        recentWorkPanel.setBounds(30, 390, 755, 280);
        add(recentWorkPanel);
        recentWorkPanel.setLayout(null);
        
        JPanel recentHeader = new RoundedPanel();
        recentHeader.setBackground(new Color(255, 64, 169));
        recentHeader.setBounds(20, 65, 713, 36);
        recentWorkPanel.add(recentHeader);
        recentHeader.setLayout(null);
        
        JLabel number = new JLabel("NO.");
        number.setFont(new Font("Tahoma", Font.BOLD, 16));
        number.setBounds(30, 10, 39, 13);
        recentHeader.add(number);
        
        JLabel works = new JLabel("WORKS");
        works.setFont(new Font("Tahoma", Font.BOLD, 16));
        works.setBounds(126, 10, 77, 13);
        recentHeader.add(works);
        
        JLabel dateModified = new JLabel("DATE MODIFIED");
        dateModified.setFont(new Font("Tahoma", Font.BOLD, 16));
        dateModified.setBounds(419, 10, 139, 13);
        recentHeader.add(dateModified);
        
        recentWorksPanel = new RoundedPanel();
        recentWorksPanel.setBackground(new Color(238, 235, 235));
        recentWorksPanel.setBounds(20, 106, 713, 165);
        recentWorksPanel.setLayout(null);
        recentWorkPanel.add(recentWorksPanel);
        
        recentWorksTable = new JTable(new DefaultTableModel(new String[]{"", "", ""}, 0)) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                JTextArea area = new JTextArea(getValueAt(row, column).toString());
                area.setLineWrap(true);
                area.setWrapStyleWord(true);
                area.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                area.setBackground(new Color(238, 235, 235));
                area.setBorder(BorderFactory.createEmptyBorder(15, 40, 10, 10));
                if (column == 3) {
                    if (getValueAt(row, column).toString().equalsIgnoreCase("Approved")) {
                        area.setForeground(new Color(255, 64, 169));
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
        recentWorksTable.setRowHeight(55);
        recentWorksTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        recentWorksTable.getColumnModel().getColumn(1).setPreferredWidth(210);
        recentWorksTable.getColumnModel().getColumn(2).setPreferredWidth(240);
        recentWorksTable.setShowGrid(false);
        recentWorksTable.setTableHeader(null);
        recentWorksTable.setIntercellSpacing(new Dimension(0, 0));
        recentWorksTable.setForeground(Color.BLACK);
        recentWorksTable.setBounds(3, 3, 705, 155);
        recentWorksPanel.add(recentWorksTable);

        JScrollPane scrollPane = new JScrollPane(recentWorksTable);
        scrollPane.setBounds(3, 3, 705, 155);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        styleScrollBar(scrollPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        recentWorksPanel.add(scrollPane);

        DefaultTableModel model = fetchRecentWorks();
        if (model.getRowCount() > 0 && !model.getValueAt(0, 1).equals("No Recent Works So Far")) {
            recentWorksTable.setModel(model);
        } else {
            JLabel noRecentWorksLabel = new JLabel("No Recent Works So Far");
            noRecentWorksLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            noRecentWorksLabel.setHorizontalAlignment(SwingConstants.CENTER);
            noRecentWorksLabel.setVerticalAlignment(SwingConstants.CENTER);
            noRecentWorksLabel.setBounds(0, 0, 713, 165);
            recentWorksPanel.add(noRecentWorksLabel);
            recentWorksPanel.remove(recentWorksTable); 
            recentWorksPanel.remove(scrollPane); 
        }
        
        JLabel recentWorks = new JLabel("Recent Works\r\n");
        recentWorks.setFont(new Font("Tahoma", Font.PLAIN, 22));
        recentWorks.setBounds(80, 10, 148, 30);
        recentWorkPanel.add(recentWorks);
        
        ImageIcon recLogo = new ImageIcon(homePage.class.getResource("/imgs/recent.png"));
        JLabel recentLogo = new JLabel(recLogo);
        recentLogo.setBounds(25, 15, 40, 40);
        recentWorkPanel.add(recentLogo);
        
        JLabel recentDescription = new JLabel("List of done activities this month ");
        recentDescription.setFont(new Font("Tahoma", Font.PLAIN, 14));
        recentDescription.setBounds(80, 38, 300, 13);
        recentWorkPanel.add(recentDescription);
        
        JPanel notifPanel = new RoundedPanel();
        notifPanel.setBounds(799, 120, 510, 287);
        add(notifPanel);
        notifPanel.setLayout(null);
        
        JLabel Notification = new JLabel("Notifications\r\n\r\n");
        Notification.setFont(new Font("Tahoma", Font.BOLD, 28));
        Notification.setBounds(77, 13, 220, 47);
        notifPanel.add(Notification);
        
        JPanel checkPlannerPanel = new RoundedPanel();
        checkPlannerPanel.setBackground(new Color(238, 235, 235));
        checkPlannerPanel.setBounds(26, 175, 462, 90);
        notifPanel.add(checkPlannerPanel);
        checkPlannerPanel.setLayout(null);
        
        RoundedButton checkPlannerBTN = new RoundedButton("");
        checkPlannerBTN.setBackground(new Color(238, 235, 235));
        checkPlannerBTN.setBounds(5, 5, 450, 80);
        checkPlannerBTN.setLayout(null);
        checkPlannerPanel.add(checkPlannerBTN);
        
        checkPlanner = new JLabel();
        checkPlannerBTN.add(checkPlanner);
        checkPlanner.setBounds(90, 15, 206, 29);
        checkPlanner.setBackground(new Color(238, 235, 235));
        checkPlanner.setText("Check your planner");
        checkPlanner.setFont(new Font("Tahoma", Font.PLAIN, 24));
        
        JLabel plannerDescription = new JLabel("You have planned activities for today");
        plannerDescription.setFont(new Font("Tahoma", Font.PLAIN, 12));
        plannerDescription.setBounds(92, 45, 250, 20);
        checkPlannerBTN.add(plannerDescription);
        
        JLabel plannerArrowLabel = new JLabel(">");
        plannerArrowLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
        plannerArrowLabel.setForeground(Color.BLACK);
        plannerArrowLabel.setBounds(410, 20, 30, 40);
        checkPlannerBTN.add(plannerArrowLabel);
        
        ImageIcon planLogo = new ImageIcon(homePage.class.getResource("/imgs/checkPlanner.png"));
        JLabel plannerLogo = new JLabel(planLogo);
        plannerLogo.setBounds(10, 5, 70, 70);
        checkPlannerBTN.add(plannerLogo);
        
        checkPlannerBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkPlanner planner = new checkPlanner();
                mainPage.instance.showDim();
                planner.setLocationRelativeTo(null);
                planner.setVisible(true);
            }
        });
        
        RoundedPanel newMemberAddedPanel = new RoundedPanel();
        newMemberAddedPanel.setLayout(null);
        newMemberAddedPanel.setBackground(new Color(238, 235, 235));
        newMemberAddedPanel.setBounds(26, 71, 462, 90);
        notifPanel.add(newMemberAddedPanel);
        
        RoundedButton newMemberBTN = new RoundedButton("");
        newMemberBTN.setBackground(new Color(238, 235, 235));
        newMemberBTN.setBounds(5, 5, 450, 80);
        newMemberBTN.setLayout(null);
        newMemberAddedPanel.add(newMemberBTN);
        
        newMember = new JLabel();
        newMember.setText("New Member Added");
        newMember.setFont(new Font("Tahoma", Font.PLAIN, 24));
        newMember.setBackground(new Color(238, 235, 235));
        newMember.setBounds(90, 15, 250, 29);
        newMemberBTN.add(newMember);
        
        JLabel memberArrowLabel = new JLabel(">");
        memberArrowLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
        memberArrowLabel.setForeground(Color.BLACK);
        memberArrowLabel.setBounds(410, 20, 30, 40);
        newMemberBTN.add(memberArrowLabel);
        
        JLabel newDescription = new JLabel("Newly registered Solo Parent member");
        newDescription.setFont(new Font("Tahoma", Font.PLAIN, 12));
        newDescription.setBounds(92, 43, 300, 20);
        newMemberBTN.add(newDescription);
        
        ImageIcon newMemLogo = new ImageIcon(homePage.class.getResource("/imgs/newMember.png"));
        JLabel newMemberLogo = new JLabel(newMemLogo);
        newMemberLogo.setBounds(10, 5, 70, 70);
        newMemberBTN.add(newMemberLogo);
        
        ImageIcon notiLogo = new ImageIcon(homePage.class.getResource("/imgs/notification.png"));
        JLabel notifLogo = new JLabel(notiLogo);
        notifLogo.setBounds(25, 15, 45, 45);
        notifPanel.add(notifLogo);
        
        newMemberBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newMember member = new newMember();
                mainPage.instance.showDim();
                member.setLocationRelativeTo(null);
                member.setVisible(true);
            }
        });
        
        JPanel totalmemberPanel = new RoundedPanel();
        totalmemberPanel.setBackground(new Color(39, 39, 39));
        totalmemberPanel.setBounds(799, 415, 250, 255);
        add(totalmemberPanel);
        totalmemberPanel.setLayout(null);
        
        JPanel soloParentLine = new JPanel();
        soloParentLine.setBounds(0, 60, 250, 2);
        totalmemberPanel.add(soloParentLine);
        
        JLabel total_solo_parent = new JLabel("Total Registered Solo Parent\r\n");
        total_solo_parent.setFont(new Font("Tahoma", Font.PLAIN, 16));
        total_solo_parent.setForeground(new Color(255, 255, 255));
        total_solo_parent.setBounds(10, 10, 230, 26);
        totalmemberPanel.add(total_solo_parent);
        
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        String monthName = new DateFormatSymbols().getMonths()[month];
        
        JLabel solo_parent_date = new JLabel("as of " + monthName + " " + year);
        solo_parent_date.setFont(new Font("Tahoma", Font.PLAIN, 12));
        solo_parent_date.setForeground(new Color(255, 255, 255));
        solo_parent_date.setBounds(13, 37, 93, 13);
        totalmemberPanel.add(solo_parent_date);
        
        totalNumber = new JLabel("0");
        totalNumber.setHorizontalAlignment(SwingConstants.CENTER);
        totalNumber.setFont(new Font("Tahoma", Font.PLAIN, 100));
        totalNumber.setForeground(new Color(255, 255, 255));
        totalNumber.setBounds(60, 85, 131, 96);
        totalmemberPanel.add(totalNumber);
        
        JLabel soloParent = new JLabel("SOLO PARENTS");
        soloParent.setForeground(new Color(255, 255, 255));
        soloParent.setFont(new Font("Tahoma", Font.BOLD, 26));
        soloParent.setBounds(25, 200, 210, 26);
        totalmemberPanel.add(soloParent);
        
        JPanel genderPanel = new RoundedPanel();
        genderPanel.setBackground(new Color(39, 39, 39));
        genderPanel.setBounds(1059, 415, 250, 255);
        add(genderPanel);
        genderPanel.setLayout(null);
        
        JPanel genderLine = new JPanel();
        genderLine.setBounds(0, 60, 250, 2);
        genderPanel.add(genderLine);
        
        JLabel total_by_sex = new JLabel("Total Registered By Sex");
        total_by_sex.setForeground(Color.WHITE);
        total_by_sex.setFont(new Font("Tahoma", Font.PLAIN, 16));
        total_by_sex.setBounds(10, 10, 230, 26);
        genderPanel.add(total_by_sex);
        
        JLabel gender_date = new JLabel("as of " + monthName + " " + year);
        gender_date.setForeground(Color.WHITE);
        gender_date.setFont(new Font("Tahoma", Font.PLAIN, 12));
        gender_date.setBounds(10, 37, 93, 13);
        genderPanel.add(gender_date);
        
        JPanel genderLine1 = new JPanel();
        genderLine1.setBounds(0, 155, 250, 2);
        genderPanel.add(genderLine1);
        
        totalMale = new JLabel("0");
        totalMale.setHorizontalAlignment(SwingConstants.CENTER);
        totalMale.setFont(new Font("Tahoma", Font.BOLD, 60));
        totalMale.setForeground(new Color(4, 220, 251));
        totalMale.setBounds(121, 65, 95, 57);
        genderPanel.add(totalMale);
        
        JLabel male = new JLabel("MALE");
        male.setFont(new Font("Tahoma", Font.BOLD, 20));
        male.setForeground(new Color(4, 220, 251));
        male.setBounds(145, 125, 67, 26);
        genderPanel.add(male);
        
        totalFemale = new JLabel("0");
        totalFemale.setHorizontalAlignment(SwingConstants.CENTER);
        totalFemale.setForeground(new Color(255, 64, 169));
        totalFemale.setFont(new Font("Tahoma", Font.BOLD, 60));
        totalFemale.setBounds(121, 160, 95, 57);
        genderPanel.add(totalFemale);
        
        JLabel female = new JLabel("FEMALE");
        female.setForeground(new Color(255, 64, 169));
        female.setFont(new Font("Tahoma", Font.BOLD, 20));
        female.setBounds(131, 219, 81, 26);
        genderPanel.add(female);
        
        ImageIcon mLogo = new ImageIcon(homePage.class.getResource("/imgs/male.png"));
        JLabel maleLogo = new JLabel(mLogo);
        maleLogo.setBounds(25, 70, 80, 80);
        genderPanel.add(maleLogo);
        
        ImageIcon fLogo = new ImageIcon(homePage.class.getResource("/imgs/female.png"));
        JLabel femaleLogo = new JLabel(fLogo);
        femaleLogo.setBounds(25, 165, 80, 80);
        genderPanel.add(femaleLogo);
        
        JPanel datePanel = new RoundedPanel();
        datePanel.setBackground(new Color(255, 64, 169));
        datePanel.setBounds(799, 15, 510, 95);
        add(datePanel);
        datePanel.setLayout(null);
        
        monthLabel = new JLabel(" ", SwingConstants.CENTER);
        monthLabel.setForeground(new Color(255, 255, 255));
        monthLabel.setFont(new Font("Segoe UI", Font.BOLD, 45));
        monthLabel.setBounds(10, 0, 490, 60);
        datePanel.add(monthLabel);
        
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        String dayName = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        
        JLabel day = new JLabel(dayName);
        day.setHorizontalAlignment(SwingConstants.CENTER);
        day.setFont(new Font("Tahoma", Font.PLAIN, 24));
        day.setBounds(105, 60, 307, 30);
        day.setForeground(new Color(255, 255, 255));
        updateMonthLabel();
        datePanel.add(day);
        
        int[] genderCounts = fetchGenderCounts();
        totalNumber.setText(String.valueOf(genderCounts[0]));
        totalMale.setText(String.valueOf(genderCounts[1]));
        totalFemale.setText(String.valueOf(genderCounts[2]));
        
        Timer refreshTimer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshDashboardData(); 
            }
        });
        refreshTimer.setRepeats(true);
        refreshTimer.start();
    }
    
    public void refreshDashboardData() {
        int[] genderCounts = fetchGenderCounts();
        totalNumber.setText(String.valueOf(genderCounts[0]));
        totalMale.setText(String.valueOf(genderCounts[1]));
        totalFemale.setText(String.valueOf(genderCounts[2]));

        DefaultTableModel expModel = fetchPlannerActivities();
        summaryTable.setModel(expModel); 
        if (expModel.getRowCount() == 0 || expModel.getValueAt(0, 2).equals("No Planned Activities So Far")) {
            JLabel noActivityLabel = new JLabel("No Planned Activities So Far");
            noActivityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            noActivityLabel.setHorizontalAlignment(SwingConstants.CENTER);
            noActivityLabel.setVerticalAlignment(SwingConstants.CENTER);
            noActivityLabel.setBounds(0, 0, 713, 207);
            summarytablePanel.add(noActivityLabel);
            summarytablePanel.remove(summaryTable);
        } else {
            summarytablePanel.removeAll(); 
            summarytablePanel.add(summaryTable);
            summaryTable.setBounds(3, 3, 705, 200);
        }

        DefaultTableModel model = fetchRecentWorks();
        recentWorksTable.setModel(model); 
        if (model.getRowCount() == 0 || model.getValueAt(0, 1).equals("No Recent Works So Far")) {
            JLabel noRecentWorksLabel = new JLabel("No Recent Works So Far");
            noRecentWorksLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            noRecentWorksLabel.setHorizontalAlignment(SwingConstants.CENTER);
            noRecentWorksLabel.setVerticalAlignment(SwingConstants.CENTER);
            noRecentWorksLabel.setBounds(0, 0, 713, 165);
            recentWorksPanel.add(noRecentWorksLabel);
            recentWorksPanel.remove(recentWorksTable);
            recentWorksPanel.remove(recentWorksPanel.getComponent(1)); 
        } else {
            recentWorksPanel.removeAll(); 
            recentWorksPanel.add(recentWorksTable);
            recentWorksTable.setBounds(3, 3, 705, 155);
            JScrollPane scrollPane = new JScrollPane(recentWorksTable);
            scrollPane.setBounds(3, 3, 705, 155);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            styleScrollBar(scrollPane);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            recentWorksPanel.add(scrollPane);
        }
        revalidate();
        repaint();
    }
    
    private void updateMonthLabel() {
        monthLabel.setText(new SimpleDateFormat("MMMM dd, yyyy").format(calendar.getTime()));
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

    public static DefaultTableModel fetchPlannerActivities() {
        String[] columns = {"Title", "Date", "Fund", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        String startOfYear = currentYear + "-01-01";
        String endOfYear = currentYear + "-12-31";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "SELECT title, activity_date, fund, status " +
                "FROM planner_activities " +
                "WHERE activity_date BETWEEN ? AND ? " +
                "ORDER BY activity_date ASC")) {

            stmt.setString(1, startOfYear);
            stmt.setString(2, endOfYear);

            ResultSet rs = stmt.executeQuery();
            boolean hasResults = false;

            while (rs.next()) {
                hasResults = true;
                String title = rs.getString("title");
                String date = rs.getString("activity_date");
                String fund = "₱ " + rs.getString("fund");
                String status = rs.getString("status");

                model.addRow(new Object[]{title, date, fund, status});
            }

            if (!hasResults) {
                model.addRow(new Object[]{"", "", "No Planned Activities So Far", ""});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }
    
    private int[] fetchGenderCounts() {
        int[] counts = new int[3]; 
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT sex, COUNT(*) as count FROM applicant_information GROUP BY sex");
             ResultSet rs = stmt.executeQuery()) {

            int total = 0, male = 0, female = 0;
            while (rs.next()) {
                String sex = rs.getString("sex");
                int count = rs.getInt("count");
                total += count;
                if ("Male".equalsIgnoreCase(sex)) {
                    male = count;
                } else if ("Female".equalsIgnoreCase(sex)) {
                    female = count;
                }
            }
            counts[0] = total;
            counts[1] = male;
            counts[2] = female;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return counts;
    }
    
    public static DefaultTableModel fetchRecentWorks() {
        String[] columns = {"", "", ""};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT title, activity_date " +
                 "FROM planner_activities " +
                 "WHERE activity_date < CURRENT_DATE AND status = 'Approved' " +
                 "ORDER BY activity_date DESC LIMIT 10")
        ) {
            ResultSet rs = stmt.executeQuery();
            int count = 1;
            boolean hasResults = false;

            while (rs.next()) {
                hasResults = true;
                String title = rs.getString("title");
                String date = rs.getString("activity_date");
                model.addRow(new Object[]{count++, title, date});
            }

            if (!hasResults) {
                model.addRow(new Object[]{"", "No Recent Works So Far", ""});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
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