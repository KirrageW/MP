import java.awt.Canvas;
import java.awt.Color;

import javax.swing.JFrame;

public class FrameGUI extends JFrame {
	
	private Planet planet;
	
	
	FrameGUI(){
		Planet test = new Planet();
		this.planet = test;
		
		
		setTitle("Planet rendering");
		setSize(planet.PLANETSIZE, planet.PLANETSIZE);
		
		
		ContinentDrawer drawer = new ContinentDrawer(planet);
		this.add(drawer);
		
		
		
		setVisible(true);
	}
	
	
	
	
	// main test here
	public static void main(String args[]) {
		FrameGUI testing = new FrameGUI();
	}

}
