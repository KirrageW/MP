import java.util.ArrayList;

public class Planet extends Thread {
	
	private ArrayList<Continent> continents;
	
	protected final int PLANETSIZE = 500;
	
	public Planet() {
				
		continents = new ArrayList<Continent>();
		
		Continent test = new Continent(200,50, 150, 20, PLANETSIZE);
		Continent test2 = new Continent(200,280, 50, 20, PLANETSIZE);
		
		test.setSpeeds(1, 0);
		test2.setSpeeds(0, 1);
		
		continents.add(test);
		continents.add(test2);
		
		for (int i = 0; i < continents.size(); i++) {
			continents.get(i).start();
		}
		
		this.start();
		
	}
	
	public void run()  {
		while(true) {
			for (int i = 0; i < continents.size(); i++) {
				for (int j = 1; j < continents.size(); j ++) {
					continents.get(i).checkCollision(continents.get(j));
				}
			}
			
		}
		
	}
	
	
	public ArrayList<Continent> getContinents(){
		return continents;
	}
	
	//planet must handle collisions and stuff, then... i think
	// this class has a list of all continents, so surely it is able to detect collisions
	// and can directly set new speeds and directions in such events...
	

	
	
	
	

	
	
}
