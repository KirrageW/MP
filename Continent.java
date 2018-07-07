import java.util.ArrayList;
import java.util.Random;

// an entire continent formed of Squares - aggregation. File/Folder perhaps? general hierarchy..

public class Continent extends Thread {

	
	private int planetSize;
	private int squareSize;
	private int defaultHeight;
	
	private String id;
	private double mass;
	private int speedX;
	private int speedY;
	
	private ArrayList<Square> children;
	private int centreX;
	private int centreY;
	
	
	
	// these can all be settings chosen by the user at the start of the program
	public Continent(String id, int startingArea, int speedX, int speedY, int defaultHeight, int planetSize, int squareSize) {
		children = new ArrayList<Square>();
		this.id = id;
		this.planetSize = planetSize;
		this.squareSize = squareSize;
		this.defaultHeight = defaultHeight;
		addLand(startingArea);
		setSpeeds(speedX, speedY);
		
	}
	
	// make some clever way to randomly generate landmasses - encapsulate all this crap
	// some sort of offset from centre??? 
	// make a central square, and then build randomly off that one with each square having an integer that translates to a 
	// coordinated offset for both x and y. that way they can stick to where they are meant to be.
	
	public int generateRandomCoords(int CoordSeed) {
		
		int newX = 0;
		Random r = new Random();
		int LowX = CoordSeed-30;
		int HighX = CoordSeed+30;
		
		int ResultX = r.nextInt(HighX-LowX) + LowX;
		ResultX = Math.round(ResultX/squareSize)*squareSize; // round to nearest square size
		
		return ResultX;
	}
	
	
	public void addLand(int size) {
		
		//for (int i = 0; i < size; i ++) {
			
			// say this first guy is seed
			Square seed = new Square(200,245, defaultHeight, squareSize, planetSize, id);			
			children.add(seed);
			
			//get random multiple of 5, within a certain range, ensure no repetitions of X/Y combinations			
			ArrayList<Integer> xOffsets = new ArrayList<Integer>();
			ArrayList<Integer> yOffsets = new ArrayList<Integer>();
			
			// for x
			for (int i = 0; i < size; i ++) {
				int x = generateRandomCoords(seed.getXCoord());
				xOffsets.add(x);
				System.out.println(x);				
			}
					
			// for y
			for (int i = 0; i <size; i ++) {
				int y = generateRandomCoords(seed.getYCoord());
				yOffsets.add(y);				
			}
						
			// this does not check for duplicated coordinate combinations - two squares in same place
			for (int i = 0; i < size; i ++) {
				children.add(new Square(xOffsets.get(i),yOffsets.get(i), defaultHeight, squareSize, planetSize, id));				
			}
			
			
			
			// it makes a pleasingly random outline, so perhaps there is a way to fill in the gaps????
			
			
			
			
			/*children.add(new Square(200,250, 75, 5, 500, id));
			children.add(new Square(200,255, 75, 5, 500, id));
			children.add(new Square(200,260, 75, 5, 500, id));
			children.add(new Square(205,250, 75, 5, 500, id));
			children.add(new Square(205,255, 75, 5, 500, id));
			children.add(new Square(205,260, 75, 5, 500, id));
			children.add(new Square(210,255, 75, 5, 500, id));*/
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
