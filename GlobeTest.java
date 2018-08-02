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
	public void singleContinent() {
				// make a single 4 square continent
		
				// follow the basic pattern for continent creation, as in Globe.
				ArrayList<Square> cont = new ArrayList<Square>();
		
				testSquare = testGlobe.squares[0][0];
		
				testSquare.setGroup(1);
				testSquare.setHeight(50);	
				testSquare.setX(10);
				testSquare.setY(10);
				cont.add(testSquare);
				
				continents[continentsCounter] = cont;
				continentsCounter++;
				System.out.println(continentsCounter);
				
				// for boundary test
				cont = new ArrayList<Square>();
				
				testSquareForBoundary = testGlobe.squares[0][1];
				
				testSquareForBoundary.setGroup(2);
				testSquareForBoundary.setHeight(50);	
				testSquareForBoundary.setX(99);
				testSquareForBoundary.setY(99);
				cont.add(testSquareForBoundary);
				
				continents[continentsCounter] = cont;
				continentsCounter++;
				System.out.println(continentsCounter);

				/*
				testGlobe.squares[0][1].setHeight(50);
				testGlobe.squares[0][1].setX(10);
				testGlobe.squares[0][1].setY(11);
				
				testGlobe.squares[0][2].setGroup(1);
				testGlobe.squares[0][2].setHeight(50);
				testGlobe.squares[0][2].setX(11);
				testGlobe.squares[0][2].setY(10);
				
				testGlobe.squares[0][3].setGroup(1);
				testGlobe.squares[0][3].setHeight(50);
				testGlobe.squares[0][3].setX(11);
				testGlobe.squares[0][3].setY(11);*/
				
				continentA = testGlobe.squares[0][2];
				continentB = testGlobe.squares[0][3];
				
				cont = new ArrayList<Square>();
				continentA.setGroup(3);
				continentA.setHeight(50);
				continentA.setX(49);
				continentA.setX(49);
				cont.add(continentA);
				continents[continentsCounter] = cont;
				continentsCounter++;
				System.out.println(continentsCounter);

				cont = new ArrayList<Square>();
				continentB.setGroup(4);
				continentB.setHeight(50);
				continentB.setX(51);
				continentB.setX(51);
				cont.add(continentB);
				continents[continentsCounter] = cont;
				continentsCounter++;
				System.out.println(continentsCounter);

				testGlobe.plotMaps();				
	}
	
	
	/**
	 * This tests the large move() method in the Globe class. 
	 * A simple test in this case: are squares being moved correctly in respect to their given X and Y velocities?
	 * This also tests numerous helper methods such as getters for Square coordinates, and the setVelocity method of Globe.
	 */
	@Test
	public void testMoveWithoutCollision() {
		
		int originalXPosition = testSquare.getX();
		int originalYPosition = testSquare.getY();
		
		int velocityX = 1;
		int velocityY = 0;
		
		testGlobe.setVelocity(1,velocityX,velocityY);
		testGlobe.move();
		
		int correctXPosition = originalXPosition + velocityX;
		int correctYPosition = originalYPosition + velocityY;
		
		//assertEquals("Does the square's X coordinate correctly reflect velocity, after movement?", correctXPosition, testSquare.getX());
		assertEquals("Does the square's X coordinate correctly reflect velocity, after movement?", correctYPosition, testSquare.getY());
				
	}
	

	/**
	 * Similar as above test, but this time square is placed on threshold of a corner, and advanced. this tests
	 * both x and y border transition, and proves the completion of the flat earth requirement.
	 */
	@Test
	public void testXBoundaries() {
		
		
		int velocityX = 1;
		int velocityY = 1;
		
		testGlobe.setVelocity(2,velocityX,velocityY);
		testGlobe.move();
		
		int correctXPosition = 1;
		int correctYPosition = 1;
		
		assertEquals("Does the square's X coordinate correctly reflect velocity, after movement?",correctXPosition, testSquareForBoundary.getX());
		
		
	}
	@Test
	public void testYBoundaries() {
		
		
		int velocityX = 1;
		int velocityY = 1;
		
		testGlobe.setVelocity(2,velocityX,velocityY);
		testGlobe.move();
		
		int correctXPosition = 1;
		int correctYPosition = 1;
		
		assertEquals("Does the square's X coordinate correctly reflect velocity, after movement?",correctYPosition, testSquareForBoundary.getY());
		
		
	}
	
	/**
	 * This test will see whether collision between two continents are being detected.
	 * It moves two squares into the same coordinate, and compares their speeds before and after.
	 * If the speeds remain unchanged, then collisions are no longer being detected.
	 */
	@Test
	public void testCollisionDetection() {
		
		
		
		testGlobe.setVelocity(3, 1, 1);
		testGlobe.setVelocity(4, -1,-1);
		testGlobe.move();
		
		int[] speedExpected = {0,0,0,0,};
		
		int[] speedActual = {continentA.getXVel(), continentA.getYVel(), continentB.getXVel(), continentB.getYVel()};
		
		assertEquals("Speeds of the continents should have been reduced to zero, signalling that collision was detected", speedExpected, speedActual);
		
		
	}

}
