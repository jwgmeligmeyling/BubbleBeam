package nl.tudelft.ti2206.throwaway;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import nl.tudelft.ti2206.game.Cannon;

public class GuiThrowAwayPanel extends JPanel {
	
	private Bubble[] bubbels = new Bubble[200];
	private int current=0;
	private Cannon cannon;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 525456508008501827L;

	public GuiThrowAwayPanel() {
		this.setVisible(true);
	}	
	
	public void addBubble(Bubble b){
		bubbels[current] =b;
		current ++;
	}
	
	protected final static int WIDTH = 400;
	protected final static int HEIGHT = 400;
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
	
	@Override
	public void paintComponent(Graphics g){
		cannon.render(g);
		
		for(int i=0; i< current; i++){
			bubbels[i].render(g);
		}
	}

	public void addCannon(Cannon cannon) {
		this.cannon = cannon;
	}

	public void gameStep() {
		if(cannon!=null){
			cannon.update();
			repaint();
		}
	}
}
