import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GlobeGUI extends JFrame implements ActionListener{

	private Globe g;
	private int size;
	private JPanel panel;
	private JPanel panel2;
	private JButton advance;
	
	
	private Graphics2D gr;
	private Graphics2D g2d;
	
	private BufferedImage img;
	
	
	public GlobeGUI(int size) {		
		g = new Globe(size);
		
		//g.makeTestContinent();
		//g.makeOtherContinent();
		g.newNumbers();
		//g.setVelocity(1, 5, 2);
		//g.setVelocity(2,1,4);
		g.plotToHeightMap();
		
		//g.generate(4);
		
		//g.singlePixel();
		//g.plotToMap();
		
		this.size = size;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		layoutComponents();
	}
	
	// makes a whole new frame and verything atm - java garbage should handle it
	public void redraw() {
		img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		gr = (Graphics2D) img.getGraphics();

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int c = g.getHeightMap()[i][j];
				if (c < 0) {
					gr.setColor(new Color(10, 10, 100));
				} else {
					gr.setColor(new Color(c, c, c));
				}
				gr.fillRect(i, j, 1, 1);
			}
		}
		
		panel.repaint();
		
		
		
	}
	
	public void layoutComponents() {
		
		img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		gr = (Graphics2D) img.getGraphics();

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int c = g.getHeightMap()[i][j];
				if (c < 0) {
					gr.setColor(new Color(10, 10, 100));
				} else {
					gr.setColor(new Color(c, c, c));
				}
				gr.fillRect(i, j, 1, 1);
			}
		}
		
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				 g2d = (Graphics2D) g;
				g2d.clearRect(0, 0, getWidth(), getHeight());
				// smoothing
				 g2d.setRenderingHint(
				 RenderingHints.KEY_INTERPOLATION,
				 RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				// Or _BICUBIC
				g2d.scale(2, 2);
				g2d.drawImage(img, 0, 0, this);
			}
		};
		panel.setPreferredSize(new Dimension(size * 2, size * 2));
		
		panel2 = new JPanel();
		advance = new JButton("Advance time");
		
		advance.addActionListener(this);
		panel2.add(advance);
		
	
		this.add(panel2,BorderLayout.SOUTH);
		this.getContentPane().add(panel);
		this.pack();
		this.setVisible(true);
	}	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == advance) {
			g.move();
			redraw();
			System.out.println("Advance!");
			
		}		
	}
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		GlobeGUI main = new GlobeGUI(30);
		
		
		
	}





}
