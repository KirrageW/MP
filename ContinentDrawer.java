import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

// as calculations are kept separate, and this simply draws rectangles where Continent XY locations are, this class is basically
// finished, and needs no other changes. I think this is good as it keeps the display very separate from the data - replacement classes could 
// be added in future so long as they use X,Y locations for describing Continents. 

public class ContinentDrawer extends JComponent {
	
	private Planet planet;
	private Color sea;
	
	
	public ContinentDrawer(Planet x) {
		this.planet = x;
		sea = new Color(20,20,175);
	}
	
	

	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		this.setBackground(sea);
		
		//for loop for drawing rects where continent is
				
		for (int i = 0; i < planet.getContinents().size(); i++) {
			g.setColor(new Color(10, 255-planet.getContinents().get(i).getHeight(), 10));
			g.fillRect(planet.getContinents().get(i).getXCoord(), planet.getContinents().get(i).getYCoord(), planet.getContinents().get(i).getSize(), planet.getContinents().get(i).getSize());
		}
		
		
		
	}
}
