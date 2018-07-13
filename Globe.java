import java.util.ArrayList;

public class Globe {
	
	private Square[][] squares; // this is just a list. the 2d index has no bearing on the location of the square. each square maintains its own x and y position
	private int size;
	private int[][] heightMap; // this is a map. the 2d array index is derived from the x and y coordinates of the squares
	
	private int[][] groupMap; // this will be a 2d array map that can check for the presence of two Squares in the same place
	
	private ArrayList<Square> group1;
	private ArrayList<Square> group2;

	private ArrayList<Square>[] continents;
	private int continentsCounter;
	
	
	private ArrayList<Square> collisions;
	
	public Globe(int size) {
		
		continents = new ArrayList[10];
		continentsCounter = 1;
		
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
	
	// **********************************************************************************************************
	// TO DO:
	
	// should make a special list for squares with continent, to loop over for move methods etc. ignores sea squares and would be more efficient.
	// make a continent for testing
	
	// NOT USING ALL THOSE SQUARES IN 2D ARRAY - GET RID OF IN FUTURE
	
	// MAKE ISLAND GENERATOR - AUTO GROUP TOO
	
	// GROUPS NEED SPEED VARIABLES - CONTINENT CLASS AFTER ALL?
	
	// **********************************************************************************************************
	
	// temp
	public void makeTestContinent() {
		
		group1 = new ArrayList<Square>();
		
		for (int i = size/4; i < size/3; i++) {
			for (int j = size/4; j < size/3; j++) {
				squares[i][j].setX(i);
				squares[i][j].setY(j);
				squares[i][j].setHeight(150);			
				squares[i][j].setGroup(1);		
				
				group1.add(squares[i][j]);
				
				
			}
		}
		
		continents[continentsCounter] = group1;
		continentsCounter++;
	}
	
	// temp
	public void makeOtherContinent() {
		
		group2 = new ArrayList<Square>();
		
		for (int i = size/4+size/4; i < size/3+size/4; i++) {
			for (int j = size/4; j < size/3; j++) {
				squares[i][j].setX(i);
				squares[i][j].setY(j);
				squares[i][j].setHeight(150);			
				squares[i][j].setGroup(2);
				
				group2.add(squares[i][j]);
			}
		}
		
		continents[continentsCounter] = group2;
		continentsCounter++;
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
	
	// moves all squares by one iteration of their velocities. checking for collision
	// in event of collision, get the groups involved, get their speeds, get their masses, get the angle
	// and do something with that
		
	public void move() {
		
		collisions = new ArrayList<Square>(); // reset collisions each time
		
		boolean collision = false;
		
		int o = 0;
		int p = 0;
		
		for (int i = 0; i < size; i ++){
			for (int j = 0; j < size; j ++) {
				
				squares[i][j].setX(squares[i][j].getX()+(squares[i][j].getXVel()));
				squares[i][j].setY(squares[i][j].getY()+(squares[i][j].getYVel()));				
			
				if (groupMap[squares[i][j].getX()][squares[i][j].getY()] > 0) {
					if (groupMap[squares[i][j].getX()][squares[i][j].getY()] != squares[i][j].getGroup()) {
						
						// save i and j of first instance - to get groups and stuff
						if (collision == false) {
							o = i;
							p = j;
						}
						
						collision = true;
						
						// we could amass a list of these squares FIRST, before dealing with the collision as below. 
						// this means we can changes the heights of all these squares, and their neighbours, and so on. 
						
						collisions.add(squares[i][j]);
						// just make variable for i and j you fucking idiot. carry them over. 
						
					}
				}
				
			}
		}
						
						if (collision == true) {
					
						// which groups are involved?
						int g1 = squares[o][p].getGroup();
						int g2 = groupMap[squares[o][p].getX()][squares[o][p].getY()];
						
						
						// get the speeds from the first square in these group (they are all the same)
						
						// first cont						
						int aX = continents[g1].get(0).getXVel();
						int aY = continents[g1].get(0).getYVel();
						// second cont
						int bX = continents[g2].get(0).getXVel();
						int bY = continents[g2].get(0).getYVel();
						
						// get mass						
						int massA = continents[g1].size();
						int massB = continents[g2].size();
						
						// MATHS ***********************************
						
						
						
						
						
						
						
						
						// ******************************************
						
						for (Square a : continents[g1]) {
							a.setXVel(-aX);
							a.setYVel(-aY);
						}
						
						for (Square b : continents[g2]) {
							b.setXVel(-bX);
							b.setYVel(-bY);
						}
						
						}
				
					
		
		for (Square v : collisions) {
			v.setHeight(250);
			System.err.println(v.getGroup());
		}
			
		plotToHeightMap();
		plotToGroupMap();
		
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
				groupMap[i][j]=-1;
				
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
