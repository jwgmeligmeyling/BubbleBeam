package nl.tudelft.ti2206.game.event;

import java.util.EventListener;
import java.util.EventObject;
import java.util.List;
import java.util.Set;

import nl.tudelft.ti2206.bubbles.Bubble;
import nl.tudelft.ti2206.bubbles.mesh.BubbleMesh;

public interface BubbleMeshListener extends EventListener {

	abstract class BubbleMeshEvent extends EventObject {

		private static final long serialVersionUID = -7537897994655908360L;
		
		protected final transient BubbleMesh bubbleMesh;

		protected BubbleMeshEvent(BubbleMesh bubbleMesh) {
			super(bubbleMesh);
			this.bubbleMesh = bubbleMesh;
		}

		@Override
		public BubbleMesh getSource() {
			return bubbleMesh;
		}

	}
	
	class RowInsertEvent extends BubbleMeshEvent {
		
		private static final long serialVersionUID = 2696579104143658958L;
		
		protected final List<Bubble> insertedBubbles;
		
		public RowInsertEvent(BubbleMesh bubbleMesh, List<Bubble> insertedBubbles) {
			super(bubbleMesh);
			this.insertedBubbles = insertedBubbles;
		}

		/**
		 * @return the insertedBubbles
		 */
		public List<Bubble> getInsertedBubbles() {
			return insertedBubbles;
		}

		/**
		 * @return amount of inserted bubbles
		 * @see java.util.Set#size()
		 */
		public int amountOfInsertedBubbles() {
			return insertedBubbles.size();
		}
		
	}
	
	/**
	 * Invoked on this {@code BubbleMeshListener} when a {@link RowInsertEvent} occurred
	 * @param event {@code RowInsertEvent}
	 */
	void rowInsert(RowInsertEvent event);
	
	class BubblePopEvent extends BubbleMeshEvent {
		
		private static final long serialVersionUID = 5334939430531797593L;
		
		protected final Set<Bubble> bubblesPopped;

		public BubblePopEvent(BubbleMesh bubbleMesh, Set<Bubble> bubblesPopped) {
			super(bubbleMesh);
			this.bubblesPopped = bubblesPopped;
		}
		
		/**
		 * @return the bubblesPopped
		 */
		public Set<Bubble> getPoppedBubbles() {
			return bubblesPopped;
		}

		/**
		 * @return amount of bubbles popped
		 * @see java.util.Set#size()
		 */
		public int amountOfPoppedBubbles() {
			return bubblesPopped.size();
		}
		
	}
	
	/**
	 * Invoked on this {@code BubbleMeshListener} when a {@link BubblePopEvent} occurred
	 * @param event {@code BubblePopEvent}
	 */
	void pop(BubblePopEvent event);
	
	/**
	 * {@code RowInstertedListener}
	 * 
	 * @author Jan-Willem Gmelig Meyling
	 * @author Liam Clark
	 *
	 */
	@FunctionalInterface
	interface RowInstertedListener extends BubbleMeshListener {
		@Override default void pop(BubblePopEvent event) {}
	}
	
	/**
	 * {@code PopListener}
	 * 
	 * @author Jan-Willem Gmelig Meyling
	 * @author Liam Clark
	 *
	 */
	@FunctionalInterface
	interface PopListener extends BubbleMeshListener {
		@Override default void rowInsert(RowInsertEvent event) {} 
	}

}
