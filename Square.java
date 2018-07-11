public class Square {
	
	private double x;
	private double y;
	private double xVel;
	private double yVel;
	private int height;
	private int group;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private int generation;
		
	public Square(int height) {
		this.height = height;		
	}
	
	public Square(int height, int generation) {
		this.height = height;	
		this.generation = generation;
	}
	
	public Square(int x, int y, int height) {
		this.x = x;
		this.y = y;
		this.height = height;
	}
	

	// generation (for initial set up)
	public int getGeneration() {
		return this.generation;
	}
	
	// COORDINATES
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	// VELOCITIES
	public void setXVel(double x) {
		this.xVel = x;
	}
	
	public void setYVel(double y) {
		this.yVel = y;
	}
	
	public double getXVel() {
		return xVel;
	}
	
	public double getYVel() {
		return yVel;
	}
	
	//HEIGHT
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int h) {
		this.height = h;
	}
	
	//GROUP
	public void setGroup(int g) {
		this.group = g;
	}
	
	public int getGroup() {
		return group;
	}

}
