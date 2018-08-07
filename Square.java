/**
 * The data structure for a single grid square. Many of these Squares make up continents. 
 * @author 2354535k
 *
 */
public class Square {
	
	/**
	 * Coordinate on x axis
	 */
	private double x; 
	/**
	 * Coordinate on y axis
	 */
	private double y; 
	/**
	 * x axis speed.
	 */
	private double xVel;
	/**
	 * y axis speed
	 */
	private double yVel;
	/**
	 * represents height. intended range at the moment is between 0 and 255 because
	 * it easily translates to a color for visual mapping
	 */
	private int height;
	/**
	 * represents which continent the Square is part of
	 */
	private int group;
	/**
	 * represents which super continent the Square is part of
	 */
	private int superGroup;

	/**
	 * used in mountain generation component. this is bad coupling but the algorithm that needs it is inefficient and should be replaced
	 * anyway.
	 */
	private boolean checked;
		
	/**
	 * Constructor. 
	 * @param height. a height of -1 represents sea. 
	 */
	public Square(int height) {
		this.height = height;	
		this.superGroup = -1; // represents isolated continent
		checked = false;
	}
	
	/**
	 * Sets the checked boolean value. It is used for propagating height changes in the current mountain former.
	 * @param x
	 */
	public void setChecked(boolean x) {
		if (x == true) {
			checked = true;
		}
		else
			checked = false;
	}
	/**
	 * Getter for boolean checked. See setChecked().
	 * @return
	 */
	public boolean checked() {
		return checked;
	}
	

	/**
	 * Getter for x location. Makes sure to round to integer because the heightMap, groupMap etc are all int based.
	 * @return
	 */
	public int getX() {
		return (int) Math.round(x);
	}

	/**
	 * Getter for y location. Makes sure to round to integer because the heightMap, groupMap etc are all int based.
	 * @return
	 */
	public int getY() {
		return (int) Math.round(y);
	}

	/**
	 * setter for x location
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * setter for y location
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	// VELOCITIES
	/**
	 * setter for x axis speed
	 * @param x double representing speed
	 */
	public void setXVel(double x) {
		this.xVel = x;
	}
	/**
	 * setter for y axis speed
	 * @param y double representing speed
	 */
	public void setYVel(double y) {
		this.yVel = y;
	}
	/**
	 * 
	 * @return returns x speed as int
	 */
	public int getXVel() {
		return (int) Math.round(xVel);
	}
	/**
	 * 
	 * @return returns y speed as int
	 */
	public int getYVel() {
		return (int) Math.round(yVel);
	}
	
	//HEIGHT
	/**
	 * returns height
	 * @return int height
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * setter for height. makes sure height cannot be set above 250, for current coloring.
	 * @param h
	 */
	public void setHeight(int h) {
		this.height = h;
		if (height > 250) {
			this.height = 250;
		}
	}
	
	
	/**
	 * setter for group that represents continent ID. In this way, it is possible to transer plate to another continent 
	 * during collision. This could represent plate destruction and construction, or subduction.
	 * @param g
	 */
	public void setGroup(int g) {
		this.group = g;
	}
	
	/**
	 * 
	 * @return continent ID
	 */
	public int getGroup() {
		return group;
	}
	
	//SUPERGROUP
	/**
	 * setter for supercontinent mechanic
	 * @param sG ID of supercontinent
	 */
	public void setSuperGroup(int sG) {
		this.superGroup = sG;
	}
	
	/**
	 * getter for super continent ID
	 * @return
	 */
	public int getSuperGroup() {
		return this.superGroup;
	}
}
