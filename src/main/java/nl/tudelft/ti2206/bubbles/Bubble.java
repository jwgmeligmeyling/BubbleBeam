package nl.tudelft.ti2206.bubbles;

import java.awt.Point;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

/**
 * A {@code} BubblePlaceholder represents a place that a Bubble can snap on to.
 * 
 * @author Jan-Willem Gmelig Meyling
 */
public interface Bubble extends Sprite, Circle, Serializable {
		
	void bind(Direction direction, Bubble other);
	Bubble getBubbleAt(Direction direction);
	void setBubbleAt(Direction direction, Bubble bubble);
	boolean hasBubbleAt(Direction direction);
	boolean isHittable();
	
	Collection<Bubble> getNeighbours();
	<T> List<T> getNeighboursOfType(Class<T> type);
	BubblePlaceholder getSnapPosition(Bubble b);

	void setOrigin(boolean value);
	Point calculatePosition();
	
	default Stream<Bubble> traverse(final Direction direction) {
		final List<Bubble> bubbles = Lists.newArrayList(this);
		Bubble current = this;
		while(current.hasBubbleAt(direction)) {
			current = current.getBubbleAt(direction);
			bubbles.add(current);
		}
		return bubbles.stream();
	}
	
	default void replace(final Bubble original) {
		this.bind(Direction.TOPLEFT, original.getBubbleAt(Direction.TOPLEFT));
		this.bind(Direction.TOPRIGHT, original.getBubbleAt(Direction.TOPRIGHT));
		this.bind(Direction.LEFT, original.getBubbleAt(Direction.LEFT));
		this.bind(Direction.RIGHT, original.getBubbleAt(Direction.RIGHT));
		this.bind(Direction.BOTTOMLEFT, original.getBubbleAt(Direction.BOTTOMLEFT));
		this.bind(Direction.BOTTOMRIGHT, original.getBubbleAt(Direction.BOTTOMRIGHT));
		this.setPosition(original.getPosition());
	}

	enum Direction {
		TOPLEFT, TOPRIGHT, LEFT, RIGHT, BOTTOMLEFT, BOTTOMRIGHT;
		
		public Direction opposite() {
			return oppositeFor(this);
		}
		
		public static Direction oppositeFor(final Direction direction) {
			switch(direction) {
			case BOTTOMLEFT: return TOPRIGHT;
			case BOTTOMRIGHT: return TOPLEFT;
			case LEFT: return RIGHT;
			case RIGHT: return LEFT;
			case TOPLEFT: return BOTTOMRIGHT;
			case TOPRIGHT: return BOTTOMLEFT;
			}
			throw new IllegalArgumentException();
		}
		
	}

}
