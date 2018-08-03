import java.util.ArrayList;

public class MountainFormation {
	
	private double force1;
	private double force2;
	private int group;
	private ArrayList<Square> squares;
	private ArrayList<Square> continent;
	private boolean edge;
	
	public MountainFormation(double force1, double force2, int group, ArrayList<Square> squares, ArrayList<Square> continent, boolean edge) {	
		this.force1 = force1;
		this.force2 = force2;
		this.group = group;
		this.squares = squares;	
		this.continent = continent;
		this.edge = edge;
		
		System.out.println("MAKE MAIOPNTUEPANB");
		
		if (edge == false) {
			makeMountains();
		}
		else
			makeMountainsEdge();
	}
	
	public MountainFormation(double force1, double force2, int group, ArrayList<Square> squares, boolean edge) {	
		this.force1 = force1;
		this.force2 = force2;
		this.group = group;
		this.squares = squares;	
		continent = squares;
		this.edge = edge;
		
		System.out.println("MAKE MAIOPNTUEPANB");
		
		if (edge == false) {
			makeMountains();
		}
		else
			makeMountainsEdge();
	}
	
	
	
	public void makeMountains() {
		if (force1 > 300) {
			force1 = 500;
		}
		if (force2 > 300) {
			force2 = 500;
		}

		if (force1 < 20) {
			force1 = 20;
		}
		if (force2 < 20) {
			force2 = 20;
		}

		// convert force to rating between 0 and 250 - for height map boundaries.
		double oldRange = (500 - 20);
		double newRange = (250 - 30);

		force1 = ((force1 - 20) / oldRange) * newRange + 30;
		force2 = ((force2 - 20) / oldRange) * newRange + 30;

		// apply height changes to affected squares and near neighbours
		for (Square v : squares) {
				getNeighbours(4, v, (int) Math.round(force1));
		}

	}

	public void makeMountainsEdge() {
		if (force1 > 300) {
			force1 = 500;
		}
		if (force2 > 300) {
			force2 = 500;
		}

		if (force1 < 20) {
			force1 = 20;
		}
		if (force2 < 20) {
			force2 = 20;
		}

		// convert force to rating between 0 and 250 - for height map boundaries.
		double oldRange = (500 - 20);
		double newRange = (250 - 30);

		force1 = ((force1 - 20) / oldRange) * newRange + 30;
		force2 = ((force2 - 20) / oldRange) * newRange + 30;

		// apply height changes to affected squares and near neighbours
		for (Square v : squares) {

			getNeighbours(2, v, (int) Math.round(force1));
		}

	}

	public void getNeighbours(int times, Square x, int force) {

		// get group of square involved
		int g = x.getGroup();
		for (int i = 1; i < times; i++) {
			for (Square a : continent) {

				if (force < 5) {
					return;
				}

				// need to check them off, so they are not overwritten

				if (a.getX() == x.getX() && a.getY() == x.getY() && a.checked() == false) {
					a.setHeight(a.getHeight() + force / i);
					a.setChecked(true);
				}

				if (a.getX() == x.getX() + i && a.getY() == x.getY() + i && a.checked() == false) {
					a.setHeight(a.getHeight() + force / i);
					a.setChecked(true);
				}

				if (a.getX() == x.getX() - i && a.getY() == x.getY() - i && a.checked() == false) {
					a.setHeight(a.getHeight() + force / i);
					a.setChecked(true);
				}

				if (a.getX() == x.getX() && a.getY() == x.getY() + i && a.checked() == false) {
					a.setHeight(a.getHeight() + force / i);
					a.setChecked(true);
				}

				if (a.getX() == x.getX() + i && a.getY() == x.getY() && a.checked() == false) {
					a.setHeight(a.getHeight() + force / i);
					a.setChecked(true);
				}
				if (a.getX() == x.getX() - i && a.getY() == x.getY() && a.checked() == false) {
					a.setHeight(a.getHeight() + force / i);
					a.setChecked(true);
				}

				if (a.getX() == x.getX() && a.getY() == x.getY() - i && a.checked() == false) {
					a.setHeight(a.getHeight() + force / i);
					a.setChecked(true);
				}
				if (a.getX() == x.getX() - i && a.getY() == x.getY() + i && a.checked() == false) {
					a.setHeight(a.getHeight() + force / i);
					a.setChecked(true);
				}

				if (a.getX() == x.getX() + i && a.getY() == x.getY() - i && a.checked() == false) {
					a.setHeight(a.getHeight() + force / i);
					a.setChecked(true);
				}

			}
		}

		for (Square a : continent) {
			a.setChecked(false);
		}
	}
	
	public ArrayList<Square> getDeformedContinent(){
		return continent;
	}

}
