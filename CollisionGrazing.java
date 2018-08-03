
public class CollisionGrazing {
	
	private int mass1;
	private int mass2;
	
	private double speedXfirst;
	private double speedYfirst;
	private double speedXsecond;
	private double speedYsecond;
	
	private double newXfirst;
	private double newYfirst;
	private double newXsecond;
	private double newYsecond;
	
	private double force1;
	private double force2;
	
	private double mountainCoefficient;
	
	public CollisionGrazing(int mass1, int mass2, double x1, double y1, double x2, double y2) {
		
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

	System.out.println("done maths in minor");
	}
	
	public double getNewXSpeed1() {
		return newXfirst;
	}
	public double getNewYSpeed1() {
		return newYfirst;
	}
	public double getNewXSpeed2() {
		return newXsecond;
	}
	public double getNewYSpeed2() {
		return newYsecond;
	}
	
	public double getForce1() {
		return force1;
	}
	
	public double getForce2() {
		return force2;
	}
	
	public double getMountainCoefficient() {
		return mountainCoefficient;
	}


}
