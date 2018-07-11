import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Globe {
	
	private Square[][] squares; // this is just a list. the 2d index has no bearing on the location of the square. each square maintains its own x and y position
	private int size;
	private int[][] map; // this is a map. the 2d array index is derived from the x and y coordinates of the squares
	
	public Globe(int size) {
		
		this.size=size;
		
		squares = new Square[size][size];
		
		// can still be normal 1d array
		
		// all squares -1 (sea) across whole map
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				squares[i][j] = new Square(i,j,-1);
			}
		}
		
		// new 2d array which will take coordinates from Squares, and store their height
		setMap(new int[size][size]);
		
		for (int i = 0; i < size; i ++) {
			for (int j = 0; j < size; j++) {
				getMap()[i][j]=-1;
			}
		}
	}
	
	// make a continent for testing
	public void makeTestContinent() {
		for (int i = size/4; i < size/3; i++) {
			for (int j = size/4; j < size/3; j++) {
				squares[i][j] = new Square(i,j,250);
				squares[i][j].setGroup(1);
			}
		}
	}
	
	// sets velocities of all members of chosen group
	public void setVelocity(int group, int xVel, int yVel) {
		for (int i = 0; i < size; i ++) {
			for (int j = 0; j < size; j++) {
				if (squares[i][j].getGroup() == group) {
					squares[i][j].setXVel(xVel);
					squares[i][j].setXVel(yVel);					
				}
			}
		}
	}
	
	// moves all squares by one iteration of their velocities
	public void move() {
		for (int i = 0; i < size; i ++){
			for (int j = 0; j < size; j ++) {
				squares[i][j].setX(squares[i][j].getX()+(squares[i][j].getXVel()));
				squares[i][j].setY(squares[i][j].getY()+(squares[i][j].getYVel()));
			}
		}
		plotToMap();
	}
	
	// run through the Squares, getting their X and Y coordinates for 2d height array
	public void plotToMap() {
		for (int i = 0; i < size; i ++) {
			for (int j = 0; j < size; j ++) {
				getMap()[(int) Math.round(squares[i][j].getX())][(int) Math.round(squares[i][j].getY())] = squares[i][j].getHeight();		
			}
		}
	}
	
	
	// MAIN TESTING AND DISPLAY
	/*public static void main(String args[]) {

		Globe g = new Globe(250);
		int size = g.size;
		
		
		g.makeTestContinent();
		g.plotToMap();
		

		// for testing
		

		// displays as a bufferedimage. color set from 2d array of int height
		final BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		Graphics2D gr = (Graphics2D) img.getGraphics();

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int c = g.getMap()[i][j];
				if (c < 0) {
					gr.setColor(new Color(10, 10, 100));
				} else {
					gr.setColor(new Color(c, c, c));
				}
				gr.fillRect(i, j, 1, 1);
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
		panel.setPreferredSize(new Dimension(size * 2, size * 2));
		
		JPanel panel2 = new JPanel();
		Advancer button = new Advancer(g);
		panel2.add(button);
		frame.add(panel2,BorderLayout.SOUTH);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
		
		

	}
*/
	public int[][] getMap() {
		return map;
	}

	public void setMap(int[][] map) {
		this.map = map;
	}

	
	
	


}
