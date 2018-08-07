/**
 * A component that calculates speed changes and forces during a minor, or grazing, collision between two 
 * continents. In the current implementation, different types of collision are defined by the proportion of overlapping Squares 
 * to the total sizes of continents, during collisions. 
 * In this grazing collision, i do not expect that continents come to a stop, but are rather slowed down, and some minor mountain formation
 * occurs at the point of contact.
 * @author 2354535k
 *
 */
public class CollisionGrazing {
	
	// masses of two continents in question
	private int mass1; 
	private int mass2;
	
	// speeds
	private double speedXfirst;
	private double speedYfirst;
	private double speedXsecond;
	private double speedYsecond;
	
	// new speeds
	private double newXfirst;
	private double newYfirst;
	private double newXsecond;
	private double newYsecond;
	
	// forces
	private double force1;
	private double force2;
	
	// what proportion of energy is taken from speed and used to create mountains? Inelastic collision
	private double mountainCoefficient;
	
	/**
	 * Constructor that needs basic information from the Globe model.
	 * @param mass1 - of first continent
	 * @param mass2 - of second continent
	 * @param x1 - x axis speed of first
	 * @param y1 - y axis speed of first
	 * @param x2 - x axis speed of second
	 * @param y2 - y axis speed of second
	 */
	public CollisionGrazing(int mass1, int mass2, double x1, double y1, double x2, double y2) {
		
		this.mass1 = mass1;
		this.mass2 = mass2;
		this.speedXfirst = x1;
		this.speedYfirst = y1;
		this.speedXsecond = x2;
		this.speedYsecond = y2;
		
		collisionMechanics();
		
	}
	
	/**
	 * The calculations are in this method. Pythagoras's method is used to calculated overall speed, with which 
	 * forces are derived from (f = ma). 
	 * Then speeds are individually reduced in both axes in relation to mountain coefficient value.
	 */
	public void collisionMechanics() {
		
	double velocity1 = Math.sqrt((speedXfirst * speedXfirst) + (speedYfirst * speedYfirst));
	double velocity2 = Math.sqrt((speedXsecond * speedXsecond) + (speedYsecond * speedYsecond));

	force1 = velocity1 * mass1;
	force2 = velocity2 * mass2;

	// mountain formation coefficient - proportion of force used in mountain
	// building
	mountainCoefficient = 0.25;

	force1 = force1 * 0.25;
	force2 = force2 * 0.25;

	// need to slow down the continents a little, but not change their directions at
	// all...

	// speed coefficient - what proportion of this energy is NOT lost to mountain
	// formation?
	double energyCoefficient = 1 - mountainCoefficient;

	newXfirst = energyCoefficient * speedXfirst;
	newYfirst = energyCoefficient * speedYfirst;
	newXsecond = energyCoefficient * speedXsecond;
	newYsecond = energyCoefficient * speedYsecond;

	}
	
	/**
	 * 
	 * @return new x axis speed for first continent
	 */
	public double getNewXSpeed1() {
		return newXfirst;
	}
	/**
	 * 
	 * @return new y axis speed for first continent
	 */
	public double getNewYSpeed1() {
		return newYfirst;
	}
	/**
	 * 
	 * @return new x  axis speed for second continent
	 */
	public double getNewXSpeed2() {
		return newXsecond;
	}
	/**
	 * 
	 * @return new y axis speed for second continent
	 */
	public double getNewYSpeed2() {
		return newYsecond;
	}
	
	/**
	 * 
	 * @return force involved for first continent
	 */
	public double getForce1() {
		return force1;
	}
	
	/**
	 * 
	 * @return force involved for second continent
	 */
	public double getForce2() {
		return force2;
	}
	
	/**
	 * 
	 * @return amount of force taken to make mountains, rather than reduce speed further.
	 */
	public double getMountainCoefficient() {
		return mountainCoefficient;
	}


}
