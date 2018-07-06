
// just pure info about the land

public class Continent extends Thread {

	private int planetSize;
	private int x;
	private int y;
	private int height;
	private int size;
	private int speedX, speedY;
	
	//bounds
	private int topLeft, topRight, bottomLeft, bottomRightX, bottomRightY;

	public Continent(int x, int y, int height, int size, int planetSize) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.size = size;
		this.planetSize = planetSize;
		setBounds();
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
	
	public int getSpeedX() {
		return speedX;
	}
	
	public int getSpeedY() {
		return speedY;
	}

	public void setBounds() {
		topLeft = x;
		topRight = x+size;
		bottomLeft = y+size;
		bottomRightX = x+size;
		bottomRightY = y+size;
	}
	
	
	
	public void run()  {

		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			x = x + speedX;
			y = y + speedY;

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
			
			setBounds();
			

		}
	}
	
	// detect if one continent is in the bounds of another
	public boolean checkCollision(Continent other) {	
			
		if ((this.x >= other.x && this.x <= other.x+size) && (this.y >= other.y && this.y <= other.y+size)) {
			
			return true;
		}
		return false;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
