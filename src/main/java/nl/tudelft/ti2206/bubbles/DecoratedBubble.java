package nl.tudelft.ti2206.bubbles;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * This interface is used to use the decorator pattern with {@link Bubble} sub
 * classes
 * 
 * @author Jan-Willem Gmelig Meyling
 *
 */
public interface DecoratedBubble extends Bubble {
	
	/**
	 * Return the current Bubble
	 * @return the Bubble attribute
	 */
	Bubble getBubble();
	
	@Override
	default void render(Graphics graphics) {
		getBubble().render(graphics);
	}

	@Override
	default void setPosition(Point position) {
		getBubble().setPosition(position);
	}

	@Override
	default Point getPosition() {
		return getBubble().getPosition();
	}

	@Override
	default int getX() {
		return getBubble().getX();
	}

	@Override
	default int getY() {
		return getBubble().getY();
	}

	@Override
	default int getWidth() {
		return getBubble().getWidth();
	}

	@Override
	default int getHeight() {
		return getBubble().getHeight();
	}

	@Override
	default int getRadius() {
		return getBubble().getRadius();
	}

	@Override
	default Point getCenter() {
		return getBubble().getCenter();
	}

	@Override
	default void setCenter(Point center) {
		getBubble().setCenter(center);
	}

	@Override
	default BubblePlaceholder getSnapPosition(Bubble bubble) {
		return getBubble().getSnapPosition(bubble);
	}

	@Override
	default void bind(Direction direction, Bubble other) {
		getBubble().bind(direction, other);
	}

	@Override
	default Bubble getBubbleAt(Direction direction) {
		return getBubble().getBubbleAt(direction);
	}

	@Override
	default void setBubbleAt(Direction direction, Bubble bubble) {
		getBubble().setBubbleAt(direction, bubble);
	}

	@Override
	default boolean hasBubbleAt(Direction direction) {
		return getBubble().hasBubbleAt(direction);
	}

	@Override
	default boolean isHittable() {
		return getBubble().isHittable();
	}

	@Override
	default Collection<Bubble> getNeighbours() {
		return getBubble().getNeighbours();
	}

	@Override
	default <T extends Bubble> List<T> getNeighboursOfType(Class<T> type) {
		return getBubble().getNeighboursOfType(type);
	}

	@Override
	default Point calculatePosition() {
		return getBubble().calculatePosition();
	}

	@Override
	default Map<Direction, Bubble> getConnections() {
		return getBubble().getConnections();
	}

	@Override
	default boolean popsWith(Bubble target) {
		return getBubble().popsWith(target);
	}
	
}