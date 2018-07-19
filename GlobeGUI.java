import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class GlobeGUI extends JFrame implements ActionListener{

	private Globe g;
	private int size;
	private JPanel panel;
	private JPanel panel2;
	private JButton advance;
	private JButton play;
	private JButton pause;
	
	private Graphics2D gr;
	private Graphics2D g2d;
	
	private BufferedImage img;
	
	private boolean stop;
	
	private PlayerTask plt;
	
	
	public GlobeGUI(int size) {		
		
		stop = false;
		g = new Globe(size);
		
		g.newNumbers(10,10,50, 1);
		g.newNumbers(100,100,149, 2);
		g.setVelocity(1, 1, 2);
		g.setVelocity(2,-3,-1);
		
			
		this.size = size;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		plt = new PlayerTask();
		
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
		
		play = new JButton("Play");
		play.addActionListener(this);
		pause = new JButton("Stop");
		pause.addActionListener(this);
		panel2.add(play);
		panel2.add(pause);
	
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
		if (e.getSource() == play) {
			stop = false;
			play.setEnabled(false);
			plt.execute();
		}
		
		if (e.getSource() == pause) {
			stop = true;
			play.setEnabled(true);
		}
	}
	
	private class PlayerTask extends SwingWorker<Void, Integer>{
		protected Void doInBackground() {
			while (!stop) {
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				g.move();
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				redraw();
			}
			return null;
			
		}
		
		protected void process(List<Integer> seaLevels) {
			
		}
		
		protected void done() {
			play.setEnabled(true);
		}
	}
	
	
	/*public void play() {
		while (!stop) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			g.move();
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			redraw();
		}
	}*/
		
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				
				new GlobeGUI(150);
				
			}
			
		});
		
		
	}





}
