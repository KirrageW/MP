import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class Advancer extends JPanel implements ActionListener {
	
	private JButton advance;
	private JTextField displaySomething;
	
	private Globe globe;
	
	
	public Advancer(Globe g) {
		this.globe = g;
		layoutComponents();
		
	}
	
	private void layoutComponents() {
		
		advance = new JButton("Advance time");
		advance.addActionListener(this);
		
		
		
		this.add(advance);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == advance) {
			globe.move();
			System.out.println("Advance!");
			
		}
		
	}

}
