package nl.tudelft.ti2206.game;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * GameTick notifies its observers every {@link framePeriod} milliseconds.
 * 
 * @author Sam Smulders
 */
public class GameTick {
	
	private ArrayList<Tickable> gameTickObservers = new ArrayList<Tickable>();
	
	public final void registerObserver(Tickable observer) {
		gameTickObservers.add(observer);
	}
	
	public final void removeObserver(Tickable observer) {
		gameTickObservers.remove(observer);
	}
	
	public void notifyObservers() {
		gameTickObservers.forEach(listener -> listener.gameTick());
	}
	
	private final int framePeriod;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	public GameTick(int framePeriod) {
		this.framePeriod = framePeriod;
	}
	
	public void start() {
		scheduler.scheduleAtFixedRate(new Runnable() {
			public void run() {
				notifyObservers();
			}
		}, framePeriod, framePeriod, TimeUnit.MILLISECONDS);
	}
}