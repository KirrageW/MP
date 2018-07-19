import java.util.ArrayList;
import java.util.Random;

public class Globe {

	private Square[][] squares; // this is just a list. the 2d index has no bearing on the location of the
								// square. each square maintains its own x and y position
	private int size;
	private int[][] heightMap; // this is a map. the 2d array index is derived from the x and y coordinates of
								// the squares

	private int[][] groupMap; // this will be a 2d array map that can check for the presence of two Squares in
								// the same place

	private ArrayList<Square>[] continents;
	private int continentsCounter;

	private ArrayList<Square> collisions;
	
	private boolean stop;

	public Globe(int size) {
		stop = true;
		continents = new ArrayList[10];
		continentsCounter = 1;

		this.size = size;

		squares = new Square[size][size];

		// all squares -1 (sea) across whole map
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				squares[i][j] = new Square(-1);
			}
		}

		// new 2d array which will take coordinates from Squares, and store their height
		setHeightMap(new int[size + 1][size + 1]);

		for (int i = 0; i < size + 1; i++) {
			for (int j = 0; j < size + 1; j++) {
				getHeightMap()[i][j] = -1;
			}
		}

		// new 2d array which takes coordinates from Squares, and stores their group
		groupMap = new int[size + 1][size + 1];
		for (int i = 0; i < size + 1; i++) {
			for (int j = 0; j < size + 1; j++) {
				groupMap[i][j] = 0;
			}
		}
	}

	// **********************************************************************************************************
	// TO DO:

	// better mountain generation

	// rotating continents?

	// refactor all of it - squares and stuff

	// add GUI stuff

	// add erosion

	// add ice
	
	// these will be published by the swing thing, to receive results during running.

	// **********************************************************************************************************

	// make array of pointers to grid
	// assign first N of them IDs - 1, 2, 3, etc
	// until array points to no spaces that doo not have IDs
	// iterate through the array lookinh fotr spaces that dfo not have IDs
	// if space -if space has neighbours in grid that DO have ID, assign the space
	// the ID from the weighted selection of the IDs around it
	// if it doesnt have neighbours with IDs, skip to next
	//

	// populate 2d array with a single instance of each part ID number, randomly
	// distributed
	// iterate over the 2d array smd insert part ID numbers in the empty cells based
	// on the contents of their surrounding cells
	// let them expand and fight it out -- I can just take one ID, or two IDs etc.

	// now make this happen in a certain area of the total area, and translate it to
	// real square coordinates, and THEN reset the group map
	// group map should reset normally if plot method occurs after the generation...

	// need to use it all to make different groups

	public void newNumbers(int x, int y, int size, int group) {
		ArrayList<Square> cont = new ArrayList<Square>();
		Random ran = new Random();

		for (int i = x + 2; i < size - 2; i++) {
			for (int j = y + 2; j < size - 2; j++) {
				int number = ran.nextInt(4);
				groupMap[i][j] = number;
			}
		}

		for (int i = 2; i < this.size - 2; i++) {
			for (int j = 2; j < this.size - 2; j++) {
				groupMap[i][j] = getNeighbours(i, j);
			}
		}

		// you need more or fewer of these, depending on size of grid.
		closer();
		closer1();
		closer1();
		closer1();
		closer();
		focus();

		// **************************************************************** VIEW NUMBERS
		/*
		 * for (int i = 1; i < this.size - 1; i++) { System.out.println();
		 * 
		 * for (int j = 1; j < this.size - 1; j++) { System.out.print(groupMap[i][j] +
		 * " "); } }
		 */
		// *********************************************************************

		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if (groupMap[i][j] == 1) {
					squares[i][j].setX(i);
					squares[i][j].setY(j);
					squares[i][j].setHeight(150);
					squares[i][j].setGroup(group);

					cont.add(squares[i][j]);

				}
			}
		}

		continents[continentsCounter] = cont;
		continentsCounter++;

		System.err.println(continentsCounter + " " + continents[continentsCounter - 1].size());

		plotToHeightMap();
		plotToGroupMap();

		// is it counting them all as same group
		// is it rounding up to 1.

	}

	public int getNeighbours(int i, int j) {

		int a = groupMap[i - 1][j - 1];
		int b = groupMap[i][j - 1];
		int c = groupMap[i + 1][j - 1];
		int d = groupMap[i + 1][j];
		int e = groupMap[i + 1][j + 1];
		int f = groupMap[i][j + 1];
		int g = groupMap[i - 1][j + 1];
		int h = groupMap[i - 1][j];

		int q = groupMap[i - 2][j - 2];
		int w = groupMap[i][j - 2];
		int k = groupMap[i + 2][j - 2];
		int l = groupMap[i + 2][j];
		int m = groupMap[i + 2][j + 2];
		int n = groupMap[i][j + 2];
		int o = groupMap[i - 2][j + 2];
		int p = groupMap[i - 2][j];

		return (a + b + c + d + e + f + g + h + q + w + k + l + m + n + o + p) / 16;

	}

	public void closer() {

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int numberOfNeighbours = 0;
				int req = 3;
				if (groupMap[i][j] > 0) {

					if (groupMap[i - 1][j - 1] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i][j - 1] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i + 1][j - 1] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i + 1][j] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i + 1][j + 1] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i][j + 1] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i - 1][j + 1] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i - 1][j] > 0) {
						numberOfNeighbours++;
					}

					// whittle
					if (numberOfNeighbours <= req) {
						groupMap[i][j] = 0;

					}
				}

			}
		}

	}

	// gets rid of lakes
	public void focus() {
		for (int i = 1; i < size - 1; i++) {
			for (int j = 1; j < size - 1; j++) {
				int numberOfNeighbours = 0;
				int req = 3;
				if (groupMap[i][j] == 0) {
					if (groupMap[i - 1][j - 1] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i][j - 1] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i + 1][j - 1] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i + 1][j] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i + 1][j + 1] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i][j + 1] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i - 1][j + 1] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i - 1][j] > 0) {
						numberOfNeighbours++;
					}

					if (numberOfNeighbours > req) {
						groupMap[i][j] = 1;

					}
				}
			}
		}

	}

	public void closer1() {

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int numberOfNeighbours = 0;
				int req = 3;
				if (groupMap[i][j] > 0) {

					if (groupMap[i - 2][j - 2] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i][j - 2] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i + 2][j - 2] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i + 2][j] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i + 2][j + 2] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i][j + 2] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i - 2][j + 2] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i - 2][j] > 0) {
						numberOfNeighbours++;
					}

					// whittle
					if (numberOfNeighbours <= req) {
						groupMap[i][j] = 0;

					}
				}

			}
		}

	}

	// sets velocities of all members of chosen group
	public void setVelocity(int group, int xVel, int yVel) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (squares[i][j].getGroup() == group) {
					squares[i][j].setXVel(xVel);
					squares[i][j].setYVel(yVel);
				}
			}
		}
	}

	public void move() {

		collisions = new ArrayList<Square>(); // reset collisions each time
		int groupNumber = -2;
		boolean collision = false;

		int o = 0;
		int p = 0;
		int h = 0;
		int k = 0;

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {

				if (squares[i][j].getX() + squares[i][j].getXVel() >= size) {
					// find the amount it's gone over the limit, don't just set to 0
					int amountOver = (squares[i][j].getX() + squares[i][j].getXVel()) - size;
					squares[i][j].setX(amountOver);
				} else if (squares[i][j].getY() + squares[i][j].getYVel() >= size) {
					int amountOver = (squares[i][j].getY() + squares[i][j].getYVel()) - size;
					squares[i][j].setY(amountOver);
				} else if (squares[i][j].getX() + squares[i][j].getXVel() < 0) {
					// find the amount it's gone under 0, set to size - that
					int amountUnder = squares[i][j].getX() + squares[i][j].getXVel() + size;
					squares[i][j].setX(amountUnder);
				} else if (squares[i][j].getY() + squares[i][j].getYVel() < 0) {
					int amountUnder = squares[i][j].getY() + squares[i][j].getYVel() + size;
					squares[i][j].setY(amountUnder);
				}

				// repeat again as it could have gone above or below for the X AND the Y
				// coordinate, at a corner
				if (squares[i][j].getX() + squares[i][j].getXVel() >= size) {
					// find the amount it's gone over the limit, don't just set to 0
					int amountOver = (squares[i][j].getX() + squares[i][j].getXVel()) - size;
					squares[i][j].setX(amountOver);
				} else if (squares[i][j].getY() + squares[i][j].getYVel() >= size) {
					int amountOver = (squares[i][j].getY() + squares[i][j].getYVel()) - size;
					squares[i][j].setY(amountOver);
				} else if (squares[i][j].getX() + squares[i][j].getXVel() < 0) {
					// find the amount it's gone under 0, set to size - that
					int amountUnder = squares[i][j].getX() + squares[i][j].getXVel() + size;
					squares[i][j].setX(amountUnder);
				} else if (squares[i][j].getY() + squares[i][j].getYVel() < 0) {
					int amountUnder = squares[i][j].getY() + squares[i][j].getYVel() + size;
					squares[i][j].setY(amountUnder);
				}
			}
		}

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {

				// pre emptive check for overlapping - before actual move. one step ahead.

				// so it's trying to access group map array index beyond the range - why. i
				// thought the above would always handle these boundaries before it

				if (groupMap[squares[i][j].getX() + (squares[i][j].getXVel())][squares[i][j].getY() // arrayIndexOutofBounds:
																									// 152 , 150 , -2 ,
																									// 150
						+ (squares[i][j].getYVel())] > 0) {
					if (groupMap[squares[i][j].getX() + (squares[i][j].getXVel())][squares[i][j].getY()
							+ (squares[i][j].getYVel())] != squares[i][j].getGroup()) {

						// save i and j of first instance - to get groups and stuff
						if (collision == false) {
							o = i;
							p = j;
							groupNumber = groupMap[squares[i][j].getX() + (squares[i][j].getXVel())][squares[i][j].getY()
								                                                 							+ (squares[i][j].getYVel())];
							h = squares[i][j].getX() + (squares[i][j].getXVel());
							k = squares[i][j].getY() + (squares[i][j].getYVel());
							
						}

						collision = true;
						
						// amass a list of overlapped squares FIRST
						collisions.add(squares[i][j]); 					
						collisions.add(getSquare(h, k, groupNumber)); // on the other continent
					}
				}

			}
		}

		if (collision == true) {
			System.err.println("COLLISION DETECTED OINEFION");

			// which groups are involved?
			int g1 = squares[o][p].getGroup();
			int g2 = groupMap[squares[o][p].getX() + squares[o][p].getXVel()][squares[o][p].getY()
					+ squares[o][p].getYVel()];

			// masses can simply equal number of squares
			int mass1 = continents[g1].size();			
			int mass2 = continents[g2].size();
			
			double nx1 = continents[g1].get(0).getXVel() * (mass1 - mass2)
					+ 2 * (mass2 * continents[g2].get(0).getXVel());
			double ny1 = continents[g1].get(0).getYVel() * (mass1 - mass2)
					+ 2 * (mass2 * continents[g2].get(0).getYVel());
			double nx2 = continents[g2].get(0).getXVel() * (mass2 - mass1)
					+ 2 * (mass1 * continents[g1].get(0).getXVel());
			double ny2 = continents[g2].get(0).getYVel() * (mass2 - mass1)
					+ 2 * (mass1 * continents[g1].get(0).getYVel());

			
			nx1 = nx1 / (mass1 + mass2);
			System.out.println(nx1);
			ny1 = ny1 / (mass1 + mass2);
			System.out.println(ny1);
			nx2 = nx2 / (mass1 + mass2);
			System.out.println(nx2);
			ny2 = ny2 / (mass1 + mass2);
			System.out.println(ny2);

			for (Square a : continents[g1]) {
				a.setXVel(nx1);
				a.setYVel(ny1);
			}

			for (Square b : continents[g2]) {
				b.setXVel(nx2);
				b.setYVel(ny2);
			}
		}

		// get those that overlapped
		int force = 250;
		for (Square v : collisions) {
			getNeighbours(v, force);
		}

		for (Square v : collisions) {
			v.setHeight(force);
		}
		
		// move here. check for boundaries and handle accordingly
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				squares[i][j].setX(squares[i][j].getX() + (squares[i][j].getXVel()));
				squares[i][j].setY(squares[i][j].getY() + (squares[i][j].getYVel()));
			}
		}
		plotToHeightMap();
		plotToGroupMap();
	}

	// upper bound for forloop here could be related to the force involved - so a
	// large collision will iterate more times, reaching more neighbours
	// also should take in force to determine scale of mountain formation
	public void getNeighbours(Square x, int force) {

		// get group of square involved
		int g = x.getGroup();

		for (int i = 1; i < 5; i++) {
			for (Square a : continents[g]) {
				if (force < 10) {
					return;
				}
				if ((a.getX() == x.getX() + i || a.getX() == x.getX() - i || a.getX() == x.getX())
						&& (a.getY() == x.getY() + i || a.getY() == x.getY() - i || a.getY() == x.getY())) {
					if (a.getX() == x.getX() && a.getY() == x.getY()) {
						a.setHeight(force);

					} else {
						a.setHeight(force / i);

					}
				}
			}
		}
		for (int i = 0; i < force; i++) {
		}
	}

	// run through all the Squares, getting their X and Y coordinates for 2d height
	// array
	public void plotToHeightMap() {

		// reset height map - overwrites old positions
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				heightMap[i][j] = -1;

			}
		}
		
		// translate Squares X and Y integers to the heightmap 2d array index - giving coordinates.
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				heightMap[squares[i][j].getX()][squares[i][j].getY()] = squares[i][j].getHeight(); // error here
			}
		}
	}

	public void plotToGroupMap() {

		// reset group map - overwrites old positions
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				groupMap[i][j] = -1;

			}
		}

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				groupMap[squares[i][j].getX()][squares[i][j].getY()] = squares[i][j].getGroup(); // index out of bounds																									
			}
		}
	}

	// plot new positions on the group map, and loop through to see if any planned
	// positions have continent in them.
	public void displayGroupMap() {
		for (int i = 0; i < size; i++) {
			System.out.println();
			for (int j = 0; j < size; j++) {
				System.out.print(groupMap[i][j] + " ");

			}
		}
	}

	// returns a square matching an x and y coordinate from the group map or height map
	public Square getSquare(int x, int y, int group) {
		
		for (Square a : continents[group]) {
			if (a.getX() == x && a.getY() == y) {
				return a;
			}
		}
		return null;
		
	}
	

	
	
	
	
	public int[][] getHeightMap() {
		return heightMap;
	}

	public void setHeightMap(int[][] map) {
		this.heightMap = map;
	}

}
