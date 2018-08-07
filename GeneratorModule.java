import java.util.Random;
/**
 * Component that generates randomly shaped continents. I struggled with this greatly and expect this is not a brilliant way to
 * go about island generation. Continents have a frustrating straight line down the right end, and are often broken into lots of 
 * smaller continents, which isn't a problem but should be optional. 
 * @author 2354535k
 *
 */
public class GeneratorModule {

	/**
	 * Size of canvas - should be same as Globe size.
	 */
	private int size;
	/**
	 * The group map that proto continents are mapped onto in this component, and returned to Globe for plotting to 
	 * actual continents.
	 */
	private int[][] groupMap;

	/**
	 * Constructor that takes in the size of the canvas
	 * @param size
	 */
	public GeneratorModule(int size) {
		groupMap = new int[size][size];
		this.size = size;
	}

	/**
	 * The method works by plotting random numbers between 0 and 4, and averaging them out by the values of their neighbours.
	 * Then the other methods can be used in various combinations to delete land where the number of neighbours falls below a 
	 * threshold, or to make land if the abundance of neighbors is high enough. 
	 * 
	 * @param x - top left of proposed continent
	 * @param y - top left of proposed continent
	 * @param size - x axis max width
	 * @param sizeY - y axis max height
	 * @param group 
	 */
	public void newNumbers(int x, int y, int size, int sizeY, int group) {

		Random ran = new Random();

		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				groupMap[i][j] = 0;
			}
		}

		for (int i = x + 5; i < x + size - 5; i++) {
			for (int j = y + 5; j < y + sizeY - 5; j++) {
				int number = ran.nextInt(4);
				groupMap[i][j] = number;
			}
		}

		for (int i = x + 5; i < x + size; i++) {
			for (int j = y + 5; j < y + sizeY; j++) {
				groupMap[i][j] = getNeighbours(i, j);
			}
		}

		// you need more or fewer of these, depending on size of grid.
		closer(1);
		closer(1);
		closer(2);
		closer(2);
		focus();
		closer(2);
		focus();
		focus();
		focus();
		closer(1);

	}

	/**
	 * Calculates the average value of a gridsquare, at a radius of three squares.
	 * @param i
	 * @param j
	 * @return - average value of location
	 */
	public int getNeighbours(int i, int j) {

		int a = groupMap[i - 1][j - 1];
		int b = groupMap[i][j - 1];
		int c = groupMap[i + 1][j - 1];
		int d = groupMap[i + 1][j];
		int e = groupMap[i + 1][j + 1];
		int f = groupMap[i][j + 1];
		int g = groupMap[i - 1][j + 1];
		int h = groupMap[i - 1][j];

		int k = groupMap[i - 2][j - 2];
		int l = groupMap[i][j - 2];
		int m = groupMap[i + 2][j - 2];
		int n = groupMap[i + 2][j];
		int o = groupMap[i + 2][j + 2];
		int p = groupMap[i][j + 2];
		int q = groupMap[i - 2][j + 2];
		int r = groupMap[i - 2][j];

		int s = groupMap[i - 3][j - 3];
		int t = groupMap[i][j - 3];
		int u = groupMap[i + 3][j - 3];
		int v = groupMap[i + 3][j];
		int w = groupMap[i + 3][j + 3];
		int x = groupMap[i][j + 3];
		int y = groupMap[i - 3][j + 3];
		int z = groupMap[i - 3][j];

		return (a + b + c + d + e + f + g + h + k + l + m + n + o + p + q + r + s + t + u + v + w + x + y + z) / 24;

	}

	// degree param represents immediate neighbours (1) or next neighbours (2)
	/**
	 * Deletes land if neighbours count falls below a threshold
	 * @param degree - the distance of neighbours that are being counted. i.e, 1 represents immediate neighbours.
	 */
	public void closer(int degree) {

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int numberOfNeighbours = 0;
				int req = 3;
				if (groupMap[i][j] > 0) {

					if (groupMap[i - degree][j - degree] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i][j - degree] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i + degree][j - degree] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i + degree][j] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i + degree][j + degree] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i][j + degree] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i - degree][j + degree] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i - degree][j] > 0) {
						numberOfNeighbours++;
					}

					// whittle
					if (numberOfNeighbours <= req) {
						groupMap[i][j] = 0;

					}
				}

			}
		}

	}

	// gets rid of lakes
	/**
	 * Works like closer() but in reverse, to delete ocean lakes and replace them with land.
	 */
	public void focus() {
		for (int i = 1; i < size - 1; i++) {
			for (int j = 1; j < size - 1; j++) {
				int numberOfNeighbours = 0;
				int req = 3;
				if (groupMap[i][j] == 0) {
					if (groupMap[i - 1][j - 1] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i][j - 1] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i + 1][j - 1] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i + 1][j] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i + 1][j + 1] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i][j + 1] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i - 1][j + 1] > 0) {
						numberOfNeighbours++;
					}
					if (groupMap[i - 1][j] > 0) {
						numberOfNeighbours++;
					}

					if (numberOfNeighbours > req) {
						groupMap[i][j] = 1;

					}
				}
			}
		}

	}

	/**
	 * Finally returns the map of continent location to Globe, so that the continent is stored in the correct structures (see Globe).
	 * This is just one proposed mechanism of continent creation, I'm sure there are lots. But the ability to handle a 2d array of integers
	 * seems to be a logical way to create and store continent location.
	 * @return
	 */
	public int[][] getMapping() {
		return groupMap;
	}

}
