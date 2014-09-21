package nl.tudelft.ti2206.game;

import java.awt.Graphics;
import java.awt.Point;

import nl.tudelft.ti2206.bubbles.BubbleMesh;
import nl.tudelft.ti2206.cannon.Cannon;
import nl.tudelft.ti2206.exception.GameOver;

public class ReactiveGamePanel extends GamePanel {
	
	protected Cannon cannon;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 525456508008501827L;

	public ReactiveGamePanel(final BubbleMesh bubbleMesh) {
		super(bubbleMesh);
		this.cannon = new Cannon(bubbleMesh, new Point(WIDTH / 2, 400),
				this.getPreferredSize(), this.getLocation());
		this.cannon.bindMouseListenerTo(this);
	}
	
	@Override
	public void paintComponent(final Graphics graphics) {
		super.paintComponent(graphics);
		cannon.render(graphics);
	}

	@Override
	public void gameStep() throws GameOver {
		cannon.gameStep();
	}	
}
