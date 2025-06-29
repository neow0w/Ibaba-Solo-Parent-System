package recordPage;
import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;


public class familyCompositionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable familyTable;
    private DefaultTableModel tableModel;
    
	public familyCompositionPanel() {
		setBackground(new Color(238, 235, 235));
		setBounds(0, 25, 1110, 735);
		setLayout(null);
		
		String[] columns = {
                "Full Name", "Relationship", "Age", "Birthdate",
                "Civil Status", "Educational Attainment", "Occupation", "Monthly Income"
        };

        tableModel = new DefaultTableModel(columns, 17);
        familyTable = new JTable(tableModel) {
            public TableCellRenderer getCellRenderer(int row, int column) {
                return new TextAreaRenderer();
            }
            
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };
        
        HeaderRenderer headerRenderer = new HeaderRenderer();
        for (int i = 0; i < familyTable.getColumnCount(); i++) {
        	familyTable.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        
        JTableHeader header = familyTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(255, 105, 180)); 
        header.setForeground(Color.WHITE);
        header.setOpaque(true);
        header.setBounds(40, 122, 1030, 55);
        add(header);

        familyTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        familyTable.setRowHeight(41);
        familyTable.setBounds(40, 177, 1030, 535);
        familyTable.setGridColor(new Color(200, 200, 200));
        familyTable.setShowGrid(true);
        familyTable.setRowSelectionAllowed(false);
        familyTable.setCellSelectionEnabled(false);
        familyTable.setSelectionBackground(familyTable.getBackground());
        familyTable.setSelectionForeground(familyTable.getForeground());
        add(familyTable);

        int[] columnWidths = {150, 120, 50, 100, 100, 160, 120, 100};
        for (int i = 0; i < columnWidths.length; i++) {
        	familyTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        DefaultCellEditor input = new DefaultCellEditor(new JTextField());
        input.setClickCountToStart(1);
        for (int i = 0; i < familyTable.getColumnCount(); i++) {
        	familyTable.getColumnModel().getColumn(i).setCellEditor(input);
        }

        add(familyTable);

		JLabel applicantInformation = new JLabel("Applicant Information");
		applicantInformation.setFont(new Font("Tahoma", Font.BOLD, 14));
		applicantInformation.setBounds(215, 60, 165, 30);
		add(applicantInformation);
		
		JPanel firstProcessPanel = new JPanel();
		firstProcessPanel.setBackground(new Color(189, 189, 189));
		firstProcessPanel.setBounds(275, 20, 40, 40);
		add(firstProcessPanel);
		firstProcessPanel.setLayout(null);
		
		JLabel firstProcess = new JLabel("1");
		firstProcess.setForeground(new Color(0, 0, 0));
		firstProcess.setHorizontalAlignment(SwingConstants.CENTER);
		firstProcess.setFont(new Font("Tahoma", Font.BOLD, 16));
		firstProcess.setBounds(5, 10, 30, 20);
		firstProcessPanel.add(firstProcess);
		
		JPanel secondProcessPanel = new JPanel();
		secondProcessPanel.setLayout(null);
		secondProcessPanel.setBackground(new Color(255, 64, 169));
		secondProcessPanel.setBounds(540, 20, 40, 40);
		add(secondProcessPanel);
		
		JLabel secondProcess = new JLabel("2");
		secondProcess.setHorizontalAlignment(SwingConstants.CENTER);
		secondProcess.setForeground(new Color(255, 255, 255));
		secondProcess.setFont(new Font("Tahoma", Font.BOLD, 16));
		secondProcess.setBounds(5, 10, 30, 20);
		secondProcessPanel.add(secondProcess);
		
		JLabel familyComposition = new JLabel("Family Composition");
		familyComposition.setFont(new Font("Tahoma", Font.BOLD, 14));
		familyComposition.setBounds(490, 60, 145, 30);
		add(familyComposition);
		
		JPanel thirdProcessPanel = new JPanel();
		thirdProcessPanel.setLayout(null);
		thirdProcessPanel.setBackground(new Color(189, 189, 189));
		thirdProcessPanel.setBounds(795, 20, 40, 40);
		add(thirdProcessPanel);
		
		JLabel thirdPanel = new JLabel("3");
		thirdPanel.setHorizontalAlignment(SwingConstants.CENTER);
		thirdPanel.setForeground(Color.BLACK);
		thirdPanel.setFont(new Font("Tahoma", Font.BOLD, 16));
		thirdPanel.setBounds(5, 10, 30, 20);
		thirdProcessPanel.add(thirdPanel);
		
		JLabel classification = new JLabel("Classification");
		classification.setFont(new Font("Tahoma", Font.BOLD, 14));
		classification.setBounds(770, 60, 95, 30);
		add(classification);
		
		JPanel line1 = new JPanel();
		line1.setBackground(new Color(128, 128, 128));
		line1.setBounds(335, 40, 180, 4);
		add(line1);
		
		JPanel line2 = new JPanel();
		line2.setBackground(new Color(128, 128, 128));
		line2.setBounds(595, 40, 180, 4);
		add(line2);
		
		JPanel hrLine1 = new JPanel();
		hrLine1.setBackground(new Color(104, 104, 104));
		hrLine1.setBounds(30, 110, 1050, 2);
		add(hrLine1);
	}
    public JTable getTable() {
        return familyTable;
    }

}

class TextAreaRenderer extends JTextArea implements TableCellRenderer {
    public TextAreaRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value == null ? "" : value.toString());
        setFont(table.getFont());
        setForeground(table.getForeground());
        setBackground(table.getBackground());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return this;
    }
}

class HeaderRenderer extends JTextArea implements TableCellRenderer {
    public HeaderRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true);
        setEditable(false);
        setFocusable(false);
        setFont(new Font("Segoe UI", Font.BOLD, 15));
        setBackground(new Color(255, 105, 180));
        setForeground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value == null ? "" : value.toString());
        return this;
    }
}
