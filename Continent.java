import java.util.ArrayList;

// an entire continent formed of Squares - aggregation. File/Folder perhaps? general hierarchy..

public class Continent extends Thread {

	
	private int planetSize;
	private String id;
	private double mass;
	private int speedX;
	private int speedY;
	
	private ArrayList<Square> children;
	private int centreX;
	private int centreY;
	
	
	
	
	public Continent(String id, int land, int speedX, int speedY, int planetSize) {
		children = new ArrayList<Square>();
		this.id = id;
		this.planetSize = planetSize;
		addLand(land);
		setSpeeds(speedX, speedY);
		
	}
	
	// make some clever way to randomly generate landmasses - encapsulate all this crap
	// some sort of offset from centre??? 
	// make a central square, and then build randomly off that one with each square having an integer that translates to a 
	// coordinated offset for both x and y. that way they can stick to where they are meant to be.
	
	public void addLand(int size) {
		
		//for (int i = 0; i < size; i ++) {
			
			// say this first guy is seed
			Square seed = new Square(200,245, 50, 5, 500, id);
			int centreX = seed.getXCoord();
			int centreY = seed.getYCoord();
			
			children.add(seed);
			
			children.add(new Square(200,250, 75, 5, 500, id));
			children.add(new Square(200,255, 75, 5, 500, id));
			children.add(new Square(200,260, 75, 5, 500, id));
			children.add(new Square(205,250, 75, 5, 500, id));
			children.add(new Square(205,255, 75, 5, 500, id));
			children.add(new Square(205,260, 75, 5, 500, id));
			children.add(new Square(210,255, 75, 5, 500, id));
		//}
		
	}
	
	public void setSpeeds(int x, int y) {
		this.speedX = x;
		this.speedY = y;
	}
	
	public double getSpeedX() {
		return speedX;
	}
	
	public double getSpeedY() {
		return speedY;
	}
	
	
	// not sure why i need these - could be useful
	// gets centreX
	public int setCentreX() {
		
		int sumX = 0;
		for (int i = 0; i < children.size(); i++) {
			sumX =+ children.get(i).getXCoord();
		}
		int avX = sumX/children.size();
		int centreX = 5*(Math.round(avX/5));
		
		this.centreX = centreX;
		return centreX;
	}
	
	// gets centreY
	public int setCentreY() {
		
		int sumY = 0;
		for (int i = 0; i < children.size(); i++) {
			sumY =+ children.get(i).getYCoord();
		}
		int avY = sumY/children.size();
		int centreY = 5*(Math.round(avY/5));
		
		this.centreY = centreY;
		return centreY;
	}
	
	
	public void run()  {

		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (Square child : children){
			
			

			if (child.getXCoord() > planetSize) {
				child.setX(0); 
			}

			if (child.getXCoord() < 0) {
				child.setX(planetSize); 
			}

			if (child.getYCoord() > planetSize) {
				child.setY(0);
			}

			if (child.getYCoord() < 0) {
				child.setY(planetSize);
			}
			
			child.setX(child.getXCoord() + speedX);
			child.setY(child.getYCoord() + speedY); 
			
			}

		}
		
	}
		
		// detect if one continent is in the bounds of another
		public boolean checkCollision(Continent other) {	
				
			//if ((this.x >= other.x && this.x <= other.x+size) && (this.y >= other.y && this.y <= other.y+size)) {
				
				//return true;
			//}
			return false;
			
		}
	
	
	
	public ArrayList<Square> getChildren()	{
		return children;
	
	}
	
	// some way to tell if one square touches another square...
	
	
	
	
	
}
