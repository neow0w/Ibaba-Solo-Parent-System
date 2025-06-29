package recordPage;
import java.awt.*;
import javax.swing.*;

public class classificationPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField dateofWeddingBar, dateofSeparationBar, othersBar;
	private Icon checkBox;
	private JCheckBox unmarriedAbandoned, UnmarriedSeparated, deathofPartner, marriedAbandoned, annulled, deFacto;
	private JCheckBox LegallySeparated, divorced, widow, others;


	public classificationPanel() {
		setBackground(new Color(238, 235, 235));
		setBounds(0, 25, 1110, 735);
		setLayout(null);
			
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
		secondProcessPanel.setBackground(new Color(189, 189, 189));
		secondProcessPanel.setBounds(540, 20, 40, 40);
		add(secondProcessPanel);
		
		JLabel secondProcess = new JLabel("2");
		secondProcess.setHorizontalAlignment(SwingConstants.CENTER);
		secondProcess.setForeground(new Color(0, 0, 0));
		secondProcess.setFont(new Font("Tahoma", Font.BOLD, 16));
		secondProcess.setBounds(5, 10, 30, 20);
		secondProcessPanel.add(secondProcess);
		
		JLabel familyComposition = new JLabel("Family Composition");
		familyComposition.setFont(new Font("Tahoma", Font.BOLD, 14));
		familyComposition.setBounds(490, 60, 145, 30);
		add(familyComposition);
		
		JPanel thirdProcessPanel = new JPanel();
		thirdProcessPanel.setLayout(null);
		thirdProcessPanel.setBackground(new Color(255, 64, 169));
		thirdProcessPanel.setBounds(795, 20, 40, 40);
		add(thirdProcessPanel);
		
		JLabel thirdProcess = new JLabel("3");
		thirdProcess.setHorizontalAlignment(SwingConstants.CENTER);
		thirdProcess.setForeground(new Color(255, 255, 255));
		thirdProcess.setFont(new Font("Tahoma", Font.BOLD, 16));
		thirdProcess.setBounds(5, 10, 30, 20);
		thirdProcessPanel.add(thirdProcess);
		
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
		
		JLabel classification_circumstances = new JLabel("Classification/ Circumstances of being a Solo Parent");
		classification_circumstances.setFont(new Font("Tahoma", Font.BOLD, 20));
		classification_circumstances.setBounds(30, 120, 540, 30);
		add(classification_circumstances);
		
		JLabel description = new JLabel("How the individual became a solo parent - check as applicable");
		description.setFont(new Font("Tahoma", Font.PLAIN, 16));
		description.setBounds(30, 155, 451, 20);
		add(description);
		
		unmarriedAbandoned = new JCheckBox("Unmarried, Abandoned (hindi kasal, inabandona)");
		unmarriedAbandoned.setBackground(new Color(238, 235, 235));
		unmarriedAbandoned.setFont(new Font("Tahoma", Font.PLAIN, 18));
		unmarriedAbandoned.setBounds(50, 185, 430, 30);
		checkBox = createCustomCheckboxIcon(new Color(0,0,0));
		unmarriedAbandoned.setIcon(checkBox);
		unmarriedAbandoned.setSelectedIcon(checkBox);
		add(unmarriedAbandoned);
		
		UnmarriedSeparated = new JCheckBox("Unmarried, Separated (hindi kasal, naghiwalay)");
		UnmarriedSeparated.setFont(new Font("Tahoma", Font.PLAIN, 18));
		UnmarriedSeparated.setBackground(new Color(238, 235, 235));
		UnmarriedSeparated.setBounds(50, 230, 430, 30);
		checkBox = createCustomCheckboxIcon(new Color(0,0,0));
		UnmarriedSeparated.setIcon(checkBox);
		UnmarriedSeparated.setSelectedIcon(checkBox);
		add(UnmarriedSeparated);
		
		deathofPartner = new JCheckBox("Unmarried, Death of Partner (hindi kasal, namatay ang partner)");
		deathofPartner.setFont(new Font("Tahoma", Font.PLAIN, 18));
		deathofPartner.setBackground(new Color(238, 235, 235));
		deathofPartner.setBounds(50, 275, 550, 30);
		checkBox = createCustomCheckboxIcon(new Color(0,0,0));
		deathofPartner.setIcon(checkBox);
		deathofPartner.setSelectedIcon(checkBox);
		add(deathofPartner);
		
		marriedAbandoned = new JCheckBox("Married, Abandoned (kasal, inabandona)");
		marriedAbandoned.setFont(new Font("Tahoma", Font.PLAIN, 18));
		marriedAbandoned.setBackground(new Color(238, 235, 235));
		marriedAbandoned.setBounds(50, 320, 355, 30);
		checkBox = createCustomCheckboxIcon(new Color(0,0,0));
		marriedAbandoned.setIcon(checkBox);
		marriedAbandoned.setSelectedIcon(checkBox);
		add(marriedAbandoned);
		
		JLabel dateofWedding = new JLabel("Petsa ng Kasal:");
		dateofWedding.setFont(new Font("Tahoma", Font.ITALIC, 16));
		dateofWedding.setBounds(150, 355, 117, 20);
		add(dateofWedding);
		
		dateofWeddingBar = new JTextField();
		dateofWeddingBar.setBounds(273, 356, 207, 20);
		add(dateofWeddingBar);
		dateofWeddingBar.setColumns(10);
		
		annulled = new JCheckBox("Married, Annulled (kasal, annulled)");
		annulled.setFont(new Font("Tahoma", Font.PLAIN, 18));
		annulled.setBackground(new Color(238, 235, 235));
		annulled.setBounds(50, 385, 310, 30);
		checkBox = createCustomCheckboxIcon(new Color(0,0,0));
		annulled.setIcon(checkBox);
		annulled.setSelectedIcon(checkBox);
		add(annulled);
		
		JLabel dateofSeparation = new JLabel("Petsa ng Paghihiwalay:");
		dateofSeparation.setFont(new Font("Tahoma", Font.ITALIC, 16));
		dateofSeparation.setBounds(150, 425, 175, 20);
		add(dateofSeparation);
		
		dateofSeparationBar = new JTextField();
		dateofSeparationBar.setColumns(10);
		dateofSeparationBar.setBounds(335, 425, 207, 20);
		add(dateofSeparationBar);
		
		deFacto = new JCheckBox("Married, Separation de Facto (kasal, impormal na naghiwalay)");
		deFacto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		deFacto.setBackground(new Color(238, 235, 235));
		deFacto.setBounds(50, 451, 550, 30);
		checkBox = createCustomCheckboxIcon(new Color(0,0,0));
		deFacto.setIcon(checkBox);
		deFacto.setSelectedIcon(checkBox);
		add(deFacto);
		
		LegallySeparated = new JCheckBox("Married, Legally Separated (kasal, pormal na naghiwalay)");
		LegallySeparated.setFont(new Font("Tahoma", Font.PLAIN, 18));
		LegallySeparated.setBackground(new Color(238, 235, 235));
		LegallySeparated.setBounds(50, 490, 490, 30);
		checkBox = createCustomCheckboxIcon(new Color(0,0,0));
		LegallySeparated.setIcon(checkBox);
		LegallySeparated.setSelectedIcon(checkBox);
		add(LegallySeparated);
		
		divorced = new JCheckBox("Married, Divorced (kasal, nagdiborsyo)");
		divorced.setFont(new Font("Tahoma", Font.PLAIN, 18));
		divorced.setBackground(new Color(238, 235, 235));
		divorced.setBounds(50, 530, 490, 30);
		checkBox = createCustomCheckboxIcon(new Color(0,0,0));
		divorced.setIcon(checkBox);
		divorced.setSelectedIcon(checkBox);
		add(divorced);
		
		widow = new JCheckBox("Married, Widow/er (kasal, namatay ang asawa)");
		widow.setFont(new Font("Tahoma", Font.PLAIN, 18));
		widow.setBackground(new Color(238, 235, 235));
		widow.setBounds(50, 570, 450, 30);
		checkBox = createCustomCheckboxIcon(new Color(0,0,0));
		widow.setIcon(checkBox);
		widow.setSelectedIcon(checkBox);
		add(widow);
		
		others = new JCheckBox("Others / Ibang Kadahilanan:");
		others.setFont(new Font("Tahoma", Font.PLAIN, 18));
		others.setBackground(new Color(238, 235, 235));
		others.setBounds(50, 610, 270, 30);
		checkBox = createCustomCheckboxIcon(new Color(0,0,0));
		others.setIcon(checkBox);
		others.setSelectedIcon(checkBox);
		add(others);
		
		othersBar = new JTextField();
		othersBar.setColumns(10);
		othersBar.setBounds(320, 615, 207, 25);
		add(othersBar);
		
		JCheckBox[] allCheckboxes = {
		    unmarriedAbandoned,
		    UnmarriedSeparated,
		    deathofPartner,
		    marriedAbandoned,
		    annulled,
		    deFacto,
		    LegallySeparated,
		    divorced,
		    widow,
		    others
		};

		for (JCheckBox checkBox : allCheckboxes) {
		    checkBox.addActionListener(e -> {
		        for (JCheckBox cb : allCheckboxes) {
		            if (cb != checkBox) {
		                cb.setSelected(false);
		            }
		        }


		        dateofWeddingBar.setEnabled(false);
		        dateofSeparationBar.setEnabled(false);
		        othersBar.setEnabled(false);


		        if (checkBox == marriedAbandoned && marriedAbandoned.isSelected()) {
		            dateofWeddingBar.setEnabled(true);
		        } else if (checkBox == annulled && annulled.isSelected()) {
		            dateofSeparationBar.setEnabled(true);
		        } else if (checkBox == others && others.isSelected()) {
		            othersBar.setEnabled(true);
		        }
		    });
		}

		dateofWeddingBar.setEnabled(false);
		dateofSeparationBar.setEnabled(false);
		othersBar.setEnabled(false);
	}
	
    public void setMarriedWidow(boolean selected) {
    	widow.setSelected(selected);
    }
    
    public void setUnmarriedAbandoned(boolean selected) {
    	unmarriedAbandoned.setSelected(selected);
    }
    
    public void setUnmarriedSeparated(boolean selected) {
    	UnmarriedSeparated.setSelected(selected);
    }
    
    public void setdeathofPartner(boolean selected) {
    	deathofPartner.setSelected(selected);
    }
    
    public void setMarriedAbandoned(boolean selected) {
    	marriedAbandoned.setSelected(selected);
    }
    
    public void setannulled(boolean selected) {
    	annulled.setSelected(selected);
    }
    
    public void setdeFacto(boolean selected) {
    	deFacto.setSelected(selected);
    }
    
    public void setLegallySeparated(boolean selected) {
    	LegallySeparated.setSelected(selected);
    }

    public void setdivorced(boolean selected) {
    	divorced.setSelected(selected);
    }
    
    public void setothers(boolean selected) {
    	others.setSelected(selected);
    }
    
    public void setDateOfWedding(String date) {
    	dateofWeddingBar.setText(date);
    }

    public void setDateOfSeparation(String date) {
    	dateofSeparationBar.setText(date);
    }

    public void setOtherReason(String reason) {
    	othersBar.setText(reason);
    }
    public Icon createCustomCheckboxIcon(Color checkmarkColor) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                AbstractButton button = (AbstractButton) c;
                ButtonModel model = button.getModel();
                Graphics2D g2 = (Graphics2D) g.create();

                int size = getIconWidth();

                if (model.isSelected()) {
                    g2.setColor(new Color(255, 64, 169));
                    g2.fillRect(x + 1, y + 1, size - 2, size - 2);
                }

                g2.setColor(model.isSelected() ? Color.WHITE : Color.BLACK); 
                g2.setStroke(new BasicStroke(2));
                g2.drawRect(x + 1, y + 1, size - 2, size - 2);

                if (model.isSelected()) {
                    g2.setColor(checkmarkColor);
                    g2.setStroke(new BasicStroke(2));
                    g2.drawLine(x + 4, y + 8, x + 7, y + 11);
                    g2.drawLine(x + 7, y + 11, x + 12, y + 4);
                }

                g2.dispose();
            }

            @Override
            public int getIconWidth() {
                return 16;
            }

            @Override
            public int getIconHeight() {
                return 16;
            }
        };
    }
}
