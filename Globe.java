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

	private ArrayList<Square> group1;
	private ArrayList<Square> group2;

	private ArrayList<Square>[] continents;
	private int continentsCounter;

	private ArrayList<Square> collisions;

	public Globe(int size) {

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
		setHeightMap(new int[size][size]);

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				getHeightMap()[i][j] = -1;
			}
		}

		// new 2d array which takes coordinates from Squares, and stores their group
		groupMap = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				groupMap[i][j] = 0;
			}
		}
	}

	// **********************************************************************************************************
	// TO DO:

	// MAKE ISLAND GENERATOR - AUTO GROUP TOO - use the 2d int array, generate
	// random 1s, then group to make islands. internet search for it.

	// better collisions

	// better mountain generation

	// rotating continents?

	// sort out boundary index thing

	// refactor all of it - squares and stuff

	// add GUI stuff

	// add erosion

	// add ice

	// **********************************************************************************************************

	// temp
	public void makeTestContinent() {

		group1 = new ArrayList<Square>();

		for (int i = size / 4; i < size / 3; i++) {
			for (int j = size / 4; j < size / 2; j++) {
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

	// start to make an int array map to define islands
	// can sys.out it to view it easily.

	// generate random numbers , 1s, 2s, 3s depedning on number of initial
	// continents you want
	// sea is -1

	// then do things with finding neighbours and such

	public void generate(int x) {
		Random r = new Random();
		for (int i = 0; i < size * 2; i++) {

			int e = r.nextInt(size);
			int f = r.nextInt(size);

			groupMap[e][f] = 1;

		}

		for (int i = 0; i < size; i++) {
			System.out.println("");

			for (int j = 0; j < size; j++) {
				System.out.print(groupMap[i][j]);
			}
		}

	}

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

	public void newNumbers() {

		Random ran = new Random();

		for (int i = 2; i < size - 2; i++) {
			for (int j = 2; j < size - 2; j++) {
				int number = ran.nextInt(4);
				groupMap[i][j] = number;
			}
		}

		for (int i = 2; i < size - 2; i++) {
			for (int j = 2; j < size - 2; j++) {
				groupMap[i][j] = getNeighbours(i, j);
			}
		}

		closer();
		closer1();
		closer1();

		for (int i = 1; i < size - 1; i++) {
			System.out.println();

			for (int j = 1; j < size - 1; j++) {
				System.out.print(groupMap[i][j] + " ");
			}
		}

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

	// temp
	public void makeOtherContinent() {

		group2 = new ArrayList<Square>();

		for (int i = size / 4 + size / 4; i < size / 3 + size / 4; i++) {
			for (int j = size / 4; j < size / 3; j++) {
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

		boolean collision = false;

		int o = 0;
		int p = 0;

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

			}
		}

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {

				// pre emptive check for overlapping - before actual move. one step ahead.

				if (groupMap[squares[i][j].getX() + (squares[i][j].getXVel())][squares[i][j].getY()
						+ (squares[i][j].getYVel())] > 0) {
					if (groupMap[squares[i][j].getX() + (squares[i][j].getXVel())][squares[i][j].getY()
							+ (squares[i][j].getYVel())] != squares[i][j].getGroup()) {

						// save i and j of first instance - to get groups and stuff
						if (collision == false) {
							o = i;
							p = j;
						}

						collision = true;

						// we could amass a list of these squares FIRST, before dealing with the
						// collision as below.
						// this means we can changes the heights of all these squares, and their
						// neighbours, and so on.

						collisions.add(squares[i][j]);
						// just make variable for i and j you fucking idiot. carry them over.

					}
				}

			}
		}

		if (collision == true) {
			System.err.println("COLLISION DETECTED OINEFION");

			// which groups are involved?
			int g1 = squares[o][p].getGroup();
			int g2 = groupMap[squares[o][p].getX()][squares[o][p].getY()];

			// get mass

			// MATHS ***********************************

			// angles, momentum, combination events etc...

			// relax, i have taken out the changes in direction.

			// can use the resulting direction to add mountain formation in the direction of
			// impact, away from the collision line, in angle.

			// maybe in collisions I should let them overlap a bit. this might help make
			// combinations look better..
			// or
			// for combinations, i can let them go into each other a little, and then when
			// splitting them later, change their directions and ignore each other until
			// free...

			// BELOW USED:
			// https://www.emanueleferonato.com/2007/08/19/managing-ball-vs-ball-collision-with-flash/

			/*
			 * int mass1 = continents[g1].size(); int mass2 = continents[g2].size();
			 * 
			 * double dX = continents[g1].get(0).getX() - continents[g2].get(0).getX();
			 * double dY = continents[g1].get(0).getY() - continents[g2].get(0).getY();
			 * 
			 * double collisionAngle = Math.atan2(dY,dX);
			 * System.out.println(collisionAngle);
			 * 
			 * double mag1 = Math.sqrt(continents[g1].get(0).getXVel() *
			 * continents[g1].get(0).getXVel() + continents[g1].get(0).getYVel() *
			 * continents[g1].get(0).getYVel()); double mag2 =
			 * Math.sqrt(continents[g2].get(0).getXVel() * continents[g2].get(0).getXVel() +
			 * continents[g2].get(0).getYVel() * continents[g2].get(0).getYVel());
			 * 
			 * double dir1 = Math.atan2(continents[g1].get(0).getYVel(),
			 * continents[g1].get(0).getXVel()); double dir2 =
			 * Math.atan2(continents[g2].get(0).getYVel(), continents[g2].get(0).getXVel());
			 * 
			 * double newXspeed1 = mag1 * Math.cos(dir1 - collisionAngle); double newYspeed1
			 * = mag1 * Math.sin(dir1 - collisionAngle);
			 * 
			 * double newXspeed2 = mag2 * Math.cos(dir2 - collisionAngle); double newYspeed2
			 * = mag2 * Math.sin(dir2 - collisionAngle);
			 * 
			 * double finXspeed1 = ((mass1 - mass2) * newXspeed1 + (mass2 + mass2) *
			 * newXspeed2) / (mass1 + mass2); double finXspeed2 = ((mass1 + mass1) *
			 * newXspeed1 + (mass2 - mass1) * newXspeed2) / (mass1 + mass2);
			 * 
			 * double finYspeed1 = newYspeed1; double finYspeed2 = newYspeed2;
			 * 
			 * 
			 * double aX = Math.cos(collisionAngle) * finXspeed1 + Math.cos(collisionAngle +
			 * Math.PI/2) * finYspeed1; System.out.println(aX); double aY =
			 * Math.sin(collisionAngle) * finXspeed1 + Math.sin(collisionAngle + Math.PI/2)
			 * * finYspeed1; System.out.println(aY); double bX = Math.cos(collisionAngle) *
			 * finXspeed2 + Math.cos(collisionAngle + Math.PI/2) * finYspeed2;
			 * System.out.println(bX); double bY = Math.sin(collisionAngle) * finXspeed2 +
			 * Math.sin(collisionAngle + Math.PI/2) * finYspeed2; System.out.println(bY);
			 * 
			 * for (Square a : continents[g1]) { a.setXVel((int)Math.round(aX));
			 * a.setYVel((int)Math.round(aY)); }
			 * 
			 * for (Square b : continents[g2]) { b.setXVel((int)Math.round(bX));
			 * b.setYVel((int)Math.round(bY)); } }
			 */

			// ******************************************

			int aXv = continents[g1].get(0).getXVel();
			int aYv = continents[g1].get(0).getYVel();
			int bXv = continents[g2].get(0).getXVel();
			int bYv = continents[g2].get(0).getYVel();

			for (Square a : continents[g1]) {
				a.setXVel(-aXv);
				a.setYVel(-aYv);
			}

			for (Square b : continents[g2]) {
				b.setXVel(-bXv);
				b.setYVel(-bYv);
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

		// clear it first - but then how are you going to detect collisions - use the
		// squares!
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				heightMap[i][j] = -1;

			}
		}

		// translate Squares X and Y integers to the heightmap 2d array index - giving
		// coordinates.
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				// if (squares[i][j].getX() > 0 && squares[i][j].getY() > 0) {
				heightMap[squares[i][j].getX()][squares[i][j].getY()] = squares[i][j].getHeight();

				// }
			}
		}
	}

	public void plotToGroupMap() {

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				groupMap[i][j] = -1;

			}
		}

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				groupMap[squares[i][j].getX()][squares[i][j].getY()] = squares[i][j].getGroup();
			}
		}
	}

	// plot new positions on the group map, and loop through to see if any planned
	// positions have continent in them.
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
