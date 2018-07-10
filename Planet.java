import java.util.ArrayList;

// maintains list of all continents, allows calculations to be made on aggregate 

public class Planet extends Thread {
	
	private ArrayList<Continent> continents;
	
	protected final int PLANETSIZE = 500;
	protected final int SQUARESIZE = 5;
	
	public Planet() {
				
		continents = new ArrayList<Continent>();	
		
		Continent test = new Continent("test", 20, 1,0, 50, 50, 50, PLANETSIZE, SQUARESIZE);		
		Continent test1 = new Continent("test", 20, -1,0, 200, 50, 50, PLANETSIZE, SQUARESIZE);
		
		continents.add(test);
		continents.add(test1);
		
		for (int i = 0; i < continents.size(); i++) {
			continents.get(i).start();
		}
		
		this.start();
		
	}
	
	public void run()  {
		while(true) {
			for (Continent continent : continents) {
				for (int i = 1; i < continents.size(); i ++) {
					if (continent.checkCollision(continents.get(i))) {
						
						continent.setSpeeds(0, 0);
						continents.get(i).setSpeeds(0, 0);
					
						
						// do whatever in the event of a collision - maths
						
					}
				}
			}
			
		}
	}
	
	
	public ArrayList<Continent> getContinents(){
		return continents;
	}
	

	
}
