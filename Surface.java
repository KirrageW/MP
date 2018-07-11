import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Surface {

	private int[][] map;
	private int size;
	
	private Square[] squares;
	
	public Surface(int size, Square[] squares) {
		this.size = size;
		map = new int[size][size];
		this.squares = squares;
		
	}
	
	public void setMap() {
		
	}
	
	
	
	
	public static void main(String[] args) {
		
		Globe g = new Globe(100);
		Surface k = new Surface(100, g.getSquares());
		
		// displays as a bufferedimage. color set from 2d array of int height
				final BufferedImage img = new BufferedImage(k.size, k.size, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = (Graphics2D) img.getGraphics();

				for (int i = 0; i < k.size; i++) {
					for (int j = 0; j < k.size; j++) {
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
