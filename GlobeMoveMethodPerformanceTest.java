import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * In this performance test, the move() method can be timed. You can change the size of the surface, and the size/number
 * of the continents too. In this way, you can see what effect the relative amounts of moving continent has on a surface, and
 * the relative number of squares that are involved in collisions.
 * You can also see the effect that certain components have on the move() performance, such as the getNeighbours() method for
 * mountain building, that seems to decrease performance quite markedly.
 * 
 * This test allows the effect of adding/removing modules and components to be quickly assessed in terms of their impact on the
 * main processing of the model - move() in Globe class.
 * 
 * @author 2354535k
 *
 */
public class GlobeMoveMethodPerformanceTest {
	
	private Globe testAll;
	
	private int size;
	
	public GlobeMoveMethodPerformanceTest() {
		size = 250;
		testAll = new Globe(size);	
	}

	@Before
	public void setUp() {	
		double sizeForTestDouble = (size*0.25)-1; //minus 1 to keep below array bounds
		System.out.println("double size" + sizeForTestDouble);
		int sizeForTest = (int)Math.round(sizeForTestDouble);
		System.out.println("int size" + sizeForTest);
		testAll.generateSquareContinent(1, 1, (sizeForTest));	
		
		//testAll.generateSquareContinent(size/2, size/2, (size/2)-5);	
		
		System.out.println(testAll.getContinents()[1].size());
	}
	
	/**
	 * Compare when removed calls to getNeighbours(). Drastic improvements.
	 */
	@Test//(timeout = 750)
	public void test() {
		
		testAll.setVelocity(1, 5, 5);

		long start = System.currentTimeMillis();
		testAll.move();
		long end = System.currentTimeMillis();	
		
		System.out.println(end-start);	
		System.out.println("oeifewij");
	}
	
	/*RESULTS
	 * getNeighbors(2) - no collisions, edge formation only.
	 * 62001 - 1,1,size = 52247 
	 * 1,1,size*0.90 = 34598
	 * 1,1,size*0.80 = 22015
	 * 34969 - 1,1,size*0.75 = 16538 
	 * 15376 - 1,1,size*0.50 = 3831 
	 * 3844 - 1,1,size * 0.25 = 483
	 * 
	 * getNeighbors(1) - no collisions
	 * 1,1,size = 7620
	 * 1,1,size*0.75 = 2280
	 * 1,1,size*0.50 = 500
	 * 1,1,size*0.25 = 84
	 * 
	 * getNeighbours removed - no collisions 
	 * 1,1,size = 74
	 * 1,1,size*0.75 = 52
	 * 1,1,size*0.50 = 50
	 * 1,1,size*0.25 = 52
	 * 
	 * 
	 */

}
