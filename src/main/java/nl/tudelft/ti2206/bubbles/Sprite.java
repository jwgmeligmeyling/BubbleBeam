package nl.tudelft.ti2206.bubbles;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Basic {@code Sprite}
 * 
 * @author Jan-Willem Gmelig Meyling
 */
public interface Sprite {
	
	/**
	 * Render this {@code Sprite}
	 * @param graphics
	 */
	void render(Graphics graphics);	
	
	/**
	 * Set the position for this {@code Sprite}
	 * @param position
	 */
	void setPosition(Point position);
	
	/**
	 * @return the position for this {@code Sprite}
	 */
	Point getPosition();
	
	/**
	 * @return the X variable for this {@code Sprite}
	 */
	int getX();
	
	/**
	 * @return the Y variable for this {@code Sprite}
	 */
	int getY();
	
	/**
	 * @return the width for this {@code Sprite}
	 */
	int getWidth();
	
	/**
	 * @return the height for this {@code Sprite}
	 */
	int getHeight();
	
	/**
	 * @return the dimensions for this {@code Sprite}
	 */
	default Dimension getSize() {
		return new Dimension(getWidth(), getHeight());
	}
	
}