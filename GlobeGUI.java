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
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.TitledBorder;

/**
 * A simple GUI designed to provide basic functionality of my implemented
 * system, and demonstrate the possibility of features the model could display.
 * Is coupled to Globe directly in a 1:1 relationship.
 * 
 * @author 2354535k
 *
 */
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

	private JPanel panel5;
	private JButton reset;
	private JButton generate;
	private JLabel numContPrompt;
	private JComboBox enterNumber;

	private JTextField noContinentsF;
	private JTextField noSuperContinentsF;
	private JTextField seaChangeF;

	private boolean iceOnMap;
	private boolean paused;

	private Graphics2D gr;
	private Graphics2D g2d;

	private BufferedImage img;

	private boolean stop;
	private double seaBase;

	private JButton save;
	private String file;

	private PlayerTask plt;

	/**
	 * Constructor.
	 * 
	 * @param size
	 *            - of Globe canvas.
	 */
	public GlobeGUI(int size) {

		this.setTitle("Continental Drift Simulator");
		stop = true;
		g = new Globe(size);

		iceOnMap = true;

		g.plotMaps();
		this.size = size;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		layoutComponents();
		paused = true;

		redraw();
	}

	/**
	 * Offers a choice of 2, 3, or 4 continents for the user to start the model
	 * with.
	 * 
	 * @param number
	 */
	public void generate(int number) {

		if (number == 3) {
			g.generate(10, 10, 120, 75, 1);
			g.generate(100, 90, 75, 100, 2);
			g.generate(100, 190, 100, 50, 3);

		}
		if (number == 2) {

			g.generate(10, 10, 160, 85, 1);
			g.generate(100, 90, 100, 150, 2);
		}

		if (number == 4) {
			g.generate(10, 10, 120, 75, 1);
			g.generate(100, 90, 75, 100, 2);
			g.generate(100, 190, 100, 50, 3);
			g.generate(10, 190, 100, 50, 4);
		}

		for (int i = 1; i <= number; i++) {
			g.setVelocity(i, randomSpeed(), randomSpeed());
		}

		g.plotMaps();
		redraw();
		noContinentsF.setText(Integer.toString(number));
		seaBase = g.iceCover();
	}

	/**
	 * Gives continents a random speed and direction.
	 * 
	 * @return
	 */
	public int randomSpeed() {
		Random r = new Random();

		boolean neg = r.nextBoolean();
		int randomSpeed = r.nextInt(4);

		if (neg) {
			randomSpeed = -randomSpeed;
		}

		return randomSpeed;
	}

	/**
	 * Resets the model, deleting all continents.
	 */
	public void reset() {
		g = new Globe(size);
		redraw();
	}

	// makes a whole new frame and everything atm - java garbage should handle it
	/**
	 * Redraws the map with updated locations and heights. A new BufferedImage is
	 * created, leaving Java machine to dispose of old one. It is in this method
	 * that intuitive colors are added to the map, depending on height of continent,
	 * and whether continent is located in the poles.
	 */
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
		} else {

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

	/**
	 * Lays out swing components.
	 */
	public void layoutComponents() {

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

		// panel2 for controls
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
		save = new JButton("Save Map");
		save.addActionListener(this);

		panel2.add(play);
		panel2.add(pause);
		panel2.add(ice);
		panel2.add(save);

		TitledBorder border2 = new TitledBorder("Controls");
		border2.setTitleJustification(TitledBorder.CENTER);
		border2.setTitlePosition(TitledBorder.TOP);
		panel2.setBorder(border2);

		// panel3 for data feedback
		panel3 = new JPanel();

		noContinents = new JLabel("Continents:");
		noSuperContinents = new JLabel("Super Continents:");
		seaChange = new JLabel("Change in sea level (m):");

		noContinentsF = new JTextField(10);
		noSuperContinentsF = new JTextField(10);
		seaChangeF = new JTextField(10);

		panel3.setLayout(new GridLayout(4, 2));

		panel3.add(noContinents);
		panel3.add(noContinentsF);
		panel3.add(noSuperContinents);
		panel3.add(noSuperContinentsF);
		panel3.add(seaChange);
		panel3.add(seaChangeF);

		TitledBorder border3 = new TitledBorder("Model data");
		border3.setTitleJustification(TitledBorder.CENTER);
		border3.setTitlePosition(TitledBorder.TOP);
		panel3.setBorder(border3);

		// panel5 for making continents
		panel5 = new JPanel();
		generate = new JButton("Generate");
		generate.addActionListener(this);
		reset = new JButton("Reset");
		reset.addActionListener(this);

		enterNumber = new JComboBox();
		enterNumber.addItem(2);
		enterNumber.addItem(3);
		enterNumber.addItem(4);
		numContPrompt = new JLabel("Continents?");
		panel5.setLayout(new GridLayout(3, 2));
		panel5.add(numContPrompt);
		panel5.add(enterNumber);
		panel5.add(generate);
		panel5.add(new JLabel(""));
		panel5.add(reset);

		TitledBorder border5 = new TitledBorder("Set up");
		border5.setTitleJustification(TitledBorder.CENTER);
		border5.setTitlePosition(TitledBorder.TOP);
		panel5.setBorder(border5);

		// panel4 for entire rightside parent panel
		panel4 = new JPanel();
		panel4.setLayout(new GridLayout(5, 1));
		panel4.add(panel5);
		panel4.add(panel2);
		panel4.add(panel3);

		this.add(panel4, BorderLayout.EAST);
		this.getContentPane().add(panel);
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// advance model by one
		if (e.getSource() == advance) {
			g.move();
			redraw();
			getData();
		}
		// play
		if (e.getSource() == play) {
			paused = false;
			play.setEnabled(false);
			plt = new PlayerTask();
			plt.execute();
		}
		// pause
		if (e.getSource() == pause) {
			paused = true;
			play.setEnabled(true);
			plt.cancel(true);
			plt = null;
		}
		// show or hide ice sheets
		if (e.getSource() == ice) {
			if (iceOnMap == true) {
				iceOnMap = false;
				redraw();
			} else {
				iceOnMap = true;
				redraw();
			}
		}
		// wipe map clean
		if (e.getSource() == reset) {
			reset();
			reset.setEnabled(false);
			generate.setEnabled(true);

		}
		// trigger generator module
		if (e.getSource() == generate) {
			int number = (int) enterNumber.getSelectedItem();
			generate(number);
			generate.setEnabled(false);
			reset.setEnabled(true);
		}
		// save map requirement.
		if (e.getSource() == save) {
			File file = new File("terrain.png");
			try {
				ImageIO.write(img, "png", file);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * A thread to auto play the model, repeating calls to move().
	 * 
	 * @author 2354535k
	 *
	 */
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
					getData();
				}
			}

		}

		protected void process(List<Integer> seaLevels) {

		}

		protected void done() {
			play.setEnabled(true);
		}
	}

	/**
	 * Data feedback from Globe class, to give a flavour of some information that
	 * can be offered besides the visual map
	 */
	public void getData() {

		int supers = g.getSuperCount();
		noSuperContinentsF.setText(Integer.toString(supers));

		double seaLevelChange = seaBase - g.iceCover();

		seaChangeF.setText(String.format("%.1f", seaLevelChange));

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
