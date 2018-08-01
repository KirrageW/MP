import java.util.ArrayList;
import java.util.Random;

public class Globe {

	private int size;
	protected Square[][] squares; // this is just a list. the 2d index has no bearing on the location of the square on the visual map	
	private int[][] heightMap; // this is a map. the 2d array index is derived from the x and y coordinates of the squares
	private int[][] groupMap; // this will be a 2d array map that can check for the presence of two Squares in the same place								
	private int[][] superGroupMap; // this will be a 2d array map that checks positions of superContinents

	private ArrayList<Square>[] continents;
	private int[] superContinentSize;
	private int continentsCounter;
	private ArrayList<Square> collisions; // temporary list to store squares involved in a collision. handy for mountain building in the right places, and possibly crust deletion
	private int iceCover;

	public Globe(int size) {
		iceCover = 0;
		continents = new ArrayList[10];
		superContinentSize = new int[10];
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


		// new 2d array which takes coordinates from Squares, and stores their group
		groupMap = new int[size][size];
	

		superGroupMap = new int[size][size];
	
	}

	// **********************************************************************************************************
	// TO DO:

	// add GUI stuff

	// Junit tests: when superocntinents combine - new masses
	// conservation of energies
	// **********************************************************************************************************

	public void newNumbers(int x, int y, int size, int sizeY, int group) {
		ArrayList<Square> cont = new ArrayList<Square>();

		Random ran = new Random();
		
	

		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				groupMap[i][j] = 0;
			}
		}

		for (int i = x + 5; i < x + size - 5; i++) {
			for (int j = y + 5; j < y + sizeY - 5; j++) {
				int number = ran.nextInt(4);
				groupMap[i][j] = number;
			}
		}

		for (int i = x + 5; i < x + size; i++) {
			for (int j = y + 5; j < y + sizeY; j++) {
				groupMap[i][j] = getNeighbours(i, j);
			}
		}

		// you need more or fewer of these, depending on size of grid.
		closer(1);
		closer(1);
		closer(2);
		closer(2);
		focus();
		closer(2);
		focus();
		focus();
		focus();
		closer(1);

		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if (groupMap[i][j] == 1) {
					squares[i][j].setX(i);
					squares[i][j].setY(j);
					squares[i][j].setHeight(75);
					squares[i][j].setGroup(group); // group should be same as continentsCounter
					squares[i][j].setSuperGroup(group);
					superContinentSize[continentsCounter]++;
					cont.add(squares[i][j]);

				}
			}
		}

		continents[continentsCounter] = cont;
		continentsCounter++;
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

		int k = groupMap[i - 2][j - 2];
		int l = groupMap[i][j - 2];
		int m = groupMap[i + 2][j - 2];
		int n = groupMap[i + 2][j];
		int o = groupMap[i + 2][j + 2];
		int p = groupMap[i][j + 2];
		int q = groupMap[i - 2][j + 2];
		int r = groupMap[i - 2][j];

		int s = groupMap[i - 3][j - 3];
		int t = groupMap[i][j - 3];
		int u = groupMap[i + 3][j - 3];
		int v = groupMap[i + 3][j];
		int w = groupMap[i + 3][j + 3];
		int x = groupMap[i][j + 3];
		int y = groupMap[i - 3][j + 3];
		int z = groupMap[i - 3][j];

		return (a + b + c + d + e + f + g + h + k + l + m + n + o + p + q + r + s + t + u + v + w + x + y + z) / 24;

	}

	// degree param represents immediate neighbours (1) or next neighbours (2)
	public void closer(int degree) {

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int numberOfNeighbours = 0;
				int req = 3;
				if (groupMap[i][j] > 0) {

					if (groupMap[i - degree][j - degree] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i][j - degree] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i + degree][j - degree] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i + degree][j] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i + degree][j + degree] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i][j + degree] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i - degree][j + degree] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i - degree][j] > 0) {
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

	// sets velocities of all members of chosen group CHANGE TO USE CONTINENTS
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

		Random t = new Random();
		int possibility = t.nextInt(15); // increase number to control rarity of new boundary formation
		// basically - do any continents share the same supergroup ID?
		// if so, find the one that joined the larger supergroup, change its superGroup
		// ID back to its group ID
		// inverse its direction to simulate a new divergent boundary.
		if (possibility == 2) {
			System.out.println("It's happening");
			for (int i = 1; i < continentsCounter; i++) {
				for (int j = 1; j < continentsCounter; j++) {
					if (continents[i].get(0).getGroup() != continents[j].get(0).getGroup()) {
						if (continents[i].get(0).getSuperGroup() == continents[j].get(0).getSuperGroup()) {
							System.out.println("It's happening1");
							if (continents[i].get(0).getSuperGroup() != continents[i].get(0).getGroup()) {
								System.out.println("It's happening2");
								if (continents[i].get(0).getXVel() == 0 && continents[i].get(0).getYVel() == 0) {
									int randXspd = t.nextInt(4) - t.nextInt(4);
									int randYspd = t.nextInt(4) - t.nextInt(4);
									for (Square a : continents[i]) {
										a.setSuperGroup(a.getGroup());
										a.setXVel(randXspd);
										a.setYVel(randYspd);
									}
								} else {
									for (Square a : continents[i]) {
										a.setSuperGroup(a.getGroup());
										a.setXVel(-a.getXVel());
										a.setYVel(-a.getYVel());

									}

								}
							} else {
								if (continents[j].get(0).getXVel() == 0 && continents[j].get(0).getYVel() == 0) {
									int randXspd = t.nextInt(4) - t.nextInt(4);
									int randYspd = t.nextInt(4) - t.nextInt(4);
									for (Square a : continents[j]) {
										a.setSuperGroup(a.getGroup());
										a.setXVel(randXspd);
										a.setYVel(randYspd);
									}
								} else {
									System.out.println("It's happening3");
									for (Square a : continents[j]) {
										a.setSuperGroup(a.getGroup());
										a.setXVel(-a.getXVel());
										a.setYVel(-a.getYVel());
									}
								}
							}
						}
					}
				}
			}
		}

		// captures specific squares.
		collisions = new ArrayList<Square>(); // reset collisions each time
		int groupNumber = -2;
		boolean collision = false;

		int o = 0;
		int p = 0;
		int h = 0;
		int k = 0;

		checkBoundaries();

		// NEW ************* maybe try putting this check before the boundaries check
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {

				// pre emptive check for overlapping - before actual move. one step ahead.

				if (groupMap[squares[i][j].getX() + (squares[i][j].getXVel())][squares[i][j].getY() // arrayIndexOutofBounds:
																									// 152 , 150 , -2 ,
																									// 150
						+ (squares[i][j].getYVel())] > 0) {
					if (groupMap[squares[i][j].getX() + (squares[i][j].getXVel())][squares[i][j].getY()
							+ (squares[i][j].getYVel())] != squares[i][j].getGroup()) {

						// is supergroup present here.
						if (superGroupMap[squares[i][j].getX() + (squares[i][j].getXVel())][squares[i][j].getY()
								+ (squares[i][j].getYVel())] > 0) {
							// is it part of same supergroup.
							if (superGroupMap[squares[i][j].getX() + (squares[i][j].getXVel())][squares[i][j].getY()
									+ (squares[i][j].getYVel())] != squares[i][j].getSuperGroup()) {

								// save i and j of first instance - to get groups and stuff
								if (collision == false) {
									o = i;
									p = j;
									groupNumber = groupMap[squares[i][j].getX()
											+ (squares[i][j].getXVel())][squares[i][j].getY()
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

			}
		}

		if (collision == true) {

			// which groups are involved?
			int g1 = squares[o][p].getGroup();
			int g2 = groupMap[squares[o][p].getX() + squares[o][p].getXVel()][squares[o][p].getY()
					+ squares[o][p].getYVel()];

			// masses can simply equal number of squares
			int mass1 = continents[g1].size();
			int mass2 = continents[g2].size();

			// MOMENTUM SPREAD AMOUNG NUMBER IN COLLISIONS... p = mv.
			// calculate total momentum of plates
			double speedXfirst = continents[g1].get(0).getXVel();
			double speedYfirst = continents[g1].get(0).getYVel();
			double speedXsecond = continents[g2].get(0).getXVel();
			double speedYsecond = continents[g2].get(0).getYVel();

			double velocity1 = Math.sqrt((speedXfirst * speedXfirst) + (speedYfirst * speedYfirst));
			double velocity2 = Math.sqrt((speedXsecond * speedXsecond) + (speedYsecond * speedYsecond));

			double force1 = velocity1 * mass1;
			double force2 = velocity2 * mass2;

			// mountain formation coefficient - proportion of force used in mountain
			// building
			double mountainCoefficient = 0.25;

			force1 = force1 * 0.25;
			force2 = force2 * 0.25;

			makeMountains(force1, force2, g1, collisions);

			// need to slow down the continents a little, but not change their directions at
			// all...

			// speed coefficient - what proportion of this energy is NOT lost to mountain
			// formation?
			double energyCoefficient = 1 - mountainCoefficient;

			double minorCollisionAx = energyCoefficient * speedXfirst;
			double minorCollisionAy = energyCoefficient * speedYfirst;
			double minorCollisionBx = energyCoefficient * speedXsecond;
			double minorCollisionBy = energyCoefficient * speedYsecond;

			for (Square a : continents[g1]) {
				a.setXVel(minorCollisionAx);
				a.setYVel(minorCollisionAy);
			}

			for (Square b : continents[g2]) {
				b.setXVel(minorCollisionBx);
				b.setYVel(minorCollisionBy);
			}

			double proportion = (double) collisions.size()
					/ ((double) continents[g1].size() + (double) continents[g2].size());

			// should they combine? - if amount of continent collided (overlapped) is
			// greater than 10% of total continent, yes
			// MAJOR COLLISION
			if (proportion > 0.10) {

				double Ax = mass1 * speedXfirst;
				double Ay = mass1 * speedYfirst;
				double Bx = mass2 * speedXsecond;
				double By = mass2 * speedYsecond;

				// total mass
				double totalX = Ax + Bx;
				double totalY = Ay + By;

				totalX = totalX / (mass1 + mass2);
				totalY = totalY / (mass1 + mass2);

				// inelastic coefficient - what proportion of this energy is lost to mountain
				// formation?
				energyCoefficient = 0.5; // more mountain building, more speed loss

				totalX = totalX * energyCoefficient;
				totalY = totalY * energyCoefficient;

				int sg1 = continents[g1].get(0).getSuperGroup();
				int sg2 = continents[g2].get(0).getSuperGroup();

				// if both isolated - neither is in a supercontinent already
				int superContParent = 0;
				if (superContinentSize[sg1] >= superContinentSize[sg2]) {
					superContParent = sg1;
					superContinentSize[superContParent] = superContinentSize[superContParent] + superContinentSize[sg2]; // add
																															// smaller
																															// to
																															// larger
																															// one

				} else {
					superContParent = sg2;
					superContinentSize[superContParent] = superContinentSize[superContParent] + superContinentSize[sg1];
				}

				// so supercontinent ID is assigned as the bigger supercontinent ( remains same
				// if already is bigger supercontinent)
				for (Square a : continents[g1]) {
					a.setXVel(totalX);
					a.setYVel(totalY);
					a.setSuperGroup(superContParent);
				}

				for (Square b : continents[g2]) {
					b.setXVel(totalX);
					b.setYVel(totalY);
					b.setSuperGroup(superContParent);
				}

				// updates speeds of squares that are in this supercontinent now.
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						if (squares[i][j].getGroup() == superContParent) {
							squares[i][j].setXVel(totalX);
							squares[i][j].setYVel(totalY);
						}
					}
				}

				force1 = velocity1 * mass1;
				force2 = velocity2 * mass2;

				// mountain formation coefficient - proportion of force used in mountain
				// building - all energy makes mountains
				mountainCoefficient = 1.00;

				force1 = force1 * mountainCoefficient;
				force2 = force2 * mountainCoefficient;

				makeMountains(force1, force2, g1, collisions);

			}

			else {

			}

			// re-check for boundary crossings again
			checkBoundaries();
		}

		// carry on...
		ArrayList<Square> edges = new ArrayList<Square>();

		// make mountains on leading edge
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (squares[i][j].getHeight() > 0) {
					// if new location is ocean, must be on leading edge
					if (groupMap[squares[i][j].getX() + (squares[i][j].getXVel())][squares[i][j].getY()
							+ (squares[i][j].getYVel())] == 0) {
						// no reduction in speed, because it is being driven by convection current
						edges.add(squares[i][j]);

					}
				}
			}
		}
		// how much force, not much

		makeMountains(50, 50, edges);

		// move here. check for boundaries and handle accordingly
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (squares[i][j].getGroup() > 0) {
					squares[i][j].setX(squares[i][j].getX() + (squares[i][j].getXVel()));
					squares[i][j].setY(squares[i][j].getY() + (squares[i][j].getYVel()));
				}
			}
		}

		// threads that all join before ending the move method?
		plotMaps();
		iceCover();
		// erosion(); is broken
	}

	public void checkBoundaries() {

		// repeat twice, as corners take squares over both horizontal and vertical
		// boundaries
		for (int times = 0; times < 2; times++) {
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
		}
	}

	public void makeMountains(double force1, double force2, int g1, ArrayList<Square> squares) {
		if (force1 > 300) {
			force1 = 500;
		}
		if (force2 > 300) {
			force2 = 500;
		}

		if (force1 < 20) {
			force1 = 20;
		}
		if (force2 < 20) {
			force2 = 20;
		}

		// convert force to rating between 0 and 250 - for height map boundaries.
		double oldRange = (500 - 20);
		double newRange = (250 - 30);

		force1 = ((force1 - 20) / oldRange) * newRange + 30;
		force2 = ((force2 - 20) / oldRange) * newRange + 30;

		// apply height changes to affected squares and near neighbours
		for (Square v : squares) {

			if (v.getGroup() == g1)
				getNeighbours(8, v, (int) Math.round(force1));
			else
				getNeighbours(8, v, (int) Math.round(force2));
		}

	}

	public void makeMountains(double force1, double force2, ArrayList<Square> squares) {
		if (force1 > 300) {
			force1 = 500;
		}
		if (force2 > 300) {
			force2 = 500;
		}

		if (force1 < 20) {
			force1 = 20;
		}
		if (force2 < 20) {
			force2 = 20;
		}

		// convert force to rating between 0 and 250 - for height map boundaries.
		double oldRange = (500 - 20);
		double newRange = (250 - 30);

		force1 = ((force1 - 20) / oldRange) * newRange + 30;
		force2 = ((force2 - 20) / oldRange) * newRange + 30;

		// apply height changes to affected squares and near neighbours
		for (Square v : squares) {

			getNeighbours(4, v, (int) Math.round(force1));
		}

	}

	public void getNeighbours(int times, Square x, int force) {

		// get group of square involved
		int g = x.getGroup();

		for (int i = 1; i < times; i++) {
			for (Square a : continents[g]) {
				if (force < 5) {
					return;
				}

				// need to check them off, so they are not overwritten

				if (a.getX() == x.getX() && a.getY() == x.getY() && a.checked() == false) {
					a.setHeight(a.getHeight() + force / i);
					a.setChecked(true);
				}

				if (a.getX() == x.getX() + i && a.getY() == x.getY() + i && a.checked() == false) {
					a.setHeight(a.getHeight() + force / i);
					a.setChecked(true);
				}

				if (a.getX() == x.getX() - i && a.getY() == x.getY() - i && a.checked() == false) {
					a.setHeight(a.getHeight() + force / i);
					a.setChecked(true);
				}

				if (a.getX() == x.getX() && a.getY() == x.getY() + i && a.checked() == false) {
					a.setHeight(a.getHeight() + force / i);
					a.setChecked(true);
				}

				if (a.getX() == x.getX() + i && a.getY() == x.getY() && a.checked() == false) {
					a.setHeight(a.getHeight() + force / i);
					a.setChecked(true);
				}
				if (a.getX() == x.getX() - i && a.getY() == x.getY() && a.checked() == false) {
					a.setHeight(a.getHeight() + force / i);
					a.setChecked(true);
				}

				if (a.getX() == x.getX() && a.getY() == x.getY() - i && a.checked() == false) {
					a.setHeight(a.getHeight() + force / i);
					a.setChecked(true);
				}
				if (a.getX() == x.getX() - i && a.getY() == x.getY() + i && a.checked() == false) {
					a.setHeight(a.getHeight() + force / i);
					a.setChecked(true);
				}

				if (a.getX() == x.getX() + i && a.getY() == x.getY() - i && a.checked() == false) {
					a.setHeight(a.getHeight() + force / i);
					a.setChecked(true);
				}

			}
		}
	}

	// run through all the Squares, getting their X and Y coordinates
	public void plotMaps() {

		// reset height map - overwrites old positions
		heightMap = new int[size][size];
		groupMap = new int[size][size];
		superGroupMap = new int[size][size];

		// translate Squares X and Y integers to the heightmap 2d array index - giving
		// coordinates.
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				heightMap[squares[i][j].getX()][squares[i][j].getY()] = squares[i][j].getHeight();
				groupMap[squares[i][j].getX()][squares[i][j].getY()] = squares[i][j].getGroup();
				superGroupMap[squares[i][j].getX()][squares[i][j].getY()] = squares[i][j].getSuperGroup();
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

	// returns a square matching an x and y coordinate from the group map or height
	// map
	public Square getSquare(int x, int y, int group) {

		for (Square a : continents[group]) {
			if (a.getX() == x && a.getY() == y) {
				return a;
			}
		}
		return null;

	}

	// it's got to be within each continent...
	public void erosion() {

		int totalMaterial = 0;
		for (int i = 1; i < continentsCounter; i++) {
			for (Square a : continents[i]) {
				// for squares greater than the default starting height - those that have been
				// thrust upwards
				if (a.getHeight() > 75) {

					int material = a.getHeight() - a.getHeight() / 5;
					a.setHeight(material);
					totalMaterial = totalMaterial + material;
					// and give to neighbours that are lower...

				}
				// disperse material among nearby squares...
			}
			for (Square a : continents[i]) {
				if (a.getHeight() < 150) {
					a.setHeight(totalMaterial / continents[i].size());
				}
			}
		}
	}

	public void iceCover() {
		iceCover = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (heightMap[i][j] > 0 && heightMap[i][j] < 175) {
					if (j <= size / 5 || j > size - (size / 5)) {

						iceCover++;
					}
				}
			}
		}

		double icePerCent = ((double) iceCover / (250 * 250)) * 100;

		// roughly taking 1% cover of ice above the water level, of total world surface,
		// to translate to globally 10m of sea level.
		System.err.println("Sea level change = -" + icePerCent * 10 + "m");
	}

	public int[][] getHeightMap() {
		return heightMap;
	}

	public void setHeightMap(int[][] map) {
		this.heightMap = map;
	}

	public int[][] getGroupMap() {
		return groupMap;
	}

	public int getSize() {
		return size;
	}

}
