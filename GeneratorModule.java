import java.util.Random;

public class GeneratorModule {
	
	private int size;
	private int[][] groupMap;
	
	public GeneratorModule(int size) {
		groupMap = new int[size][size];
		this.size = size;
	}
	
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

	public int[][] getMapping(){
		return groupMap;
	}

}
