import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * this test case can test the physics of the module for minor collisions. However, since it is purely maths, and is very isolated, there is no point
 *  - the tests would simply repeat the calculations in that module and compare the obviously identical results. However, it shows that such modules can easily
 *  be tested independently, which is good for an extendable system.
 * @author 2354535k
 *
 */
public class CollisionGrazingTest {
	
	private CollisionGrazing testCollisionGrazing;
	
	public CollisionGrazingTest() {
	
	
		// a module is created with data from two hypotehical continents of given masses and speeds.
		
		// I've made variables to make it easy to change the circumstances of the collision.
		int massOfFirstContinent = 100;
		int massOfSecondContinent = 100;
		
		double xSpeedOfFirst = 5;
		double ySpeedOfFirst = 5;
		
		double xSpeedOfSecond = 0;
		double ySpeedOfSecond = 0;
				
		testCollisionGrazing = new CollisionGrazing(massOfFirstContinent, massOfSecondContinent, xSpeedOfFirst, ySpeedOfFirst, xSpeedOfSecond, ySpeedOfSecond);
	}
	
	@Before
	public void setUp() {
		
		
		
	}

	/**
	 * Tests that speed changes are applied correctly after the data goes in to collisionMechanics() method.
	 */
	@Test
	public void testGrazingCollisionSpeedChanges() {
		
		
		
	}
}
