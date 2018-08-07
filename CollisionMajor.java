
// this module can create supercontinents by making two continents move as one, after an inelastic collision.
/**
 * A component that calculates speed changes and forces during a major collision. In the current implentation a major collision is
 * defined by the number of Squares overlapping being above a certain proportion of the total Squares of involved continents.
 * In this component I use supercontinent mechanics, and both continents end up with the same speeds after collisions...
 * @author 2354535k
 *
 */
public class CollisionMajor {
	
	// masses of both continents
	private int mass1;
	private int mass2;
	
	// speeds of both continents in both axes
	private double speedXfirst;
	private double speedYfirst;
	private double speedXsecond;
	private double speedYsecond;
	
	// the new speeds of both continents - supercontinent formation
	private double totalX;
	private double totalY;
	
	
	private double force1;
	private double force2;
	
	/**
	 * Takes information from Globe and performs functions on this data.
	 * @param mass1 - of first continent
	 * @param mass2 = of second continent
	 * @param x1 - x axis speed first
	 * @param y1 - y axis speed first
	 * @param x2 - x axis speed second
	 * @param y2 - y axis speed second
	 */
	public CollisionMajor(int mass1, int mass2, double x1, double y1, double x2, double y2) {
		
		this.mass1 = mass1;
		this.mass2 = mass2;
		this.speedXfirst = x1;
		this.speedYfirst = y1;
		this.speedXsecond = x2;
		this.speedYsecond = y2;
		
		
		collisionMechanics();
		
	}
	
	/**
	 * In this component, compared with CollisionGrazing.java, total forces and resulting speeds are calculated, 
	 * as the intended effect was for the continents to be stuck pushing against each other, and end up moving as one, albeit
	 * with a reduced speed. 
	 */
	public void collisionMechanics() {
		
	double velocity1 = Math.sqrt((speedXfirst * speedXfirst) + (speedYfirst * speedYfirst));
	double velocity2 = Math.sqrt((speedXsecond * speedXsecond) + (speedYsecond * speedYsecond));
	
	
	double Ax = mass1 * speedXfirst;
	double Ay = mass1 * speedYfirst;
	double Bx = mass2 * speedXsecond;
	double By = mass2 * speedYsecond;

	// total mass
	totalX = Ax + Bx;
	totalY = Ay + By;

	totalX = totalX / (mass1 + mass2);
	totalY = totalY / (mass1 + mass2);

	// inelastic coefficient - what proportion of this energy is lost to mountain
	// formation?
	double mountainCoefficient = 0.5; // more mountain building, more speed loss

	totalX = totalX * mountainCoefficient;
	totalY = totalY * mountainCoefficient;

	// so supercontinent ID is assigned as the bigger supercontinent ( remains same
	// if already is bigger supercontinent)

	force1 = velocity1 * mass1;
	force2 = velocity2 * mass2;

	// mountain formation coefficient - proportion of force used in mountain
	// building - all energy makes mountains

	//force for mountain building
	force1 = force1 * mountainCoefficient;
	force2 = force2 * mountainCoefficient;
	}
	
	/**
	 * @return  total x axis speed
	 */
	public double getTotalX() {
		return totalX;		
	}
	
	/**
	 * 
	 * @return total y axis speed
	 */
	public double getTotalY() {
		return totalY;
	}
	
	/**
	 * 
	 * @return force of first continent
	 */
	public double getForce1() {
		return force1;
	}
	
	/**
	 * 
	 * @return force of second continent
	 */ 
	public double getForce2() {
		return force2;
	}
	

}
