import java.util.ArrayList;
import java.util.Random;

// 2d array 
public class Continent extends Thread {

	// size of square grid
	private int n;
	
	
	// need to be doubbles - this just shows height at the moment. need a class, or multiple 2d arrays??? but hang on - it is just height that is the info
	// the COORDINATES are a piece of information themselves - and can be used to specify continents. Continents can own list of coordinates. 
	
	private int[][] grid;
	
	
	public Continent(int n) {
		// initialise
		grid = new int[n][n];
		// set all to -1
		setSea();
	}
	
	public void setSea() {
		for (int i = 0; i < n; i ++) {
			for (int j = 0; j < n; j ++) {
				grid[i][j] = -1;
			}
		}
	}
	
	// make random land
	public void makeLand() {
		
	}
	
	//
	
	
	
	
	



}
