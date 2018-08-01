import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class GlobeGUI extends JFrame implements ActionListener {

	private Globe g;
	private int size;
	private JPanel panel;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	
	private JButton advance;
	private JButton play;
	private JButton pause;
	private JButton ice;
	
	private JLabel noContinents;
	private JLabel noSuperContinents;
	private JLabel seaChange;
	private JLabel averageHeight;
	
	private JPanel panel5;
	private JButton reset;
	private JButton generate;
	private JTextField enterNumber;
	
	
	
	
	
	private JTextField noContinentsF;
	private JTextField noSuperContinentsF;
	private JTextField seaChangeF;
	private JTextField averageHeightF;
	
	private boolean iceOnMap;
	private boolean paused;
	
	private Graphics2D gr;
	private Graphics2D g2d;

	private BufferedImage img;

	private boolean stop;

	private PlayerTask plt;

	public GlobeGUI(int size) {

		
		stop = true;
		g = new Globe(size);

		iceOnMap = true;
		
	/*	g.newNumbers(10, 10, 120, 75, 1);
		g.newNumbers(100, 90, 75, 100, 2);
		g.newNumbers(100, 190, 100, 50, 3);
		*/
		//g.setVelocity(1, 4, 4);
		
		
		

		
		g.plotMaps();

		this.size = size;

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		plt = new PlayerTask();
		layoutComponents();
		paused = true;
		plt.execute();
		
	}
	
	public void generate(int number) {
			
		
		
		/*
		for (int i = 1; i < number; i ++) {
			g.newNumbers( 0 + i*(size/number), i*(size/number) , size/number, size/(number+(int)(Math.random()*75)), i);
		}*/
		if (number == 3) {
		g.newNumbers(10, 10, 120, 75, 1);
		g.newNumbers(100, 90, 75, 100, 2);
		g.newNumbers(100, 190, 100, 50, 3);
		}
		
	
		
		
		
		for (int i = 1; i <= number; i ++) {
			g.setVelocity(i, randomSpeed(), randomSpeed());
		}
		
		
		g.plotMaps();
		redraw();
	}
	
	public int randomSpeed() {
		Random r = new Random();
		
		boolean neg = r.nextBoolean();
		int randomSpeed = r.nextInt(4);
		
		if (neg) {
			randomSpeed = -randomSpeed;		
		}
		
		return randomSpeed;
	}
	
	
	public void reset() {
		g = new Globe(size);
		redraw();
	}

	
	
	
	
	
	
	// makes a whole new frame and everything atm - java garbage should handle it
	public void redraw() {
		img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		gr = (Graphics2D) img.getGraphics();

		if (iceOnMap == true) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int c = g.getHeightMap()[i][j];
				if (c == 0) {
					gr.setColor(new Color(20, 20, 130));
				}

				else if (c > 0 && c < 175) {
					gr.setColor(new Color(c, c + 70, c));

					if (j <= size / 5 || j > size - (size / 5)) {
						gr.setColor(Color.WHITE);
					}
				}

				else {
					gr.setColor(Color.GRAY);
				}

				gr.fillRect(i, j, 1, 1);
			}
		}

		panel.repaint();
		}
		else {
			
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					int c = g.getHeightMap()[i][j];
					if (c == 0) {
						gr.setColor(new Color(20, 20, 130));
					}

					else if (c > 0 && c < 175) {
						gr.setColor(new Color(c, c + 70, c));
					}

					else {
						gr.setColor(Color.GRAY);
					}

					gr.fillRect(i, j, 1, 1);
				}
			}

			panel.repaint();
			
		}

	}

	public void layoutComponents() {

		img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		gr = (Graphics2D) img.getGraphics();

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int c = g.getHeightMap()[i][j];
				if (c == 0) {
					gr.setColor(new Color(20, 20, 130));
				}

				else if (c > 0 && c < 175) {
					gr.setColor(new Color(c, c + 70, c));

					if (j <= size / 5 || j > size - (size / 5)) {
						gr.setColor(Color.WHITE);
					}
				}

				else {
					gr.setColor(Color.GRAY);
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
				g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				// Or _BICUBIC
				g2d.scale(3, 3);
				g2d.drawImage(img, 0, 0, this);
			}
		};
		panel.setPreferredSize(new Dimension(size * 3, size * 3));

		panel2 = new JPanel();
		
		advance = new JButton("Advance time");
		advance.addActionListener(this);
		panel2.add(advance);

		play = new JButton("Play");
		play.addActionListener(this);
		pause = new JButton("Stop");
		pause.addActionListener(this);
		ice = new JButton("Show/Hide ice");
		ice.addActionListener(this);

		panel2.add(play);
		panel2.add(pause);
		panel2.add(ice);
		
		
		panel3 = new JPanel();
		
		noContinents = new JLabel("Continents:");
		noSuperContinents = new JLabel("Super Continents:");
		seaChange = new JLabel("Change in sea level:");
		averageHeight = new JLabel("Average height of continents:");
		
		
		noContinentsF = new JTextField(10);
		noSuperContinentsF = new JTextField(10);
		seaChangeF = new JTextField(10);
		averageHeightF = new JTextField(10);
		
		
		
		panel3.setLayout(new GridLayout(4,2));
		
		panel3.add(noContinents);
		panel3.add(noContinentsF);
		panel3.add(noSuperContinents);
		panel3.add(noSuperContinentsF);
		
		panel3.add(seaChange);
		panel3.add(seaChangeF);
		panel3.add(averageHeight);	
		panel3.add(averageHeightF);
		
		panel4 = new JPanel();
		
		panel5 = new JPanel();
		generate = new JButton("Generate");
		generate.addActionListener(this);
		reset = new JButton("Reset");
		reset.addActionListener(this);
		enterNumber = new JTextField("How many continents?");
		
		panel5.setLayout(new GridLayout(3,1));
		
		panel5.add(enterNumber);
		panel5.add(generate);
		panel5.add(reset);
		
		panel4.add(panel5, BorderLayout.NORTH);
		panel4.add(panel3, BorderLayout.SOUTH);

		this.add(panel4, BorderLayout.EAST);
		this.add(panel2, BorderLayout.SOUTH);
		this.getContentPane().add(panel);
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == advance) {
			g.move();
			redraw();
			
			

		}
		if (e.getSource() == play) {	
			paused = false;	
			play.setEnabled(false);
		}

		if (e.getSource() == pause) {
			paused = true;
			play.setEnabled(true);			
		}
		
		if (e.getSource() == ice) {
			if (iceOnMap == true) {
			iceOnMap = false;
			redraw();
		}
			else {
				iceOnMap = true;
				redraw();
			}
		}
		
		if (e.getSource() == reset) {
			reset();
		}
		
		if (e.getSource() == generate) {
			int number = Integer.parseInt(enterNumber.getText());
			generate(number);
		}
	}

	
	
	
	
	private class PlayerTask extends SwingWorker<Void, Integer> {
		protected Void doInBackground() {
			while (true) {
				if (paused == false) {
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
			}
			

		}

		protected void process(List<Integer> seaLevels) {

		}

		protected void done() {
			play.setEnabled(true);
		}
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				new GlobeGUI(250);

			}

		});

	}

}
