import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

// will add whatever controls need adding for whatever a user may want to do

public class Controls extends JPanel implements ActionListener {
	
	private JButton writeSomething;
	private JTextField displaySomething;
	
	public Controls() {
		this.
		setBackground(Color.red);
		layoutComponents();
		
	}
	
	private void layoutComponents() {
		
		writeSomething = new JButton("Does this work?");
		writeSomething.addActionListener(this);
		
		displaySomething = new JTextField(25);
		
		this.add(writeSomething);
		this.add(displaySomething);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == writeSomething) {
			String copy = writeSomething.getText();
			displaySomething.setText(copy);
			System.out.println(copy);
			
		}
		
	}
	
	
	
	

}
