package plannerPage;

import MainPage.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class approvedPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTable activitytable;
    private final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

    public approvedPanel() {
        setLayout(null);
        setBackground(new Color(255, 255, 255));
        setName("approved"); 

        String[][] data = {
                {"Wheelchair Distribution", "Approved"},
        };
        String[] columns = {"", ""};
        DefaultTableModel expModel = new DefaultTableModel(data, columns);

        activitytable = new JTable(expModel) {
            @Override
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
                if (column == 1) {
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

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        activitytable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = activitytable.getSelectedRow();
                if (selectedRow != -1) {
                    String todayKey = sdf.format(Calendar.getInstance().getTime());
                    plannerPage.instance.showEditDialog(todayKey);
                }
            }
        });

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        activitytable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        activitytable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        activitytable.getColumnModel().getColumn(0).setPreferredWidth(250);
        activitytable.getColumnModel().getColumn(1).setPreferredWidth(150);

        activitytable.setRowHeight(57);
        activitytable.setShowGrid(false);
        activitytable.setIntercellSpacing(new Dimension(0, 0));
        activitytable.setTableHeader(null);

        JScrollPane scrollPane = new JScrollPane(activitytable);
        scrollPane.setBounds(0, 0, 430, 500);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        plannerPage.instance.styleScrollBar(scrollPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane);
    }

    public JTable getTable() {
        return activitytable;
    }
}