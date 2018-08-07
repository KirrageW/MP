import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
/**
 * Globe is the core of the system. It maintains lists of Squares that make up continents. It has the move() method which progresses
 * the model. Components plug into Globe to offer additional functionality, such as mountain generation or collision momentum mechanics,
 * and also continent generation.
 * 
 * Various 2d arrays are maintained separately from the list of Squares. These offer improved collision detection which bypasses the need to
 * access the squares, and also includes the heightMap. HeightMap is the imagined main output of the program, which a GUI can turn into
 * a visual rendering of the globe.
 * @author 2354535k
 *
 */
public class Globe {

	private int size; // size of an axis, of the square canvas
	protected Square[][] squares; // this is just a list. the 2d index has no bearing on the location of the
									// square on the visual map
	private int[][] heightMap; // this is a map. the 2d array index is derived from the x and y coordinates of
								// the squares
	private int[][] groupMap; // this will be a 2d array map that can check for the presence of two Squares in
								// the same place
	private int[][] superGroupMap; // this will be a 2d array map that checks positions of superContinents

	private ArrayList<Square>[] continents; // how continents are stored, rather than in a separate data structure - see report.
	private int[] superContinentSize; // the size, in Squares of supercontinents, so a smaller one can join a larger one.
	private int continentsCounter; // a useful counter for making continents and assigning them the correct index for continents.
	private ArrayList<Square> collisions; // temporary list to store squares involved in a collision. handy for mountain
											// building in the right places, and possibly crust deletion
	private int iceCover; // amount of Squares that are in the polar region and can accomodate ice sheet.

	// the continents involved in the collision
	private int g1;
	private int g2;

	private int superCount; // number of supercontinents
	

	/**
	 * Constructor. Initiates a list of Squares, proportional to the surface area of the given size.
	 * Initialises necessary instance variables. 
	 * @param size
	 */
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

		setHeightMap(new int[size][size]);
		groupMap = new int[size][size];
		superGroupMap = new int[size][size];
	}

	
	// makes square continents - very useful for testing
	/**
	 * Makes rectangular continents - useful for testing.
	 * @param x - top left point location coordinate x axis
	 * @param y -  top left point location coordinate y axis
	 * @param size 
	 */
	public void generateSquareContinent(int x, int y, int size) {

		ArrayList<Square> cont = new ArrayList<Square>();

		for (int i = x; i < x + size; i++) {
			for (int j = y; j < y + size; j++) {
				squares[i][j].setX(i);
				squares[i][j].setY(j);
				squares[i][j].setHeight(75);
				squares[i][j].setGroup(continentsCounter); // group should be same as continentsCounter
				squares[i][j].setSuperGroup(continentsCounter);
				getSuperContinentSize()[continentsCounter]++;
				cont.add(squares[i][j]);

			}
		}

		continents[continentsCounter] = cont;
		continentsCounter++;
	}

	/**
	 * The current generator method to make the starting continents. The current generator class returns a int[][] where 1 represents
	 * the location of continent. These are the returned into a new collective continent whose group number is continentsCounter.
	 * @param x - location
	 * @param y - location
	 * @param sizeX 
	 * @param sizeY
	 * @param group - which continent this will belong to.
	 */
	public void generate(int x, int y, int sizeX, int sizeY, int group) {

		// modular continent generator - it just needs to output an int[][] with 1 set
		// as land and 0 as sea.
		GeneratorModule generator = new GeneratorModule(size);
		generator.newNumbers(x, y, sizeX, sizeY, group);
		this.groupMap = generator.getMapping();

		ArrayList<Square> cont = new ArrayList<Square>();

		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if (groupMap[i][j] == 1) {
					squares[i][j].setX(i);
					squares[i][j].setY(j);
					squares[i][j].setHeight(75);
					squares[i][j].setGroup(group); // group should be same as continentsCounter
					squares[i][j].setSuperGroup(group);
					getSuperContinentSize()[continentsCounter]++;
					cont.add(squares[i][j]);

				}
			}
		}

		continents[continentsCounter] = cont;
		continentsCounter++;
	}

	/**
	 * Sets speed and direction of continents, by group number.
	 * @param group
	 * @param xVel 
	 * @param yVel
	 */
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

	/**
	 * The core method of the model, it moves Squares by their current xVel and yVel values on the grid. 
	 * Collision detection is engaged here, as well as collision handling using any modules that are plugged into the system.
	 * In its current form, much complexity is created by my super continents mechanics. I would advise any future user to remove this.
	 */
	public void move() {

		splitSuperContinents();

		checkBoundaries();

		if (checkCollision() == true) {

			// masses can simply equal number of squares
			int mass1 = continents[g1].size();
			int mass2 = continents[g2].size();

			// get the velocities
			double speedXfirst = continents[g1].get(0).getXVel();
			double speedYfirst = continents[g1].get(0).getYVel();
			double speedXsecond = continents[g2].get(0).getXVel();
			double speedYsecond = continents[g2].get(0).getYVel();

			////////////////////////// collision mathematics can be swappedand changed here. by using separate modules.

			CollisionGrazing minorCollision = new CollisionGrazing(mass1, mass2, speedXfirst, speedYfirst, speedXsecond,
					speedYsecond);

			for (Square a : continents[g1]) {
				a.setXVel(minorCollision.getNewXSpeed1());
				a.setYVel(minorCollision.getNewYSpeed1());
			}

			for (Square b : continents[g2]) {
				b.setXVel(minorCollision.getNewXSpeed2());
				b.setYVel(minorCollision.getNewYSpeed2());
			}

			// make mountains here using component
			MountainFormation makeMountains = new MountainFormation(minorCollision.getForce1(), minorCollision.getForce2(), g1, collisions, continents[g1], false);
			continents[g1] = makeMountains.getDeformedContinent();
			MountainFormation makeMountains2 = new MountainFormation(minorCollision.getForce1(), minorCollision.getForce2(), g2, collisions, continents[g2], false);
			continents[g2] = makeMountains2.getDeformedContinent();
			
			
			double proportion = (double) collisions.size()
					/ ((double) continents[g1].size() + (double) continents[g2].size());

			// should they combine? - if amount of continent collided (overlapped) is
			// greater than 10% of total continent, yes
			// MAJOR COLLISION
			if (proportion > 0.05) {
				CollisionMajor majorCollision = new CollisionMajor(mass1, mass2, speedXfirst, speedYfirst, speedXsecond,
						speedYsecond);
				
				// supercontinent handling
				int sg1 = continents[g1].get(0).getSuperGroup();
				int sg2 = continents[g2].get(0).getSuperGroup();

				// if both isolated - neither is in a supercontinent already
				int superContParent = 0;
				if (getSuperContinentSize()[sg1] >= getSuperContinentSize()[sg2]) {
					superContParent = sg1;
					getSuperContinentSize()[superContParent] = getSuperContinentSize()[superContParent]
							+ getSuperContinentSize()[sg2];
				} else {
					superContParent = sg2;
					getSuperContinentSize()[superContParent] = getSuperContinentSize()[superContParent]
							+ getSuperContinentSize()[sg1];
				}

				// so supercontinent ID is assigned as the bigger supercontinent ( remains same
				// if already is bigger supercontinent)
				for (Square a : continents[g1]) {
					a.setXVel(majorCollision.getTotalX());
					a.setYVel(majorCollision.getTotalY());
					a.setSuperGroup(superContParent);
				}

				for (Square b : continents[g2]) {
					b.setXVel(majorCollision.getTotalX());
					b.setYVel(majorCollision.getTotalY());
					b.setSuperGroup(superContParent);
				}

				// count here as changed
				getNumberOfSuperContinents();

				// updates speeds of Squares that are in this supercontinent.
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						if (squares[i][j].getGroup() == superContParent) {
							squares[i][j].setXVel(majorCollision.getTotalX());
							squares[i][j].setYVel(majorCollision.getTotalX());
						}
					}
				}
					
				// mountains created here using component.
				makeMountains = new MountainFormation(minorCollision.getForce1(), minorCollision.getForce2(), g1, collisions, continents[g1], false);
				continents[g1] = makeMountains.getDeformedContinent();
				makeMountains2 = new MountainFormation(minorCollision.getForce1(), minorCollision.getForce2(), g2, collisions, continents[g2], false);
				continents[g2] = makeMountains2.getDeformedContinent();
			}
		}

		// re check boundaries again to acocmodate for changed speeds.
		checkBoundaries();
	
		// make mountains on leading edge - to simulate the continent pushing against oceanic plate.
		ArrayList<Square> edges = new ArrayList<Square>();	
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
		MountainFormation makeMountains = new MountainFormation(20, 20, g1, edges, true); // I have chosen a low force for this currently - it could be calculated to account for mass.
		edges = makeMountains.getDeformedContinent();

		// move here. check for boundaries and handle accordingly
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (squares[i][j].getGroup() > 0) {
					squares[i][j].setX(squares[i][j].getX() + (squares[i][j].getXVel()));
					squares[i][j].setY(squares[i][j].getY() + (squares[i][j].getYVel()));
				}
			}
		}

		// plot new data
		plotMaps();
		iceCover(); 
	}

	/**
	 * Allows continents to cross boundaries and give the model a spherical feeling.
	 */
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
					}
					if (squares[i][j].getY() + squares[i][j].getYVel() >= size) {
						int amountOver = (squares[i][j].getY() + squares[i][j].getYVel()) - size;
						squares[i][j].setY(amountOver);
					}
					if (squares[i][j].getX() + squares[i][j].getXVel() < 0) {
						// find the amount it's gone under 0, set to size - that
						int amountUnder = squares[i][j].getX() + squares[i][j].getXVel() + size;
						squares[i][j].setX(amountUnder);
					}
					if (squares[i][j].getY() + squares[i][j].getYVel() < 0) {
						int amountUnder = squares[i][j].getY() + squares[i][j].getYVel() + size;
						squares[i][j].setY(amountUnder);
					}

				}
			}
		}
	}

	/**
	 * Important method to check for collisions. It iterates through each Squares position, and first checks to see
	 * if the new position contains continent, or is simply ocean. If land, it checks that it is not part of the same continent.
	 * If different, it checks whether it is not part of same super continent. If different, there is a collision. 
	 * Squares that fulfil these conditions are collected in a list called collisions. The group numbers of involved continents are 
	 * also saved in g1 and g2. 
	 * @return boolean. True indicates a collision.
	 */
	public boolean checkCollision() {

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

				// pre emptive check for overlapping - before actual move. one step ahead.

				if (groupMap[squares[i][j].getX() + (squares[i][j].getXVel())][squares[i][j].getY()
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
								// amass a list of overlapped squares
								collisions.add(squares[i][j]);
								collisions.add(getSquare(h, k, groupNumber)); // on the other continent
							}
						}
					}
				}
			}
		}

		// which continents are involved?
		g1 = squares[o][p].getGroup();
		g2 = groupMap[squares[o][p].getX() + squares[o][p].getXVel()][squares[o][p].getY() + squares[o][p].getYVel()];

		return collision;
	}

	/**
	 * A complex method regarding the ill thought out supercontinents. Using the chance of from a random integer being 2, out of a 
	 * range that can be varied (currently 15), it cycles through existing supercontinents and reverts the supercontinent ID 
	 * of a continent back to its continent ID. If the supercontinent is moving, it removes the smaller continent in this way, and 
	 * reverses the direction to hopefully give a nice divergence. Otherwise, it gives random velocities to the x and Y speeds.
	 * This signifies a new random convection current that has taken to move the continent, and could very well happen in any direciton.
	 * many times, the new direction is right back into the supercontinent, causing more mountains and disturbance. This isn't a terrible
	 * visual effect. 
	 */
	public void splitSuperContinents() {
		Random t = new Random();
		int possibility = t.nextInt(15); // increase number to control rarity of new boundary formation
		// basically - do any continents share the same supergroup ID?
		// if so, find the one that joined the larger supergroup, change its superGroup
		// ID back to its group ID.
		// inverse its direction to simulate a new divergent boundary.
		if (superCount > 0) { // is there even a supercontinent in existence?
			if (possibility == 2) { // rare chance of new divergent boundary forming

				// cycle through continents, find some that share supercontinent ID
				for (int i = 1; i < continentsCounter; i++) {
					for (int j = 1; j < continentsCounter; j++) {
						if (continents[i].get(0).getGroup() != continents[j].get(0).getGroup()) {
							if (continents[i].get(0).getSuperGroup() == continents[j].get(0).getSuperGroup()) {

								if (continents[i].get(0).getSuperGroup() != continents[i].get(0).getGroup()) {

									for (Square a : continents[j]) {
										a.setSuperGroup(a.getGroup());
									}
									// if supercontinent is stationary, set random values of thrust
									if (continents[i].get(0).getXVel() == 0 && continents[i].get(0).getYVel() == 0) {
										int randXspd = t.nextInt(4) - t.nextInt(4);
										int randYspd = t.nextInt(4) - t.nextInt(4);
										for (Square a : continents[i]) {
											a.setSuperGroup(a.getGroup());
											a.setXVel(randXspd);
											a.setYVel(randYspd);
										}
									} else {
										// otherwise invert direction for nice divergence.
										for (Square a : continents[i]) {
											a.setSuperGroup(a.getGroup());
											a.setXVel(-a.getXVel());
											a.setYVel(-a.getYVel());
										}
									}
								} else {
									
									for (Square a : continents[i]) {
										a.setSuperGroup(a.getGroup());
									}
									
									if (continents[j].get(0).getXVel() == 0 && continents[j].get(0).getYVel() == 0) {
										int randXspd = t.nextInt(4) - t.nextInt(4);
										int randYspd = t.nextInt(4) - t.nextInt(4);
										for (Square a : continents[j]) {
											a.setSuperGroup(a.getGroup());
											a.setXVel(randXspd);
											a.setYVel(randYspd);
										}
									} else {

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
				// count here as changed
				getNumberOfSuperContinents();
			}
		}
	}

	
	/**
	 * A loop to update the positions on all the used 2d maps.
	 */
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

	// returns a square matching an x and y coordinate from the group map or height
	// map
	/**
	 * Returns a square from a known continent.
	 * @param x (x axis location)
	 * @param y (y axis location)
	 * @param group (continent id)
	 * @return the Square, or null if not found.
	 */
	public Square getSquare(int x, int y, int group) {

		for (Square a : continents[group]) {
			if (a.getX() == x && a.getY() == y) {
				return a;
			}
		}
		return null;

	}


	/**
	 * A quick implentation of ice sheet mechanics. If land is in the poles, ice cover is increased.
	 * @return
	 */
	public double iceCover() {
		iceCover = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (heightMap[i][j] > 0 && heightMap[i][j] < 175) { // is it land on height map, and below a certain height
					//(ice on ocean doesn't count, ice sheets wont form on high ground)
					if (j <= size / 5 || j > size - (size / 5)) {
						iceCover++;
					}
				}
			}
		}

		double icePerCent = ((double) iceCover / (250 * 250)) * 100;

		// roughly taking 1% cover of ice above the water level, of total world surface,
		// to translate to globally 10m of sea level.
		return icePerCent * 10;
	}

	/**
	 * Getter for heightMap
	 * @return int[][] height map
	 */
	public int[][] getHeightMap() {
		return heightMap;
	}

	/**
	 * Setter for heightMap
	 * @param map int[][]
	 */
	public void setHeightMap(int[][] map) {
		this.heightMap = map;
	}

	/**
	 * Getter for groupMap
	 * @return int [][] groups
	 */
	public int[][] getGroupMap() {
		return groupMap;
	}

	/**
	 * Getter for size of model
	 * @return size int
	 */
	public int getSize() {
		return size;
	}

	/**
	 * A counting method for the number of superocntinents currently existing.
	 * If two continents share the same supercontinent ID, there is a supercontinent. 
	 * Store the continent IDs in hash set so duplicates aren't recounted.
	 */
	public void getNumberOfSuperContinents() {
		HashSet supers = new HashSet();

		for (int i = 1; i < continentsCounter; i++) {
			for (int j = 1; j < continentsCounter; j++) {
				if (continents[i].get(0).getGroup() != continents[j].get(0).getGroup()) {
					if (continents[i].get(0).getSuperGroup() == continents[j].get(0).getSuperGroup()) {
						supers.add(continents[i].get(0).getSuperGroup());
					}
				}
			}
		}
		superCount = supers.size();
	}

	/**
	 * Getter for supercontinent number
	 * @return how  many supercontinents there are.
	 */
	public int getSuperCount() {
		return superCount;
	}

	/**
	 * Getter for the size of each supercontinent. Each one is an index position reflecting the continent ID
	 * of the parent continent - the first continent of the supercontinent.
	 * @return
	 */
	public int[] getSuperContinentSize() {

		return superContinentSize;
	}

	/**
	 * return the full list of continents. All Squares that are used in the model (ones that aren't ocean or null) 
	 * are in these lists.
	 * @return
	 */
	public ArrayList<Square>[] getContinents() {
		return continents;
	}
	
	/**
	 * return the number of Squares involved in a collision (overlapping). Useful for testing collision mechanics.
	 * @return
	 */
	public int getNumCollisions() {
		return collisions.size();
	}
}
