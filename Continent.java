
// just pure info about the land

public class Continent {
	
	private int planetSize;
	private int x;
	private int y;
	private int height;
	private int size;
	private int speedX, speedY;
	

	public Continent(int x, int y, int height, int size, int planetSize) {
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
	
	public void setSpeeds(int x, int y) {
		this.speedX = x;
		this.speedY = y;
	}
	
	public void move() {
		x = x+speedX;
		y = y+speedY;
		
		if (x > planetSize) {
			x = 0;
		}
		
		if (x < 0) {
			x = planetSize;
		}
		
		if (y > planetSize) {
			y = 0;
		}
		
		if (y < 0) {
			y = planetSize;
		}		
	}
}


