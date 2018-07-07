import java.util.ArrayList;

// maintains list of all continents, allows calculations to be made on aggregate 

public class Planet extends Thread {
	
	private ArrayList<Continent> continents;
	
	protected final int PLANETSIZE = 500;
	
	public Planet() {
				
		continents = new ArrayList<Continent>();	
		
		Continent test = new Continent("test", 15, 2,3, PLANETSIZE);
		continents.add(test);
		
		for (int i = 0; i < continents.size(); i++) {
			continents.get(i).start();
		}
		
		this.start();
		
	}
	
	public void run()  {
		while(true) {
			for (int i = 0; i < continents.size(); i++) {
				for (int j = 0; j < continents.size(); j ++) {
					if (continents.get(i)!=continents.get(j)&&(continents.get(j).checkCollision(continents.get(i)))) {
						
						// do whatever in the event of a collision
						// maths...
						// need to define rules for continents sticking together vs rebounding off eachother
						
						// maybe this could be a new class - modules to add different complexities of physics, i.e friction
						
						
						
						double firstX = continents.get(i).getSpeedX();
						double firstY = continents.get(i).getSpeedY();
						
						double secX = continents.get(j).getSpeedX();
						double secY = continents.get(j).getSpeedY();
						
						
						// making them just stop atm
						continents.get(i).setSpeeds(0, 0);
						continents.get(j).setSpeeds(0, 0);
					}
				}
			}
			
		}
		
	}
	
	
	public ArrayList<Continent> getContinents(){
		return continents;
	}
	

	
}
