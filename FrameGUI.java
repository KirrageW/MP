import java.awt.*;
import javax.swing.*;


public class FrameGUI extends JFrame {
	
	private Planet planet;
	
	private ContinentDrawer animation;
	
	private Controls controlPanel;
	private JPanel right;
	
	FrameGUI(){
		Planet test = new Planet();
		this.planet = test;
				
		setTitle("Planet rendering");
		setSize(planet.PLANETSIZE, planet.PLANETSIZE);
		
		animation = new ContinentDrawer(planet);
		add(animation);
		
		controlPanel = new Controls();
		
		layoutComponents();
		setVisible(true);
		
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
