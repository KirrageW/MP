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

	private int[][] superGroupMap; // this will be a 2d array map that checks positions of superContinents

	private ArrayList<Square>[] continents;

	private int[] superContinentSize;

	private int continentsCounter;
	private ArrayList<Square> collisions;

	private boolean stop;
	private int iceCover;

	public Globe(int size) {
		iceCover = 0;
		stop = true;
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

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				getHeightMap()[i][j] = -1;
			}
		}

		// new 2d array which takes coordinates from Squares, and stores their group
		groupMap = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				groupMap[i][j] = -1;
			}
		}

		superGroupMap = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				superGroupMap[i][j] = -1;
			}
		}
	}

	// **********************************************************************************************************
	// TO DO:

	// refactor all of it - squares and stuff

	// add GUI stuff

	// sea level

	// big splits in continents - boundary collisions.

	// **********************************************************************************************************

	public void newNumbers(int x, int y, int size, int sizeY, int group) {
		ArrayList<Square> cont = new ArrayList<Square>();

		Continent con = new Continent(continentsCounter);

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

		for (int i = 7; i < this.size - 7; i++) {
			for (int j = 7; j < this.size - 7; j++) {
				groupMap[i][j] = getNeighbours(i, j);
			}
		}

		// you need more or fewer of these, depending on size of grid.

		closer();
		closer1();
		focus();
		closer1();
		focus();
		closer1();
		focus();
		closer();

		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if (groupMap[i][j] == 1) {
					squares[i][j].setX(i);
					squares[i][j].setY(j);
					squares[i][j].setHeight(75);
					squares[i][j].setGroup(group); // group should be same as continentsCounter
					squares[i][j].setSuperGroup(group); 
					//System.out.println("group variable is "+ group + " and contCounter is "+ continentsCounter);
					superContinentSize[continentsCounter]++;
					cont.add(squares[i][j]);
					
					
				}
			}
		}

		System.out.println("size of superCon1 :"+superContinentSize[continentsCounter]+ ", for group "+ group);
		
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

		// ADD IN RANDOM CHANCE OF SUPERCONTINENTS BREAKING UP.
		
		
		
		// captures specific squares.
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

								// IF NUMBER IN COLLISIONS IS ABOVE PROPORTION IN CONTINENTS, THEN COMBINE
								// CONTINENTS
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

			// System.out.println(g1);
			// System.out.println(g2);
			// masses can simply equal number of squares
			int mass1 = continents[g1].size();
			int mass2 = continents[g2].size();

			// MOUNTAINS

			// MOMENTUM SPREAD AMOUNG NUMBER IN COLLISIONS... p = mv.
			// calculate total momentum of plates

			double speedXfirst = continents[g1].get(0).getXVel();
			double speedYfirst = continents[g1].get(0).getYVel();
			double speedXsecond = continents[g2].get(0).getXVel();
			double speedYsecond = continents[g2].get(0).getYVel();

			// trigonometry to get velocity from x and y speeds
			double velocity1 = Math.sqrt((speedXfirst * speedXfirst) + (speedYfirst * speedYfirst));
			double velocity2 = Math.sqrt((speedXsecond * speedXsecond) + (speedYsecond * speedYsecond));

			double force1 = velocity1 * mass1;
			double force2 = velocity2 * mass2;

			// calculate how many squares are involved in each continent
			int numberOfSquaresInFirst = 0;

			for (Square q : collisions) {
				if (q != null) {
					if (q.getGroup() == g1) { // null pointer
						numberOfSquaresInFirst++;
					}
				}
			}

			int numberInSecond = collisions.size() - numberOfSquaresInFirst;

			// System.out.println(numberInSecond + " " + numberOfSquaresInFirst);
			// spread force among number of squares in each - gives proportional mountain
			// building

			// switch because it's a transfer of energy.
			force1 = force1 / numberInSecond;
			// System.err.println(force1);

			force2 = force2 / numberOfSquaresInFirst;
			// System.err.println(force2);

			if (force1 > 400) {
				force1 = 400;
			}
			if (force2 > 400) {
				force2 = 400;
			}

			if (force1 < 20) {
				force1 = 20;
			}
			if (force2 < 20) {
				force2 = 20;
			}

			// convert force to rating between 0 and 250 - for height map boundaries.
			double oldRange = (400 - 20);
			double newRange = (250 - 30);

			force1 = ((force1 - 20) / oldRange) * newRange + 30;
			force2 = ((force2 - 20) / oldRange) * newRange + 30;

			// System.out.println("first: " + force1 + " second: " + force2 + " " +
			// collisions.size());

			// apply height changes to affected squares and near neighbours
			for (Square v : collisions) {
				if (v.getGroup() == g1)
					getNeighbours(v, (int) Math.round(force1));
				else
					getNeighbours(v, (int) Math.round(force2));
			}

			double proportion = (double) collisions.size()
					/ ((double) continents[g1].size() + (double) continents[g2].size());

			// should they combine? - if amount of continent collided (overlapped) is
			// greater than 10% of total continent, yes
			if (proportion > 0.001) {

				// momentum before of both
				double Ax = mass1 * speedXfirst;
				double Ay = mass1 * speedYfirst;

				double Bx = mass2 * speedXsecond;
				double By = mass2 * speedYsecond;

				// total mass
				double totalX = Ax + Bx;
				double totalY = Ay + By;

				totalX = totalX / (mass1 + mass2);
				totalY = totalY / (mass1 + mass2);

				int sg1 = continents[g1].get(0).getSuperGroup();
				int sg2 = continents[g2].get(0).getSuperGroup();

				
				// if both isolated - neither is in a supercontinent already
				int superContParent = 0;
				if (superContinentSize[sg1] >= superContinentSize[sg2]) {
					superContParent = sg1;
					System.out.println("1Before: "+superContinentSize[sg1]);
					superContinentSize[superContParent] = superContinentSize[superContParent]+ superContinentSize[sg2]; // add smaller size to larger super continent size
					System.out.println("1After: "+superContinentSize[sg1]);
				}
				else {
					superContParent = sg2;
					System.out.println("2Before: "+superContinentSize[sg2]);
					superContinentSize[superContParent] = superContinentSize[superContParent]+ superContinentSize[sg1];
					System.out.println("2After: "+superContinentSize[sg2]);
				}
						
					// so supercontinent ID is assigned as the bigger supercontinent ( remains same if already is bigger supercontinent)
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
					
					for (int i = 0 ; i < size; i ++) {
						for (int j = 0; j < size; j ++) {
							if (squares[i][j].getSuperGroup() == superContParent) {
								squares[i][j].setXVel(totalX);
								squares[i][j].setYVel(totalY);
							}
						}
					}
				
					System.out.println(superContParent);
					System.out.println("The larger one is now "+ superContinentSize[superContParent]);
				
				
				
				
				// make sure corrent supergroup is assigned if one is already in one, or both
				// are isolated etc.
				// furthermore, if one is already in it, or both are, then the other continents'
				// speeds in these groups will need to be updated
				// AND if a larger supercontinent is being made, then again these other squares
				// will need to have that number updated too.
			

				// maybe to speed this up, can add continents physically to supercontinent
				// collections, to speed up speed changes etc...
				// and then get the size - the bigger or equal supercontient remains the same
				// ID, the other sqitches and its squares are added!.

			}

			else {

				// MOVEMENT
				double nx1 = speedXfirst * (mass1 - mass2) + 2 * (mass2 * continents[g2].get(0).getXVel());
				double ny1 = speedYfirst * (mass1 - mass2) + 2 * (mass2 * continents[g2].get(0).getYVel());
				double nx2 = speedXsecond * (mass2 - mass1) + 2 * (mass1 * continents[g1].get(0).getXVel());
				double ny2 = speedYsecond * (mass2 - mass1) + 2 * (mass1 * continents[g1].get(0).getYVel());

				// new speeds
				nx1 = nx1 / (mass1 + mass2);

				ny1 = ny1 / (mass1 + mass2);

				nx2 = nx2 / (mass1 + mass2);

				ny2 = ny2 / (mass1 + mass2);

				for (Square a : continents[g1]) {
					a.setXVel(nx1);
					a.setYVel(ny1);
				}

				for (Square b : continents[g2]) {
					b.setXVel(nx2);
					b.setYVel(ny2);
				}
				
				// and those in supercontinent - but this doesnt take into account the weight of the supercontinent, which it is now supposedly affecting - mistake.
				int sg1 = continents[g1].get(0).getSuperGroup();
				int sg2 = continents[g2].get(0).getSuperGroup();
				
				for (int i = 0 ; i < size; i ++) {
					for (int j = 0; j < size; j ++) {
						if (squares[i][j].getSuperGroup() > 0) {
							if (squares[i][j].getSuperGroup() == sg1) {
							squares[i][j].setXVel(nx1);
							squares[i][j].setYVel(ny1);
							}
						}
							if (squares[i][j].getSuperGroup() == sg2) {
							squares[i][j].setXVel(nx2);
							squares[i][j].setYVel(ny2);
							}
						
					}
				}
				
				
				
			}

			// re-check for boundary crossings again
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
		plotToSuperGroupMap();
		iceCover();
	}

	public void getNeighbours(Square x, int force) {

		// get group of square involved
		int g = x.getGroup();

		for (int i = 1; i < 5; i++) {
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

	// run through all the Squares, getting their X and Y coordinates for 2d height
	// array
	public void plotToHeightMap() {

		// reset height map - overwrites old positions
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				heightMap[i][j] = -1;

			}
		}

		// translate Squares X and Y integers to the heightmap 2d array index - giving
		// coordinates.
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

	public void plotToSuperGroupMap() {

		// reset group map - overwrites old positions
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				superGroupMap[i][j] = -1;

			}
		}

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				superGroupMap[squares[i][j].getX()][squares[i][j].getY()] = squares[i][j].getSuperGroup(); // index out
																											// of bounds
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
		for (int i = 1; i < continents.length; i++) {
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

	// wrong sides
	public void iceCover() {
		iceCover = 0;
		for (int i = 0; i < size / 5; i++) {
			for (int j = 0; j < size; j++) {
				if (heightMap[i][j] > 0 && heightMap[i][j] < 175) {
					iceCover++;
				}
			}
		}
		for (int i = size - (size / 5); i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (heightMap[i][j] > 0 && heightMap[i][j] < 175) {
					iceCover++;
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
		// TODO Auto-generated method stub
		return groupMap;
	}

}
