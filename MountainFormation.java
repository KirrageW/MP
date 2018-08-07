import java.util.ArrayList;
/**
 * Mountain formation component.
 * @author 2354535k
 *
 */
public class MountainFormation {
	
	private double force1; // force of one continent's movement
	private double force2; // force of second continent's movement
	private int group; 
	private ArrayList<Square> squares;
	private ArrayList<Square> continent;
	private boolean edge;
	/**
	 * Constructor that needs some basic information from the model, to then compute results.
	 * @param force1 - the force that will represent the scale of mountain formation in the first continent
	 * @param force2 - of the second continent
	 * @param group 
	 * @param squares - the squares that are involved in the collision - those that will be immediately deformed.
	 * @param continent - all the squares that are in the continent that is collided.
	 * @param edge - a boolean switch, are we just making mountains on the leading edge of a continent?
	 */
	public MountainFormation(double force1, double force2, int group, ArrayList<Square> squares, ArrayList<Square> continent, boolean edge) {	
		this.force1 = force1;
		this.force2 = force2;
		this.group = group;
		this.squares = squares;	
		this.continent = continent;
		this.edge = edge;
			
		for (Square a : continent) {
		a.setChecked(false);
	}
		
		if (edge == false) {
			makeMountains();
		}
		else
			makeMountainsEdge();
	}
	
	/**
	 * A secondary constructor where a whole continent is not needed. Used for edge mountain formation.
	 * @param force1
	 * @param force2
	 * @param group
	 * @param squares
	 * @param edge
	 */
	public MountainFormation(double force1, double force2, int group, ArrayList<Square> squares, boolean edge) {	
		this.force1 = force1;
		this.force2 = force2;
		this.group = group;
		this.squares = squares;	
		continent = squares;
		this.edge = edge;
		
		
		if (edge == false) {
			makeMountains();
		}
		else
			makeMountainsEdge();
	}
	
	
	/**
	 * First the force is narrowed to be within a certain range scale, so calculations do not have to worrk about boundary issues 
	 * and forces can reflect a certain scale of mountain formation. 
	 * 
	 */
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

		// apply height changes to affected squares and near neighbours. For each group.
		for (Square v : squares) {
				getNeighbours(4, v, (int) Math.round(force1));
		}

	}

	/**
	 * Edge mountain formation version. Fewer iterations of getNeighbours(), mainly for processing speed reasons (see report)!
	 */
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

	/**
	 * Sets the height of affected Square, and its neighbours also. Height changes diminish from the original square 
	 * to attempt a gradient effect.
	 * Uses the checked boolean in a Square, so that as it iterates through neigbours, Squares already affected aren't written over
	 * with a new height.
	 * @param times - the number of times the mountain formation effects neighbours farther away (length of gradient)
	 * @param x - the originating Square
	 * @param force - the original force.
	 */
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

		
	}
	
	/**
	 * Returns a fully deformed set of Squares, reflecting their new heights. 
	 * @return
	 */
	public ArrayList<Square> getDeformedContinent(){
		return continent;
	}

}
