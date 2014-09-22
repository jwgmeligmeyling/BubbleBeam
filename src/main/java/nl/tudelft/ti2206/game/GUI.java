package nl.tudelft.ti2206.game;

import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.text.MaskFormatter;

import nl.tudelft.ti2206.bubbles.BubbleMesh;
import nl.tudelft.ti2206.exception.GameOver;
import nl.tudelft.ti2206.network.Client;
import nl.tudelft.ti2206.network.Connector;
import nl.tudelft.ti2206.network.Host;

/**
 * @author leon Hoek Class that contains the GUI-frame and starts the game-loop
 */

public class GUI {
	
	JFrame frame;
	GamePanel player1Panel;
	
	// multiplayer on same machine
	GamePanel player2Panel;
	boolean multiplayer = false;
	
	// Repaint variables
	public static final int FPS = 60;
	protected static final int FRAME_PERIOD = 1000 / FPS;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	// Score-labels
	JLabel playerScore;
	JLabel player2Score;
	
	// game-variables
	boolean game_is_running = true;
	private Connector connector;
	
	// gridbag constants
	final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	final static boolean RIGHT_TO_LEFT = false;
	
	/**
	 * fills the panel of a Frame with the game and the controls
	 * 
	 * @param pane
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	private void fillGameFrame(Container pane) throws FileNotFoundException, IOException,
			ParseException {
		if (RIGHT_TO_LEFT) {
			pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		}
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// two presets for use with the GridBagLayout
		Insets extPadding = new Insets(10, 10, 10, 10);
		Insets noPadding = new Insets(0, 0, 0, 0);
		
		// everything the frame must be filled with
		if (multiplayer) {
			player1Panel = new MultiPlayerActiveGamePanel(BubbleMesh.parse(GUI.class
					.getResourceAsStream("/board.txt")), connector);
		} else {
			player1Panel = new SinglePlayerGamePanel(BubbleMesh.parse(GUI.class
					.getResourceAsStream("/board.txt")));
		}
		
		player1Panel.observeScore((a, b) -> updateDisplayedScore());
		
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		c.weighty = 0;
		c.gridheight = 4;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 0;
		c.ipady = 0;
		c.insets = extPadding;
		pane.add(player1Panel, c);
		
		// score-label
		playerScore = new JLabel("Score: ");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.weighty = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 0;
		c.gridy = 4;
		c.ipadx = 30;
		c.ipady = 30;
		c.insets = extPadding;
		pane.add(playerScore, c);
		
		JLabel spaceForLogo = new JLabel("");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 2;
		c.gridy = 0;
		c.ipadx = 30;
		c.ipady = 30;
		c.insets = noPadding;
		pane.add(spaceForLogo, c);
		
		final JButton exit = new JButton("Exit");
		exit.addActionListener((event) -> GUI.this.exit());
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 2;
		c.gridy = 1;
		c.ipadx = 30;
		c.ipady = 30;
		c.insets = extPadding;
		pane.add(exit, c);
		
		final JButton singlePlayerRestart = new JButton("Restart Single-Player");
		singlePlayerRestart.addActionListener((event) -> {
			multiplayer = false;
			GUI.this.restart();
		});
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 2;
		c.gridy = 2;
		c.ipadx = 30;
		c.insets = extPadding;
		pane.add(singlePlayerRestart, c);
		
		final JButton multiPlayerRestart = new JButton("Restart Multi-Player as Host");
		multiPlayerRestart.addActionListener((event) -> {
			multiplayer = true;
			connector = new Host();
			connector.connect();
			GUI.this.restart();
			connector.start();
		});
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 2;
		c.gridy = 3;
		c.ipadx = 30;
		c.insets = extPadding;
		pane.add(multiPlayerRestart, c);
		
		final JFormattedTextField ipaddressTextField = new JFormattedTextField(new MaskFormatter(
				"###.###.###.###")); // TODO change to
										// something less buggy
		
		final JButton findMultiPlayerRestart = new JButton("Find Multiplayer game");
		findMultiPlayerRestart.addActionListener((event) -> {
			multiplayer = true;
			connector = new Client(ipaddressTextField.getText()); // tijdelijk
				connector.connect();
				GUI.this.restart();
				connector.start();
			});
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 2;
		c.gridy = 4;
		c.ipadx = 30;
		c.insets = extPadding;
		pane.add(findMultiPlayerRestart, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 2;
		c.gridy = 5;
		c.ipadx = 30;
		c.insets = extPadding;
		pane.add(ipaddressTextField, c);
		
		JLabel version = new JLabel("Version: 0.1 Alpha"); // TODO how to add
															// versionnumber
															// from POM-file
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 1;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.SOUTH;
		c.gridx = 2;
		c.gridy = 6;
		c.ipadx = 30;
		c.insets = extPadding;
		pane.add(version, c);
		
		// multiplayer
		// everything the frame must be filled with for a local multiplayer
		if (multiplayer) {
			player2Panel = new MultiPlayerPassiveGamePanel(BubbleMesh.parse(GUI.class
					.getResourceAsStream("/board.txt")), connector);
			player2Panel.observeScore((a, b) -> updateDisplayedScore());
			
			c.fill = GridBagConstraints.NONE;
			c.weightx = 0;
			c.weighty = 0;
			c.gridheight = 4;
			c.gridwidth = 1;
			c.anchor = GridBagConstraints.WEST;
			c.gridx = 3;
			c.gridy = 0;
			c.ipadx = 0;
			c.ipady = 0;
			c.insets = extPadding;
			pane.add(player2Panel, c);
			
			// score-label for multiplayer
			player2Score = new JLabel("Score: ");
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0;
			c.weighty = 0;
			c.gridheight = 1;
			c.gridwidth = 1;
			c.anchor = GridBagConstraints.WEST;
			c.gridx = 3;
			c.gridy = 4;
			c.ipadx = 30;
			c.ipady = 30;
			c.insets = extPadding;
			pane.add(player2Score, c);
			
		}
		// upon filling the frame the score of the game is not yet displayed
		updateDisplayedScore();
		
	}
	
	/**
	 * Makes a new frame filled with the gamecontrols and then passes control to
	 * GUI.run()
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public GUI() throws FileNotFoundException, IOException, ParseException {
		frame = new JFrame("Bubble Shooter");
		
		// add the game + controls
		fillGameFrame(frame.getContentPane());
		
		// resize and center the frame
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		// start the game
		run();
	}
	
	protected void exit() {
		System.exit(0);
	}
	
	/**
	 * Function that restarts the game by removing everything from the frame and
	 * filling it up with new gamePanels and controls.
	 */
	protected void restart() {
		JPanel contentPane = (JPanel) frame.getContentPane();
		
		// clear the frame from all previous content ...
		contentPane.removeAll();
		
		// ... and fill it again
		try {
			fillGameFrame(contentPane);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		// make sure everything is right and also make sure the frame is the
		// right size
		contentPane.revalidate();
		contentPane.repaint();
		frame.pack();
		
		updateDisplayedScore();
		
	}
	
	/**
	 * Call this function when the score-attribute in GuiThrowAwayPanel has
	 * changed. This function will then display the new score on the screen.
	 */
	public void updateDisplayedScore() {
		playerScore.setText("Score: " + player1Panel.getScore());
		if (multiplayer) {
			
			player2Score.setText("Score: " + player2Panel.getScore());
		}
	}
	
	/**
	 * Periodically called function that calls the updatefunctions of the
	 * gamePanels
	 */
	protected void update() throws GameOver {
		player1Panel.gameStep();
		if (multiplayer) {
			player2Panel.gameStep();
		}
	}
	
	private void run() {
		scheduler.scheduleAtFixedRate(new Runnable() {
			public void run() {
				GUI.this.update();
			}
		}, 0L, 33L, TimeUnit.MILLISECONDS);
		
		new Timer(FRAME_PERIOD, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					player1Panel.repaint();
					if (player2Panel != null) {
						player2Panel.repaint();
					}
				} catch (GameOver exception) {
					restart();
				}
			}
		}).start();
		
	}
	
}
