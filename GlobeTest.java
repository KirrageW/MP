import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This test case is aimed mainly at breaking down the large and unwieldly move() method, in the Globe class.
 * Each unit test checks a specific aspect of the mechanics of this method. This was very useful during development 
 * because as the move() method grew larger, I could run this case to find out which aspects of it were failing, when problems
 * arose.
 * @author 2354535k
 *
 */
public class GlobeTest {
// PERFORMANCE TEST - make a large map, compare %surface covered with moving continent AND compare mountain generation switched on/off
	// prove that mountain generation is performance heavy, and a future work would be to find a more efficient and pleasing algorithm to 
	// make mountains.
	
	protected Square[][] squares;
	private Globe testGlobe;
	
	private Square testSquare;
	private Square testSquareForBoundary;
	
	private Square continentA;
	private Square continentB;

	
	
	private ArrayList<Square>[] continents;
	private int continentsCounter;

	
	public GlobeTest() {
		// make a smaller globe, which will be foundation of tests
				testGlobe = new Globe(100);	
				
				continents = new ArrayList[10];
				continentsCounter = 1;
				
	}

	
	
	@Before
	public void setUp() {
	
		
				// follow the basic pattern for continent creation, as in Globe.
				ArrayList<Square> cont = new ArrayList<Square>();
		
				testSquare = testGlobe.squares[0][0];
		
				testSquare.setGroup(5);
				testSquare.setHeight(50);	
				testSquare.setX(10);
				testSquare.setY(10);
				cont.add(testSquare);
				
				continents[continentsCounter] = cont;
				continentsCounter++;
			
				
				
				// for boundary test
				cont = new ArrayList<Square>();
				
				testSquareForBoundary = testGlobe.squares[0][1];
				
				testSquareForBoundary.setGroup(6);
				testSquareForBoundary.setHeight(50);	
				testSquareForBoundary.setX(99);
				testSquareForBoundary.setY(99);
				cont.add(testSquareForBoundary);
				
				continents[continentsCounter] = cont;
				continentsCounter++;
				
	
				// makes two large continents, 10x10 squares in size, near one another, separated by 10 squares on the x axis.
				testGlobe.generateSquareContinent(20, 20, 10); // will be group 1
				testGlobe.generateSquareContinent(40, 20, 10); // will be group 2
				
				/*System.err.println(testGlobe.getContinents()[1].get(0).getGroup());
				System.err.println(testGlobe.getContinents()[2].get(0).getGroup());
				System.err.println(testGlobe.getContinents()[2].size());*/

				// plot to data structures
				testGlobe.plotMaps();				
	}
	
	
	/**
	 * 
	 * This tests the large move() method in the Globe class. 
	 * A simple test in this case: are squares being moved correctly in respect to their given X and Y velocities?
	 * This also tests numerous helper methods such as getters for Square coordinates, and the setVelocity method of Globe.
	 */
	@Test
	public void testMoveWithoutCollision() {
		
		int originalXPosition = testSquare.getX();
		int originalYPosition = testSquare.getY();
		
		int velocityX = 5;
		int velocityY = 0;
		
		testGlobe.setVelocity(5,velocityX,velocityY);
		testGlobe.move();
		
		int correctXPosition = originalXPosition + velocityX;
		int correctYPosition = originalYPosition + velocityY;
		
		//assertEquals("Does the square's X coordinate correctly reflect velocity, after movement?", correctXPosition, testSquare.getX());
		assertEquals("Does the square's X coordinate correctly reflect velocity, after movement?", correctYPosition, testSquare.getY());
				
	}
	

	/**
	 * Tests checkBoundaries() method.
	 * Similar as above test, but this time square is placed on threshold of a corner, and advanced. this tests
	 * both x and y border transition, and proves the completion of the flat earth requirement.
	 */
	@Test
	public void testXBoundaries() {
		
		
		int velocityX = 1;
		int velocityY = 1;
		
		
		testGlobe.setVelocity(6,velocityX,velocityY);
		testGlobe.move();
		
		int correctXPosition = 1;
		int correctYPosition = 1;
		
		assertEquals("Does the square's X coordinate correctly reflect velocity, after movement?",correctXPosition, testSquareForBoundary.getX());
		
		
	}
	@Test
	public void testYBoundaries() {
		
		
		int velocityX = 1;
		int velocityY = 1;
		
		testGlobe.setVelocity(6,velocityX,velocityY);
		testGlobe.move();
		
		int correctXPosition = 1;
		int correctYPosition = 1;
		
		assertEquals("Does the square's X coordinate correctly reflect velocity, after movement?",correctYPosition, testSquareForBoundary.getY());
	
	}
	

	/**
	 * These tests check checkCollision() - whether two continents are being detected AND whether the maths/physics modules are 
	 * engaging with the continents, changing their speeds etc..
	 * It moves two squares into the same coordinate, and compares their speeds before and after.
	 * If the speeds remain unchanged, then collisions are no longer being detected.	
	 */
	@Test
	public void moveContinentsNoCollision() {
		testGlobe.setVelocity(1, 3,0); // move left continent 3 places to the right, each move
		
		// therefore, there should not be a collision after one or two move() calls. NOTE, checkBoundaries() checks positions
		// after a PROJECTED move. it checks position after the next move.
		
		testGlobe.move();
		
		assertFalse(testGlobe.checkCollision());
	}
	@Test
	public void moveContinentsWithCollision() {
		testGlobe.setVelocity(1, 3,0); // move left continent 3 places to the right, each move
		
		// therefore, there should be a collision in 3 move() calls, and not before that point
		testGlobe.move();
		testGlobe.move();	
		testGlobe.move();

		assertTrue(testGlobe.checkCollision());
	}
	
	/**
	 * These same tests can also check whether the maths modules, to change continent speed/direction, and mountain building, are
	 * engaging with the continents. Are these modules 'plugged in' correctly? These tests simply test basic changes, I leave the testing of the effects to a visual inspection
	 * of the results on the GUI map, as this is, especially in terms of the mountain formation, a faster way to see what is happening.
	 * For testing the specifics of the maths, this should be done on the specific modules that use the maths - this case is only for
	 * the functionality of the Globe class.
	 */
	@Test
	public void moveContinentsWithCollisionAndCheckSpeedChange() {
		
		testGlobe.setVelocity(1, 3,0); // move left continent 3 places to the right, each move
		
		int speedXInitial = testGlobe.getContinents()[1].get(0).getXVel();
		System.out.println(speedXInitial);
		// therefore, there should be a collision in 3 move() calls, and not before that point
		testGlobe.move();
		testGlobe.move();	
		testGlobe.move();
		testGlobe.move();
		
		// after collision
		int speedXFinal = testGlobe.getContinents()[1].get(0).getXVel();
		System.out.println(speedXFinal);
		
		assertFalse("speed should have changed", speedXInitial == speedXFinal);
	}
	
	/**
	 * similar to the above, but checks that height changes have occurred on BOTH the continents involved in the collision
	 */
	@Test
	public void moveContinentsWithCollisionAndCheckMountainFormation() {
		testGlobe.setVelocity(1, 3, 0);
		
		int averageBefore = 0;		
		for (Square each : testGlobe.getContinents()[1]) {
			averageBefore = averageBefore+each.getHeight();
		}
		averageBefore = averageBefore/testGlobe.getContinents()[1].size();
		
		testGlobe.move();
		testGlobe.move();	
		testGlobe.move();
		testGlobe.move();
		
		int averageAfter = 0;		
		for (Square each : testGlobe.getContinents()[1]) {
			averageAfter = averageAfter+each.getHeight();
		}
		averageAfter = averageAfter/testGlobe.getContinents()[1].size();
		
		assertFalse("average height of this continent should have changed", averageBefore == averageAfter);
	}
	
	/**
	 * This tests the current set up of collision modules is correctly working. In this situation, a minor collision is triggered between two continents
	 * ,i.e one that is below the proportion for a different sort of collision in the move() method. Note that this system of different collision mechanics is
	 * entirely optional - this proportion condition could be removed entirely and a single module to handle all collisions could be swapped in in its place.
	 * 
	 * The below test triggers the minor collision, and tests that the speed of an affected continent is changed correctly.
	 */
	@Test
	public void testGrazingCollisionSpeedChanges() {
		
		// has to be changed to reflect current coefficient value in collisionGrazing.
		double mountainCoefficient = 0.75;
		
		testGlobe.setVelocity(1, 5, 3);		
		
		int speedXBefore = testGlobe.getContinents()[1].get(0).getXVel();
	
		
		testGlobe.setVelocity(2, 0, 0);
		
		double expected = mountainCoefficient * testGlobe.getContinents()[1].get(0).getXVel();
			
		testGlobe.move();
		testGlobe.move();	
		testGlobe.move();
		

		int expectedRounded = (int) Math.round(expected);

		assertEquals("The speed should be reduced after this minor collision, in accordance with the current CollisionGrazing module", expectedRounded,  testGlobe.getContinents()[1].get(0).getXVel());	
	}
	
	/**
	 * This tests the current set up of collision modules is correctly working. In this situation, a MAJOR collision is triggered between two continents
	 * ,i.e one that is ABOVE the proportion for a different sort of collision in the move() method.
	 * 
	 * The below test triggers the major collision, and tests that the speed of an affected continent is changed differently to a minor collision.
	 */
	@Test
	public void testMajorCollisionSpeedChanges() {
		
		
		testGlobe.setVelocity(1, 5, 0);		
		testGlobe.setVelocity(2, 0, 0);
		
		testGlobe.move();
		testGlobe.move();	
			
		//make module compute independently
		int mass1 = testGlobe.getContinents()[1].size();
		int mass2 = testGlobe.getContinents()[2].size();
		
		double speedXfirst = testGlobe.getContinents()[1].get(0).getXVel();
		double speedYfirst = testGlobe.getContinents()[1].get(0).getYVel();
		double speedXsecond = testGlobe.getContinents()[2].get(0).getXVel();
		double speedYsecond = testGlobe.getContinents()[2].get(0).getYVel();
		
		CollisionMajor actual = new CollisionMajor(mass1, mass2, speedXfirst, speedYfirst,speedXsecond, speedYsecond);

		//get actual data up to same point
		testGlobe.move();	

		assertEquals("The speed should be the same. meaning the MajorCollision module was successfully activated and worked.", (int)Math.round(actual.getTotalX()), testGlobe.getContinents()[1].get(0).getXVel());	
	}
	
	
	

}
