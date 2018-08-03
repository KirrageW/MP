
// this module can create supercontinents by making two continents move as one, after an inelastic collision.
public class CollisionMajor {
	
	private int mass1;
	private int mass2;
	
	private double speedXfirst;
	private double speedYfirst;
	private double speedXsecond;
	private double speedYsecond;
	
	private double totalX;
	private double totalY;
	
	
	private double force1;
	private double force2;
	
	public CollisionMajor(int mass1, int mass2, double x1, double y1, double x2, double y2) {
		
		this.mass1 = mass1;
		this.mass2 = mass2;
		this.speedXfirst = x1;
		this.speedYfirst = y1;
		this.speedXsecond = x2;
		this.speedYsecond = y2;
		
		
		collisionMechanics();
		
	}
	
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
	
	System.out.println("done maths in major");
	
	}
	
	public double getTotalX() {
		return totalX;		
	}
	
	public double getTotalY() {
		return totalY;
	}
	
	public double getForce1() {
		return force1;
	}
	
	public double getForce2() {
		return force2;
	}
	

}
