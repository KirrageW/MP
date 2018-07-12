

public class Globe {
	
	private Square[][] squares; // this is just a list. the 2d index has no bearing on the location of the square. each square maintains its own x and y position
	private int size;
	private int[][] heightMap; // this is a map. the 2d array index is derived from the x and y coordinates of the squares
	
	private int[][] groupMap; // this will be a 2d array map that can check for the presence of two Squares in the same place
	
	
	
	public Globe(int size) {
		
		this.size=size;
		
		squares = new Square[size][size];
				
		// all squares -1 (sea) across whole map
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				squares[i][j] = new Square(-1);
			}
		}
		
		// new 2d array which will take coordinates from Squares, and store their height
		setHeightMap(new int[size][size]);
		
		for (int i = 0; i < size; i ++) {
			for (int j = 0; j < size; j++) {
				getHeightMap()[i][j]=-1;
			}
		}
		
		
		// new 2d array which takes coordinates from Squares, and stores their group
		groupMap = new int[size][size];
		for (int i = 0; i < size; i ++) {
			for (int j = 0; j < size; j ++) {
				groupMap[i][j] = -1;
			}
		}
	}
	
	// can make a special list for squares with continent, to loop over for move methods etc. ignores sea squares and would be more efficient.
	// make a continent for testing
	public void makeTestContinent() {
		for (int i = size/4; i < size/3; i++) {
			for (int j = size/4; j < size/3; j++) {
				//squares[i][j] = new Square(i,j,250);
				squares[i][j].setX(i);
				squares[i][j].setY(j);
				squares[i][j].setHeight(250);
				
				squares[i][j].setGroup(1);
			}
		}
	}
		
	// sets velocities of all members of chosen group
	public void setVelocity(int group, int xVel, int yVel) {
		for (int i = 0; i < size; i ++) {
			for (int j = 0; j < size; j++) {
				if (squares[i][j].getGroup() == group) {
					squares[i][j].setXVel(xVel);
					squares[i][j].setYVel(yVel);					
				}
			}
		}
	}
	
	// moves all squares by one iteration of their velocities
	public void move() {
		for (int i = 0; i < size; i ++){
			for (int j = 0; j < size; j ++) {
				squares[i][j].setX(squares[i][j].getX()+(squares[i][j].getXVel()));
				squares[i][j].setY(squares[i][j].getY()+(squares[i][j].getYVel()));				
			
				
				// having to loop over them all. Can't immediately check for -1 height (representing sea level) because I have only given the heightmap the full -1 background.
				// hang on. 
				// far too slow
				
				
				
			}
		}
		
		
		
		
		
		
		plotToHeightMap();
	}
	

	
	// run through all the Squares, getting their X and Y coordinates for 2d height array
	public void plotToHeightMap() {
		
		// clear it first - but then how are you going to detect collisions - use the squares! 
		for (int i = 0; i < size; i ++) {
			for (int j = 0; j < size; j ++) {
				heightMap[i][j]=-1;
				
			}
		}
		
		// translate Squares X and Y integers to the heightmap 2d array index - giving coordinates.
		for (int i = 0; i < size; i ++) {
			for (int j = 0; j < size; j ++) {
				//if (squares[i][j].getX() > 0 && squares[i][j].getY() > 0) {
					heightMap[squares[i][j].getX()][squares[i][j].getY()] = squares[i][j].getHeight();		
				//}
			}
		}	
	}
	
	public void plotToGroupMap() {
		
		for (int i = 0; i < size; i ++) {
			for (int j = 0; j < size; j ++) {
				heightMap[i][j]=-1;
				
			}
		}	
		for (int i = 0; i < size; i ++) {
			for (int j = 0; j < size; j ++) {
				groupMap[squares[i][j].getX()][squares[i][j].getY()] = squares[i][j].getGroup();
			}
		}
	}
	
	// plot new positions on the group map, and loop through to see if any planned positions have continent in them.
	public void moveOnGroupMap() {
		
		// need to efficiently check these collisions...
		
		
	}
	
	
	
	public int[][] getHeightMap() {
		return heightMap;
	}

	public void setHeightMap(int[][] map) {
		this.heightMap = map;
	}

	
	
	


}
