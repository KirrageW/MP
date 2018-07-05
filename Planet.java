import java.util.ArrayList;

public class Planet {
	
	private ArrayList<Continent> continents;
	
	protected final int PLANETSIZE = 500;
	
	public Planet() {
		
		
		continents = new ArrayList<Continent>();
		
		Continent test = new Continent(200,250, 150, 5, PLANETSIZE);
		Continent test2 = new Continent(200,260, 50, 5, PLANETSIZE);
		continents.add(test);
		continents.add(test2);
		
		test.move();
		test2.move();
		
		
		
		
		
	}
	
	public ArrayList<Continent> getContinents(){
		return continents;
	}
	
	//planet must handle collisions and stuff, then... i think
	// this class has a list of all continents, so surely it is able to detect collisions
	// and can directly set new speeds and directions in such events...
	
	
	

	
	
}
