package nl.tudelft.ti2206.game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import nl.tudelft.ti2206.bubbles.AbstractBubble;
import nl.tudelft.ti2206.bubbles.BubbleMesh;
import nl.tudelft.ti2206.exception.GameOver;
import nl.tudelft.ti2206.room.Room;
import nl.tudelft.ti2206.room.SingleplayerRoom;
import nl.tudelft.util.ObservableObject;

public abstract class GamePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2416543550015136242L;
	
	public final static int WIDTH = AbstractBubble.WIDTH * 10 + AbstractBubble.WIDTH / 2 + 4;
	public final static int HEIGHT = 400;
	
	private final Dimension size = new Dimension(WIDTH, HEIGHT);
	
	public static Point cannonPosition;
	private Room room;
	
	protected ObservableObject<Long> score = new ObservableObject<Long>(0l);
	
	public GamePanel(final BubbleMesh bubbleMesh) {
		cannonPosition = new Point(WIDTH / 2, 400);
		room = new SingleplayerRoom(cannonPosition, this.getPreferredSize(), bubbleMesh, this);
		room.setup();
		GameTick gameTick = new GameTick(33);
		gameTick.start();
		gameTick.registerObserver(room);
		bubbleMesh.addScoreListener((amount) -> {
			setScore(getScore() + amount);
		});
		
		bubbleMesh.calculatePositions();
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		this.setVisible(true);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return size;
	}
	
	@Override
	public Dimension getMinimumSize() {
		return size;
	}
	
	@Override
	public Dimension getMaximumSize() {
		return size;
	}
	
	@Override
	public void paintComponent(final Graphics graphics) {
		super.paintComponent(graphics);
		room.render(graphics);
	}
	
	public void gameStep() throws GameOver {
		this.repaint();
	}
	
	public long getScore() {
		return score.getValue();
	}
	
	public void setScore(final long value) {
		score.setValue(value);
	}
	
	public void observeScore(final Observer o) {
		score.addObserver(o);
	}
	
}
