import java.util.ArrayList;

public class Continent {
	
	private ArrayList<Square> children;
	private int speedX;
	private int speedY;
	private int id;
	
	private int superContinent;
	
	public Continent(int id) {
		this.id = id;
		children = new ArrayList<Square>();
	}
	
	public int getNumber() {
		return children.size();
	}
	
	public void setSpeed(int x, int y) {
		this.speedX = x;
		this.speedY = y;
	}
	
	public int getSpeedX() {
		return speedX;
	}
	
	public int getSpeedY() {	
		return speedY;
	}
	
	public void setSpeeds() {
		for (int i = 0; i < children.size(); i++) {
			children.get(i).setXVel(speedX);
			children.get(i).setYVel(speedY);
		}
	}
	
	public int getID() {
		return id;
	}
	
	public void addSquare(Square a) {
		children.add(a);
	}
	
	public ArrayList<Square> getChildren(){
		return children;
	}
	
	public void setSuperContinent(int x) {
		this.superContinent = x;
	}
	
	public int getSuperContinent() {
		return this.superContinent;
	}
	

	
	
}