import java.awt.*;
import javax.swing.*;


// main frame for View of program - also contains main method. Initialises everything. 
// Frame holds other components / panels

public class FrameGUI extends JFrame {
	
	private Planet planet;
	
	private ContinentDrawer animation;
	
	private Controls controlPanel;
	private JPanel right;
	
	FrameGUI(){
		
		// make planet
		Planet test = new Planet();
		this.planet = test;
				
		// frame settings
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Planet rendering");
		setSize(planet.PLANETSIZE, planet.PLANETSIZE);
		
		// add modules
		animation = new ContinentDrawer(planet);
		add(animation);
		controlPanel = new Controls();
		layoutComponents();
		setVisible(true);
		
		// component redraws in loop here
		while (true) {
			animation.repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void layoutComponents() {
			
		right = new JPanel();	
		right.add(controlPanel);		
		add(right,BorderLayout.SOUTH);
				
	}
	
	
	
	// main test here
	public static void main(String args[]) {
		//javax.swing.SwingUtilities.invokeLater(new Runnable() {

			//@Override
			//public void run() {
				FrameGUI testing = new FrameGUI();
				
				
				
			//}
			
		//});
		
	}

}
