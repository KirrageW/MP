import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

// 2d array 
public class Continent {

	// size of square grid
	private int n;
	
	// generation level (for initial set up)
	private int gen;
	
	// an array collecting all squares in a single continent
	private ArrayList<Square> testCon;
	
	

	private Square[][] grid;

	public Continent(int n) {
		this.n = n;
		// initialise 2d array
		grid = new Square[n][n];
		
		setSea();

		testCon = new ArrayList<Square>();
		makeTestLand();
		
		
	
	
	
	}

	// initialise all squares to sea
	public void setSea() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				grid[i][j] = new Square(-1);
			}
		}
	}

	public void makeTestLand() {
		for (int i = 100; i < 200; i++) {
			for (int j = 100; j < 300; j++) {
				grid[i][j] = new Square(200);
				grid[i][j].setGroup(1);
				testCon.add(grid[i][j]);
				
				grid[i][j].setX(i);
				grid[i][j].setY(j);
				
				grid[i][j].setXVel(1.0);
				grid[i][j].setYVel(0);
				
				
				
				
			}
		}
	}
	
	public void move() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				double xPos = grid[i][j].getX();
				double yPos = grid[i][j].getY();
				double xVel = grid[i][j].getXVel();
				double yVel = grid[i][j].getYVel();
				
				double newXPos = xPos+xVel;
				double newYPos = yPos+yVel;
				
				grid[i][j].setX(newXPos);
				grid[i][j].setY(newYPos);
				
				
			}
				
			}
		
		
		
	}
	
	
	
	
	
	
	
	// doesn't work
	public void generateLand(int x) {
		Random r = new Random();
		int min = 1;
		int max = n - 2;
		for (int i = 0; i < n * 20; i++) {
			int Xcoord = min + r.nextInt(max);
			int Ycoord = min + r.nextInt(max);
			if (grid[Xcoord][Ycoord].getHeight() < 0) {
				grid[Xcoord][Ycoord] = new Square(200, x);
			}

		}
		this.gen++;
	}

	// doesn't work
	// find number of neighbours - int parameter sets required number of neighbours - more lax to start
	public void getNeighbours(int req) { 
		for (int i = 1; i < n-1; i++) {
			for (int j = 1; j < n-1; j++) {

				if (grid[i][j].getHeight() > 0 && grid[i][j].getGeneration() != this.gen) {
					int numberOfNeighbours = 0;

					// a branch to check for squares at the edge of the bounds - they must account
					// for neighbours on the other side
					// haven't done corners yet - maybe just ignore for now, and only build islands in the safe areas away from boundaries

					// left edge middle
					if (i == 0 && j > 0 && j < n) {

						if (grid[n][j - 1].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i][j - 1].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i + 1][j - 1].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i + 1][j].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i + 1][j + 1].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i][j + 1].getHeight() > 0) {
							numberOfNeighbours++;
						}									
						if (grid[n][j + 1].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[n][j].getHeight() > 0) {
							numberOfNeighbours++;
						}
						
						// whittle
						if (numberOfNeighbours <= req) {
							grid[i][j] = new Square(-1);
						}
						
					}
					//right edge middle
					else if (i == n-1 && j > 0 && j < n) {
						if (grid[i - 1][j - 1].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i][j - 1].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[0][j - 1].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[0][j].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[0][j+1].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i][j + 1].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i - 1][j + 1].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i - 1][j].getHeight() > 0) {
							numberOfNeighbours++;
						}

						// whittle
						if (numberOfNeighbours <= req) {
							grid[i][j] = new Square(-1);
						}
						
						
					}
					// top middle
					else if (i > 0 && i < n-1 && j == 0) {
						if (grid[i - 1][n-1].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i][n-1].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i+1][n-1].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i + 1][j].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i + 1][j + 1].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i][j + 1].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i - 1][j + 1].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i - 1][j].getHeight() > 0) {
							numberOfNeighbours++;
						}

						// whittle
						if (numberOfNeighbours <= req) {
							grid[i][j] = new Square(-1);
						}
						
					}
					// bottom middle
					else if (i > 0 && i < n-1 && j == n-1) {
						if (grid[i - 1][j - 1].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i][j - 1].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i + 1][j - 1].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i + 1][j].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i + 1][0].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i][0].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i-1][0].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i - 1][j].getHeight() > 0) {
							numberOfNeighbours++;
						}

						// whittle
						if (numberOfNeighbours <= req) {
							grid[i][j] = new Square(-1);
						}
						
						
					}
					// top left corner
					
					
					
					
					
					
					// anywhere else
					else {
						
						if (grid[i - 1][j - 1].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i][j - 1].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i + 1][j - 1].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i + 1][j].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i + 1][j + 1].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i][j + 1].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i - 1][j + 1].getHeight() > 0) {
							numberOfNeighbours++;
						}
						if (grid[i - 1][j].getHeight() > 0) {
							numberOfNeighbours++;
						}

						// whittle
						if (numberOfNeighbours <= req) {
							grid[i][j] = new Square(-1);
						}
					}

				}

			}
		}
	}

	public static void main(String args[]) {

		Continent k = new Continent(500);

		// for testing
		

		// displays as a bufferedimage. color set from 2d array of int height
		final BufferedImage img = new BufferedImage(k.n, k.n, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) img.getGraphics();

		for (int i = 0; i < k.n; i++) {
			for (int j = 0; j < k.n; j++) {
				int c = k.grid[i][j].getHeight();
				if (c < 0) {
					g.setColor(new Color(10, 10, 60));
				} else {
					g.setColor(new Color(c, c, c));
				}
				g.fillRect(i, j, 1, 1);
			}
		}

		JFrame frame = new JFrame("Image test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.clearRect(0, 0, getWidth(), getHeight());
				// smoothing
				 //g2d.setRenderingHint(
				 //RenderingHints.KEY_INTERPOLATION,
				 //RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				// Or _BICUBIC
				g2d.scale(2, 2);
				g2d.drawImage(img, 0, 0, this);
			}
		};
		panel.setPreferredSize(new Dimension(k.n * 2, k.n * 2));
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);

	}

}
