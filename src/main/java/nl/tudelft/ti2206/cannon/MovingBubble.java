package nl.tudelft.ti2206.cannon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import nl.tudelft.ti2206.bubbles.ColouredBubble;
import nl.tudelft.ti2206.throwaway.GuiThrowAway;
import nl.tudelft.util.Vector2f;

/**
 * @author Sam Smulders
 * @author Luka Bavdaz
 *
 */
public class MovingBubble extends ColouredBubble {
	
	public static final float SPEED_MULTIPLIER = 300f / GuiThrowAway.FPS;
	
	protected Vector2f velocity;
	protected Vector2f truePosition;
	protected Dimension screenSize;
	protected Point screenLocation;
	
	public MovingBubble(final Point position, final Vector2f velocity,
			final Dimension screenSize, final Point screenLocation,
			final Color color) {
		super(color);
		this.screenSize = screenSize;
		this.truePosition = new Vector2f(position.x, position.y);
		this.screenLocation = screenLocation;
		this.velocity = velocity;
	}
	
	public void gameStep() {
		truePosition = truePosition.add(velocity.multiply(SPEED_MULTIPLIER));
		
		bounceOnWallCollision();
		
		setPosition(new Point((int) Math.round(truePosition.x), (int) Math
				.round(truePosition.y)));
	}
	
	/**
	 * 
	 */
	protected void bounceOnWallCollision() {
		if (truePosition.x + ColouredBubble.WIDTH > screenSize.width + screenLocation.x) {
			float xError = truePosition.x + ColouredBubble.WIDTH
					- (screenSize.width + screenLocation.x);
			truePosition = truePosition.add(velocity.multiply(-xError / velocity.x));
			velocity.x = -velocity.x;
		} else if (truePosition.x < screenLocation.x) {
			float xError = truePosition.x - screenLocation.x;
			truePosition = truePosition.add(velocity.multiply(-xError / velocity.x));
			velocity.x = -velocity.x;
		}
	}
}
