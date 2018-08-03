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
		testAll.generateSquareContinent(1, 1, (size/2)-5);	
		testAll.generateSquareContinent(size/2, size/2, (size/2)-5);	
	}
	
	/**
	 * Compare when removed calls to getNeighbours(). Drastic improvements.
	 */
	@Test//(timeout = 750)
	public void test() {
		testAll.setVelocity(1, 5, 5);
		//testAll.setVelocity(2, -5, -5);
		long start = System.currentTimeMillis();
		testAll.move();
		System.err.println(testAll.getNumCollisions());
		long end = System.currentTimeMillis();	
		System.out.println(end-start);	
	}

}
