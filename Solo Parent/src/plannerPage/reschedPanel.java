package plannerPage;

import MainPage.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class reschedPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTable activitytable;
    private final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    private DefaultTableModel tableModel;

    public reschedPanel() {
        setLayout(null);
        setBackground(new Color(255, 255, 255));
        setName("resched");

        String[] columns = {"Activity", "Date"};
        tableModel = new DefaultTableModel(columns, 0);

        activitytable = new JTable(tableModel) {
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
                    area.setForeground(Color.GRAY);
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
                    String dateStr = activitytable.getValueAt(selectedRow, 1).toString();
                    String dateKey = dateStr.contains(" - ") ? dateStr.split(" - ")[0] : dateStr;
                    plannerPage.instance.showEditDialog(dateKey);
                }
            }
        });

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        activitytable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        activitytable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        activitytable.getColumnModel().getColumn(0).setPreferredWidth(150);
        activitytable.getColumnModel().getColumn(1).setPreferredWidth(100);

        activitytable.setRowHeight(70);
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