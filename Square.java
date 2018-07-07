
// a square of continental land, many of which form a Continent
// essentially an XY coordinate and a height

public class Square {
	
	private int planetSize;
	
	private int x;
	private int y;
	private int height;
	private int size;
	
	public Square(int x, int y, int height, int size, int planetSize, String continent) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.size = size;
		this.planetSize = planetSize;
		
	}

	public int getXCoord() {
		return x;
	}

	public int getYCoord() {
		return y;
	}

	public int getHeight() {
		return height;
	}

	public int getSize() {
		return size;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

}
